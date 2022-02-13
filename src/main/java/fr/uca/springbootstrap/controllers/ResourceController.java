package fr.uca.springbootstrap.controllers;


import fr.uca.springbootstrap.models.Resource;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.request.ResourceRequest;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.util.Optional;

@Transactional
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/module/resource")
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
    public ResponseEntity<String> createResource(@RequestBody ResourceRequest resourceRequest){
        if (resourceRepository.findByName(resourceRequest.getName()).isPresent()){
            return ResponseEntity.badRequest().body("A Resource with this name already exists");
        }
        Resource resource = new Resource(resourceRequest.getName());
        resourceRepository.save(resource);
        return ResponseEntity.ok().body("Resource successfully created");
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> removeResource(@RequestBody ResourceRequest resourceRequest){
        Optional<Resource> resource = resourceRepository.findByName(resourceRequest.getName());
        if (resource.isEmpty()){
            return ResponseEntity.badRequest().body("This Resource doesn't exist");
        }
        resourceRepository.delete(resource.get());
        return ResponseEntity.ok().body("Resource successfully deleted");

    }


}
