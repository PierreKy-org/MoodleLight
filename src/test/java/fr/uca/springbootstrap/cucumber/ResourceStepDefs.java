package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Resource.Resource;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static fr.uca.springbootstrap.RunCucumberTest.PASSWORD;

public class ResourceStepDefs {
    private final SpringIntegration springIntegration = new SpringIntegration();

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @When("The user {string} try to create the course {string}")
    public void theUserTryToCreateTheCourse(String userName, String courseName) {
        String jwt = authController.generateJwt(userName, PASSWORD);
        Resource r = resourceRepository.findByName(courseName).orElse(null);

        try {
            springIntegration.executePost("api/resource/create", jwt, "{\"name\":\"" + courseName + "\",\"description\":\"" + "test description of the course"+ "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
