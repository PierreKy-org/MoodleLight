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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ModuleStepDefs {
    private static final String PASSWORD = "password";

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

    @When("{string} is removed from the Module {string}")
    public void aUserdeleteAnotherFromTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        module.getParticipants().remove(user);
        user.getModules().remove(module);
        moduleRepository.save(module);
        userRepository.save(user);
    }

    @And("{string} is added to the Module {string}")
    public void isAddedToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElse(new User());
        Module module = moduleRepository.findByName(moduleName).orElse(new Module(moduleName));
        //user.addModule(module);
        module.getParticipants().add(user);
        moduleRepository.save(module);
        userRepository.save(user);
    }

    @Then("{string} is not registered to the Module {string}")
    public void isNotRegisteredToTheModule(String login, String moduleName){
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertFalse(user.getModules().contains(module));
        assertFalse(module.getParticipants().contains(user));
    }

    @When("{string} is registered to the Module {string}")
    public void isRegisteredToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertTrue(module.getParticipants().contains(user));
        assertTrue(user.getModules().stream().anyMatch(module1 -> module1.getId().equals(module.getId())));
    }
}
