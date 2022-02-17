package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;

import fr.uca.springbootstrap.payload.request.ModuleRequest;
import fr.uca.springbootstrap.payload.request.UserRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthController authController;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<String> getListOfUser(@RequestParam Optional<String> role) {
        HttpHeaders h = new HttpHeaders();
        h.add("Content-Type", "application/json");
        if (role.isPresent()) {
            Optional<Role> myrole = roleRepository.findByName(ERole.convertStringToErol(role.get()));
            if (myrole.isPresent()) {
                return ResponseEntity.ok().headers(h).body(parseData(userRepository.findAllByRoles(myrole.get())));
            }
        }
        return ResponseEntity.ok().headers(h).body(parseData(userRepository.findAll()));
    }

    private String parseData(List<User> list) {
        String[] myarr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            myarr[i] = list.get(i).toStringWithRole();
        }
        return Arrays.toString(myarr);
    }

    @GetMapping("/{userName}/id")
    public ResponseEntity<?> getIdOfUser(@PathVariable String userName) {
        User user = userRepository.findByUsername(userName).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"id\":" + user.getId() + "}");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserWithId(@PathVariable Long userId) {
        HttpHeaders h = new HttpHeaders();
        h.add("Content-Type", "application/json");
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().headers(h).body(user.toStringWithRole());
    }

    @GetMapping("/{userId}/email")
    public ResponseEntity<?> getEmailOfUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"email\":" + user.getEmail() + "}");
    }

    @GetMapping("/{userId}/username")
    public ResponseEntity<?> getUsernameOfUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"username\":" + user.getUsername() + "}");
    }

    @GetMapping("/{userId}/modules")
    public ResponseEntity<?> getModulesOfUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Module module : user.getModules()) {
            sb.append(module).append(",");
        }
        sb.append("]");
        return ResponseEntity.ok().body(sb);
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<?> getRolesOfUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Role role : user.getRoles()) {
            sb.append(role).append(",");
        }
        sb.append("]");
        return ResponseEntity.ok().body(sb);
    }

}

