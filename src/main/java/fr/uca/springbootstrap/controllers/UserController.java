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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static fr.uca.springbootstrap.models.ERole.ROLE_TEACHER;

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
    @ResponseBody
    public ResponseEntity<?> getListOfUser(@RequestParam  Optional<String> role){
        HttpHeaders  h = new HttpHeaders();
        h.add("Content-Type","application/json");
        if (role.isPresent()){
            Optional<Role> myrole = roleRepository.findByName(ERole.convertStringToErol(role.get()));
            System.out.println("\n");
            if (myrole.isPresent()){
                return ResponseEntity.ok().headers(h).body(parseData(userRepository.findAllByRoles(myrole.get())));
            }
        }

        return ResponseEntity.ok().headers(h).body(parseData(userRepository.findAll()));
    }

    private String parseData(List<User> list){
        String[] myarr = new String[list.size()];
        //TODO faire un toString pour user plutot que ça
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
        return Arrays.toString(myarr);
    }

    @GetMapping("/{userName}/id")
    public ResponseEntity<?> getIdOfUser(@PathVariable String userName) {
        //TOOD faire la feature
        User user = userRepository.findByUsername(userName).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"id\":" + user.getId() + "}");
    }

    @GetMapping("/{userId}/email")
    public ResponseEntity<?> getEmailOfUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"email\":" + user.getEmail() + "}");
    }

    @GetMapping("/{userId}/username")
    public ResponseEntity<?> getUsernameOfUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"username\":" + user.getUsername() + "}");
    }

    @GetMapping("/{userId}/modules")
    public ResponseEntity<?> getModulesOfUser(@PathVariable Long userId) {
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
    public ResponseEntity<?> getRolesOfUser(@PathVariable Long userId) {
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

