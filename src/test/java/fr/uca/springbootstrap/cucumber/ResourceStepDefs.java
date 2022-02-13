package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Resource;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
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
    ResourceRepository resourceRepository;

    @Given("a Resource named {string}")
    public void aResourceNamed(String name) {
        Resource resource = resourceRepository.findByName(name).orElse(new Resource(name));
        resourceRepository.save(resource);
    }

    @When("{string} request the id of the resource {string}")
    public void requestTheIdOfTheModule(String userName,String resourceName) {
        Resource resource = resourceRepository.findByName(resourceName).orElseThrow(()-> new RuntimeException("Error: Resource is not found."));
        User user = userRepository.findByUsername(userName).orElseThrow(()-> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            springIntegration.executeGet("api/resource/" + resource.getName() + "/id", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("{string} request the {pathModule} of the resource {string}")
    public void requestTheNameOfTheModule(String userName,String type,String resourceName) {
        Resource resource = resourceRepository.findByName(resourceName).orElseThrow(()-> new RuntimeException("Error: Resource is not found."));
        User user = userRepository.findByUsername(userName).orElseThrow(()-> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            springIntegration.executeGet("api/resource/" + resource.getId() + "/"+type, jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
