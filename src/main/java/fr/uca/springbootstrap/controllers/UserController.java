package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.response.JwtResponse;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.security.jwt.JwtUtils;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/list")
    public ResponseEntity<?> getListOfUser(){
        List<User> list = userRepository.findAll();
        String[] myarr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"id\" : ").append(list.get(i).getId()).append(",");
            sb.append("\"username \": ").append("\"").append(list.get(i).getUsername()).append("\",");
            sb.append("\"email\" : ").append("\"").append(list.get(i).getEmail()).append("\",");
            sb.append("\"role\" : [");
            String prefix = "";
            for (Role r: list.get(i).getRoles()
                 ) {
                sb.append(prefix);
                prefix = ",";
                sb.append("\"").append(r.getName()).append("\"");
            }
            myarr[i] = sb.append("]}").toString();
        }
        HttpHeaders  h = new HttpHeaders();
        h.add("Content-Type","application/json");
        return ResponseEntity.ok().headers(h).body(Arrays.toString(myarr));
    }

    @GetMapping("/{userId}/email")
    public ResponseEntity<?> getEmailOfuser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"email\":" + user.getEmail() + "}");
    }

    @GetMapping("/{userId}/username")
    public ResponseEntity<?> getUsernameOfuser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"username\":" + user.getUsername() + "}");
    }

    @GetMapping("/{userId}/modules")
    public ResponseEntity<?> getModulesOfuser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Module module : user.getModules()){
            sb.append(module).append(",");
        }
        sb.append("]");
        return ResponseEntity.ok().body(sb);
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<?> getRolesOfuser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Role role : user.getRoles()){
            sb.append(role).append(",");
        }
        sb.append("]");
        return ResponseEntity.ok().body(sb);
    }
}

