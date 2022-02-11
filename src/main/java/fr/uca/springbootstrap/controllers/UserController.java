package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.ERole;
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
        System.out.println("---------------------------------------");
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

    @GetMapping("/{login}/email")
    public String getEmail(@PathVariable Long login) {
        User user = userRepository.findById(login).orElseThrow(()->new RuntimeException("Error: User is not found."));
        return user.getEmail();
    }

    @GetMapping("/{login}/modules")
    public Set<fr.uca.springbootstrap.models.Module> getModules(@PathVariable Long login) {
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
    public void setModules(@PathVariable Long login, @PathVariable Set<fr.uca.springbootstrap.models.Module> newModules) {
        User user = userRepository.findById(login).orElseThrow(()->new RuntimeException("Error: User is not found."));
        user.setModules(newModules);
    }
}


