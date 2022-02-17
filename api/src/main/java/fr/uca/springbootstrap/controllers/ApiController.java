package fr.uca.springbootstrap.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ApiController {

    @GetMapping("/api/doc")
    public String welcome() {
        try {
            InputStream inputStream = new ClassPathResource("static/index.html").getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");

            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            return "Aucune documentation disponible";
        }
    }
}
