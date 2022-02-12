package fr.uca.springbootstrap;
import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

import static org.junit.Assert.*;

public class ModuleStepDefs {
    private static final String PASSWORD = "password";
    private SpringIntegration springIntegration = new SpringIntegration();
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    PasswordEncoder encoder;

    public String tokenControl(String login){
        String jwt;
        try{
            jwt = authController.generateJwt(login,PASSWORD);
        }
        catch (BadCredentialsException e){
            jwt = "Wrong_Token";
        }
        return jwt;
    }

    @When("{string} is removed from the Module {string}")
    public void aUserdeleteAnotherFromTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElse(null);
        Module module = moduleRepository.findByName(moduleName).orElse(null);
        user.getModules().remove(module);
        module.getParticipants().remove(user);
        moduleRepository.save(module);
        userRepository.save(user);
    }

    @And("{string} is added to the Module {string}")
    public void isAddedToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElse(null);
        Module module = moduleRepository.findByName(moduleName).orElse(null);
        user.getModules().add(module);
        moduleRepository.save(module);
        userRepository.save(user);
    }

    @Then("{string} is not registered to the Module {string}")
    public void isNotRegisteredToTheModule(String login, String moduleName) throws IOException {
        User user = userRepository.findByUsername(login).orElse(null);
        Module module = moduleRepository.findByName(moduleName).orElse(null);
        assert user != null;
        assertFalse(user.getModules().contains(module));
        assert module != null;
        assertFalse(module.getParticipants().contains(user));
        String jwt = tokenControl(login);
        springIntegration.executeGet("http://localhost:8080/api/test/"+login+"/modules/"+moduleName,jwt);
    }
    @When("{string} is registered to the Module {string}")
    public void isRegisteredToTheModule(String login, String moduleName) throws IOException {
        User user = userRepository.findByUsername(login).orElse(null);
        Module module = moduleRepository.findByName(moduleName).orElse(null);
        assertTrue(user.getModules().stream().anyMatch(module1 -> module1.getId().equals(module.getId())));
        assertTrue(module.getParticipants().contains(user));
    }



}
