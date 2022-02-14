package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Resource.Course;
import fr.uca.springbootstrap.models.Resource.Resource;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
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
    RoleRepository roleRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @ParameterType("name|visibility|module|description")
    public String pathResource(String type) {
        return type;
    }

    @ParameterType("course|questioner")
    public String typeResource(String type) {
        return type;
    }

    @Given("a {typeResource} named {string}")
    public void aResourceNamed(String type, String resourceName) {
        Resource resource = resourceRepository.findByName(resourceName).orElse(type.equals("course") ? new Course(resourceName) : new Resource(resourceName));
        resourceRepository.save(resource);
    }

    @When("{string} request the id of the resource {string}")
    public void requestTheIdOfTheResource(String userName, String resourceName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Resource resource = resourceRepository.findByName(resourceName).orElseThrow(() -> new RuntimeException("Error: Resource is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/resource/" + resource.getName() + "/id", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} request the {pathResource} of the resource {string}")
    public void requestTheIdOfTheResource(String userName, String type, String resourceName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Resource resource = resourceRepository.findByName(resourceName).orElseThrow(() -> new RuntimeException("Error: Resource is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/resource/" + resource.getId() + "/" + type, jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("The user {string} try to {creation} the {typeResource} {string}")
    public void theUserTryToCreateTheResource(String userName, String creation, String type, String resourceName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executePost("api/resource/" + creation, jwt, "{\"name\":\"" + resourceName + "\",\"type\":\"" + type + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}