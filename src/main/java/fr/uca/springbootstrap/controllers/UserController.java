package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("/{login}/email")
    public String getEmail(@PathVariable Long login) {
        User user = userRepository.findById(login).orElseThrow(()->new RuntimeException("Error: User is not found."));
        return user.getEmail();
    }

    @GetMapping("/{login}/modules")
    public Set<Module> getModules(@PathVariable Long login) {
        User user = userRepository.findById(login).orElseThrow(()->new RuntimeException("Error: User is not found."));
        return user.getModules();
    }

    @GetMapping("/{login}/{role}")
    public ERole getRole(@PathVariable Long login, @PathVariable Role role) {
        userRepository.findById(login).orElseThrow(()->new RuntimeException("Error: User is not found."));
        return role.getName();
    }

    @PostMapping("/{oldLogin}/modifyLogin/{newLogin}")
    public void setLogin(@PathVariable Long oldLogin, @PathVariable Long newLogin) {
        User user = userRepository.findById(oldLogin).orElseThrow(()->new RuntimeException("Error: User is not found."));
        user.setId(newLogin);
    }

    @PostMapping("/{login}/modules/{newModules}")
    public void setModules(@PathVariable Long login, @PathVariable Set<Module> newModules) {
        User user = userRepository.findById(login).orElseThrow(()->new RuntimeException("Error: User is not found."));
        user.setModules(newModules);
    }
}
