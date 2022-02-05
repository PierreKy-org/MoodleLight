package CucumberSteps;

import App.models.Module;
import App.models.Role;
import App.models.User;
import App.repository.ModuleRepository;
import App.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class ModuleStepDef {
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    UserRepository userRepository;

    @Given("A User with the login {string} and the role {string}")
    public void aUserWithTheLoginAndTheRole(String login, String role) {
        User user = userRepository.findByUsername(login).orElse(new User(login, login + "@gmail.com", "abcdef", Role.getRole(role)));
        userRepository.save(user);
    }

    @And("A Module named {string}")
    public void aModuleNamed(String name) {
        Module module = moduleRepository.findByName(name).orElse(new Module(name));
        moduleRepository.save(module);
    }

    @When("{string} is added to the Module {string}")
    public void isAddedToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: User is not found."));

        //TODO remplacer par une requete
        user.getModules().add(module);
        module.getParticipants().add(user);
    }

    @And("{string} is removed from the Module {string}")
    public void isRemovedFromTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: User is not found."));

        user.getModules().remove(module);
        module.getParticipants().remove(user);
    }

    @Then("{string} is not registered to the Module {string}")
    public void isNotRegisteredToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: User is not found."));

        Assert.assertFalse(user.getModules().contains(module));
        Assert.assertFalse(module.getParticipants().contains(user));
    }

    @Then("{string} is registered to the Module {string}")
    public void isRegisteredToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: User is not found."));

        Assert.assertTrue(user.getModules().contains(module));
        Assert.assertTrue(module.getParticipants().contains(user));
    }
}
