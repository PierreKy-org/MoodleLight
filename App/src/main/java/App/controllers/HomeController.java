package App.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping("/")
    public String greeting() {
        return "<h1>Salut</h1>";
    }
}
