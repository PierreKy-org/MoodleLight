package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Resource.Course;
import fr.uca.springbootstrap.models.Resource.Questioner;
import fr.uca.springbootstrap.models.Resource.Resource;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.payload.request.ResourceRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Transactional
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthController authController;

    @GetMapping("/{resourceName}/id")
    public ResponseEntity<String> getId(@PathVariable String resourceName) {
        Resource resource = resourceRepository.findByName(resourceName).orElse(null);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"id\":" + resource.getId() + "}");
    }

    @GetMapping("/{resourceId}/name")
    public ResponseEntity<String> getName(@PathVariable Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"name\":" + resource.getName() + "}");
    }

    @GetMapping("/{resourceId}/description")
    public ResponseEntity<String> getDescription(HttpServletRequest request, @PathVariable Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        if (resource.getVisibility().stream().map(Role::getName).anyMatch(r -> request.isUserInRole(r.toString()))) {
            return ResponseEntity.ok().body("{\"description\":" + resource.getDescription() + "}");
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/{resourceId}/module")
    public ResponseEntity<String> getModule(@PathVariable Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        Module module = resource.getModule();
        return ResponseEntity.ok().body(module == null ? "{}" : module.toString());
    }

    @GetMapping("/{resourceId}/visibility")
    public ResponseEntity<String> getVisibility(@PathVariable Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"name\":" + resource.getName() + "}");
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> createResource(@RequestBody ResourceRequest request) {
        if (resourceRepository.findByName(request.getName()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("A resource with this name already exists"));
        }
        Resource resource;
        if (request.getType().equals("questioner")) {
            resource = new Questioner(request.getName(), request.getDescription());
            resource.getVisibility().add(roleRepository.findByName(ERole.ROLE_TEACHER).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
            resourceRepository.save(resource);
            return ResponseEntity.ok(new MessageResponse("Questioner successfully created!"));
        } else if (request.getType().equals("course")) {
            resource = new Course(request.getName(), request.getDescription());
            resource.getVisibility().add(roleRepository.findByName(ERole.ROLE_TEACHER).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
            resourceRepository.save(resource);
            return ResponseEntity.ok(new MessageResponse("Course successfully created!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> deleteResource(@RequestBody ResourceRequest request) {
        Optional<Resource> resource = resourceRepository.findByName(request.getName());
        if (resource.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("The module does not exists"));
        }
        resourceRepository.delete(resource.get());
        return switch (request.getType()) {
            case "questioner" -> ResponseEntity.ok(new MessageResponse("Questioner successfully deleted!"));
            case "course" -> ResponseEntity.ok(new MessageResponse("Course successfully deleted!"));
            default -> ResponseEntity.ok(new MessageResponse("Module successfully deleted!"));
        };
    }
}
