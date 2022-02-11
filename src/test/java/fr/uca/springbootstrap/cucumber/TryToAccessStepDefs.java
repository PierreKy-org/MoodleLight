package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.SpringIntegration;
import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import fr.uca.springbootstrap.models.User;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TryToAccessStepDefs {
    private SpringIntegration springIntegration = new SpringIntegration();
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

    @Given("An User with the login {string} and the role {string} with no Module")
    public void aTeacherNamedWithNoModule(Long login,Long roleId) {
        User user = userRepository.findById(login).orElse(new User());
        user.setRoles(new HashSet<>() {{
            add(roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        }}); //TODO do the function add
        userRepository.save(user);
    }

    @When("{string} try to access to {string}")
    public void tryToAccessTo(Long login, String name) throws IOException {
        User user = userRepository.findById(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        springIntegration.executePost("http://localhost:8080/api/module/"+name+"/participants/"+login,jwt); // TODO do the function
    }

    @And("{string} is not allowed to access to {string}")
    public void isNotAllowedToAccesTo(Long login, String moduleName) {
        User user = userRepository.findById(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

        assertFalse(user.getModules().contains(module));
    }

    @When("{string} want to know her number of the modules her follows")
    public void wantToKnowHerNumberOfTheModulesHerFollows(String login) {
    }

    @Then("{string} has {int} modules")
    public void thereIs(Long login,int number) {
        User user = userRepository.findById(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        assertEquals(number,user.getModules().size());
    }

    @When("{string} want to know his number of the modules his follows")
    public void wantToKnowHisNumberOfTheModulesHisFollows(Long login) {
        User user = userRepository.findById(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @When("{string} try to add the Module with the wrong name {string}")
    public void tryToAddTheModuleWithTheWrongName(Long login, String moduleName) {
        User user = userRepository.findById(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findById(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        user.getModules().add(module);
    }

    @And("{string} doesn't have access to the Module {string}")
    public void doesnTHaveAccessToTheModule(Long login, String moduleName) {
        User user = userRepository.findById(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findById(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertFalse(user.getModules().contains(module));
    }
}
