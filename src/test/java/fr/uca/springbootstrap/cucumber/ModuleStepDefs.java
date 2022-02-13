package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;

import static fr.uca.springbootstrap.RunCucumberTest.PASSWORD;

public class ModuleStepDefs {
    private final SpringIntegration springIntegration = new SpringIntegration();

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @ParameterType("name|users")
    public String pathModule(String type) {
        return type;
    }

    @ParameterType("create|delete")
    public String creation(String type) {
        return type;
    }

    @Given("a Module named {string}")
    public void aModuleNamed(String name) {
        Module module = moduleRepository.findByName(name).orElse(new Module(name));
        module.setParticipants(new HashSet<>());
        moduleRepository.save(module);
    }

    @When("The user {string} try to register the user {string} to the module {string}")
    public void aGetRequestIsMadeToRegister(String user, String targetUser, String module) {
        String jwt = authController.generateJwt(user, PASSWORD);
        User u = userRepository.findByUsername(targetUser).orElse(null);
        Module m = moduleRepository.findByName(module).orElse(null);

        long targetId = u == null ? -1 : u.getId();
        long moduleId = m == null ? -1 : m.getId();
        try {
            springIntegration.executePost("api/module/register", jwt, "{\"userId\":\"" + targetId + "\",\"moduleId\":\"" + moduleId + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("The user {string} try to remove the user {string} to the module {string}")
    public void aGetRequestIsMadeToRemove(String user, String targetUser, String module) {
        String jwt = authController.generateJwt(user, PASSWORD);
        User u = userRepository.findByUsername(targetUser).orElse(new User());
        Module m = moduleRepository.findByName(module).orElse(new Module());
        try {
            springIntegration.executePost("api/module/remove", jwt, "{\"userId\":\"" + u.getId() + "\",\"moduleId\":\"" + m.getId() + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} request the {pathModule} of the module {string}")
    public void requestTheModuleNameOf(String userName, String type, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/module/" + module.getId() + "/" + type, jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} request the id of the module {string}")
    public void requestTheIdOfTheModule(String userName, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/module/" + module.getName() + "/id", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("The user {string} try to {creation} the module {string}")
    public void theUserTryToCreateTheModule(String userName, String creation, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            System.out.println("{\"name\":\"" + moduleName + "\"}");
            springIntegration.executePost("api/module/" + creation, jwt, "{\"name\":\"" + moduleName + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
