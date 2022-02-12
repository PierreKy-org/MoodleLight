package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ModuleControllerStepDefs {
    private static final String PASSWORD = "password";
    private SpringIntegration springIntegration = new SpringIntegration();

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @When("The user {string} try to register the user {string} to the module {string}")
    public void aGetRequestIsMadeTo(String user, String targetUser, String module) {
        String jwt = authController.generateJwt(user, PASSWORD);
        User u = userRepository.findByUsername(targetUser).orElse(null);
        Module m = moduleRepository.findByName(module).orElse(null);

        long targetId = u == null ? -1 : u.getId();
        long moduleId = m == null ? -1 : m.getId();
        try {
            springIntegration.executePost("http://localhost:8080/api/module/register", jwt, "{\"userId\":\"" + targetId + "\",\"moduleId\":\"" + moduleId + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
