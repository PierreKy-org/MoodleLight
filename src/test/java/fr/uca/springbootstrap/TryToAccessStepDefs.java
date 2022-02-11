package fr.uca.springbootstrap;
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


    //TODO: do controller
    @When("{string} try to access to {string}")
    public void tryToAccessTo(String login, String name) throws IOException {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        springIntegration.executePost("http://localhost:8080/api/module/"+name+"/participants/"+login,jwt);
    }

    @And("{string} is not allowed to access to {string}")
    public void isNotAllowedToAccesTo(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

        assertFalse(user.getModules().contains(module));
    }

    @When("{string} want to know her number of the modules her follows")
    public void wantToKnowHerNumberOfTheModulesHerFollows(String login) {
    }

    @Then("{string} has {int} modules")
    public void thereIs(String login,int number) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        assertEquals(number,user.getModules().size());
    }

    @When("{string} want to know his number of the modules his follows")
    public void wantToKnowHisNumberOfTheModulesHisFollows(String login) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @When("{string} try to add the Module with the wrong name {string}")
    public void tryToAddTheModuleWithTheWrongName(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findById(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        user.getModules().add(module);
    }

    @And("{string} doesn't have access to the Module {string}")
    public void doesnTHaveAccessToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findById(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertFalse(user.getModules().contains(module));
    }

    @Given("An User with the login {string} and the role {string} with no Module")
    public void anUserWithTheLoginAndTheRoleWithNoModule(String arg0, String arg1) {
        User user = userRepository.findByUsername(arg0).
                orElse(new User(arg0, arg0 + "@test.fr", encoder.encode(PASSWORD)));
        user.setRoles(new HashSet<Role>(){{ add(roleRepository.findByName(ERole.ROLE_TEACHER).
                orElseThrow(() -> new RuntimeException("Error: Role is not found."))); }});
        userRepository.save(user);
    }
}
