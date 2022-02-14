package fr.uca.springbootstrap.controllers;


import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Resource.Course;
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
    public ResponseEntity<String> getId(@PathVariable String resourceName){
        Resource resource = resourceRepository.findByName(resourceName).orElse(null);
        if (resource==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"id\" : "+resource.getId()+"}");
    }

    @GetMapping("/{resourceId}/name")
    public ResponseEntity<String> getName(@PathVariable Long resourceId){
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"name\" : "+resource.getName()+"}");
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> createCourse(@RequestBody ResourceRequest request) {
        if (resourceRepository.findByName(request.getName()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("A module with this name already exists"));
        }
        Resource resource = new Course(request.getName(),request.getDescription());
        resourceRepository.save(resource);
        return ResponseEntity.ok(new MessageResponse("Module successfully created!"));
    }


}
