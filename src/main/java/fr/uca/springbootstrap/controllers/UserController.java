package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.response.JwtResponse;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/list")
    public ResponseEntity<?> getListOfUser(){
        List<User> list = userRepository.findAll();
        String[] myarr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("id : ").append(list.get(i).getId()).append(",");
            sb.append("username : ").append("\"").append(list.get(i).getUsername()).append("\",");
            sb.append("email : ").append("\"").append(list.get(i).getEmail()).append("\",");
            sb.append("role : [");
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
}

