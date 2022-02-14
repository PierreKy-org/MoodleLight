package fr.uca.springbootstrap.controllers;


import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Resource.Course;
import fr.uca.springbootstrap.models.Resource.Questioner;
import fr.uca.springbootstrap.models.Resource.Resource;
import fr.uca.springbootstrap.payload.request.ModuleRequest;
import fr.uca.springbootstrap.payload.request.ResourceRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Transactional
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ResourceRepository resourceRepository;

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
    public ResponseEntity<String> getDescription(@PathVariable Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"description\":" + resource.getDescription() + "}");
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
        System.out.println("NEW: " + resourceRepository.findByName(request.getName()));
        Resource resource;
        if (request.getType().equals("questioner")) {
            resource = new Questioner(request.getName());
            resourceRepository.save(resource);
            return ResponseEntity.ok(new MessageResponse("Questioner successfully created!"));
        } else if (request.getType().equals("course")) {
            resource = new Course(request.getName());
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
