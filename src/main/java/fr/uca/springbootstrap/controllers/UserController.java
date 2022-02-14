package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
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
    public ResponseEntity<String> getListOfUser(@RequestParam  Optional<String> role){
        HttpHeaders  h = new HttpHeaders();
        h.add("Content-Type","application/json");
        if (role.isPresent()){
            Optional<Role> myrole = roleRepository.findByName(ERole.convertStringToErol(role.get()));
            if (myrole.isPresent()){
                return ResponseEntity.ok().headers(h).body(parseData(userRepository.findAllByRoles(myrole.get())));
            }
        }
        return ResponseEntity.ok().headers(h).body(parseData(userRepository.findAll()));
    }

    private String parseData(List<User> list){
        String[] myarr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            myarr[i] = list.get(i).toStringWithRole();
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserWithId(@PathVariable Long userId) {
        HttpHeaders  h = new HttpHeaders();
        h.add("Content-Type","application/json");
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().headers(h).body(user.toStringWithRole());
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
    @PutMapping("/{oldLogin}/modifyLogin/{newLogin}")
    public ResponseEntity<MessageResponse> modifyLogin(@PathVariable Long oldLogin,@PathVariable Long newLogin,@RequestHeader (name="Authorization") String token){
        User user = userRepository.findById(oldLogin).orElse(null);
        if(user==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error : The user doesn't exists"));
        }
        String jwt = authController.generateJwt(user.getUsername(), user.getPassword());
        if (!jwt.equals(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Error : Permission denied"));
        }
        user.setId(newLogin);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("The user Id is up to date"));
    }
    @PutMapping("/{login}/modifyEmail/{newEmail}")
    public ResponseEntity<MessageResponse> modifyEmail(@PathVariable Long login,@PathVariable String newEmail,@RequestHeader (name="Authorization") String token){
        User user = userRepository.findById(login).orElse(null);
        if(user==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error : The user doesn't exists"));
        }
        String jwt = authController.generateJwt(user.getUsername(), user.getPassword());
        if (!jwt.equals(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Error : Permission denied"));
        }
        user.setEmail(newEmail);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("The user email is up to date"));
    }
    @PutMapping("/{login}/modifyName/{newName}")
    public ResponseEntity<MessageResponse> modifyName(@PathVariable Long login,@PathVariable String newName,@RequestHeader (name="Authorization") String token){
        User user = userRepository.findById(login).orElse(null);
        if(user==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error : The user doesn't exists"));
        }
        String jwt = authController.generateJwt(user.getUsername(), user.getPassword());
        if (!jwt.equals(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Error : Permission denied"));
        }
        user.setUsername(newName);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("The user name is up to date"));
    }
    @PutMapping("/{login}/modifyPassword/{newPassword}")
    public ResponseEntity<MessageResponse> modifyPassword(@PathVariable Long login,@PathVariable String newPassword,@RequestHeader (name="Authorization") String token){
        User user = userRepository.findById(login).orElse(null);
        if(user==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error : The user doesn't exists"));
        }
        String jwt = authController.generateJwt(user.getUsername(), user.getPassword());
        if (!jwt.equals(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Error : Permission denied"));
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("The user password is up to date"));
    }
}

