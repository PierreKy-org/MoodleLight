package fr.uca.springbootstrap.controllers;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ApiController {

    @GetMapping("/api/doc")
    public String welcome() {
        Path fileName = Path.of("src/main/resources/static/index.html");
        try {
            return Files.readString(fileName);
        } catch (IOException e) {
            return "Aucune doc";
        }
    }

}
