package fr.uca.springbootstrap.controllers;


import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.request.ModuleRequest;
import fr.uca.springbootstrap.payload.request.RegistrationRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.ModuleRepository;
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
@RequestMapping("/api/module")
public class ModuleController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @GetMapping("/{moduleName}/id")
    public ResponseEntity<String> getId(@PathVariable String moduleName) {
        Module module = moduleRepository.findByName(moduleName).orElse(null);
        if (module == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"id\":" + module.getId() + "}");
    }

    @GetMapping("/{moduleId}/name")
    public ResponseEntity<String> getName(@PathVariable Long moduleId) {
        Module module = moduleRepository.findById(moduleId).orElse(null);
        if (module == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"name\":" + module.getName() + "}");
    }

    @GetMapping("/{moduleId}/users")
    public ResponseEntity<String> getUsers(@PathVariable Long moduleId) {
        Module module = moduleRepository.findById(moduleId).orElse(null);
        if (module == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("[" + module.getParticipants().stream().map(User::toString).reduce("", (subtotal, element) -> subtotal + element + ",") + "]");
    }

    //TODO /{moduleId}/users/{role}

    @PostMapping("/register")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> addUserToModule(@RequestBody RegistrationRequest request) {
        Module module = moduleRepository.findById(request.getModuleId()).orElse(null);
        User user = userRepository.findById(request.getUserId()).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("The user does not exists"));
        } else if (module == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("The module does not exists"));
        } else if (user.hasRole(ERole.ROLE_TEACHER) && module.getParticipantsOfRole(ERole.ROLE_TEACHER).length == 1) {
            return ResponseEntity.badRequest().body(new MessageResponse("There is already a teacher registered to the course"));
        } else if (module.getParticipants().contains(user)) {
            return ResponseEntity.badRequest().body(new MessageResponse("The user is already registered"));
        }

        user.getModules().add(module);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User successfully registered to module!"));
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> removeUserFromModule(@RequestBody RegistrationRequest request) {
        Module module = moduleRepository.findById(request.getModuleId()).orElse(null);
        User user = userRepository.findById(request.getUserId()).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("The user does not exists"));
        } else if (module == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("The module does not exists"));
        } else if (!module.getParticipants().contains(user)) {
            return ResponseEntity.badRequest().body(new MessageResponse("The user is already not registered to the module"));
        }

        user.getModules().remove(module);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User successfully removed from the module!"));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> createModule(@RequestBody ModuleRequest request) {
        if (moduleRepository.findByName(request.getName()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("A module with this name already exists"));
        }
        Module module = new Module(request.getName());
        moduleRepository.save(module);
        return ResponseEntity.ok(new MessageResponse("Module successfully created!"));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> deleteModule(@RequestBody ModuleRequest request) {
        Optional<Module> module = moduleRepository.findByName(request.getName());
        if (module.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("The module does not exists"));
        }
        moduleRepository.delete(module.get());
        return ResponseEntity.ok(new MessageResponse("Module successfully deleted!"));
    }

}
