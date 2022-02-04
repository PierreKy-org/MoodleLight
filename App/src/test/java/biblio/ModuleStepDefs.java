package biblio;

import Models.Users.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import Models.Module;
import org.junit.Assert;

import java.util.HashSet;

public class ModuleStepDefs extends SpringIntegration {
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


    @Given("A User with the login {string} and the role {string}")
    public void aTeacherWithLogin(String login,String role){
        User user = userRepository.findByUsername(login).orElse(new User(login));
        user.setRoles(new HashSet<Role>(){{ add(roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found."))); }});
        userRepository.save(user);
    }

    @And("A Module named {string}")
    public void aModuleNamed(String name) {
        Module module = moduleRepository.findByName(name).orElse(new Module(name));
        module.setParticipants(new HashSet<>());
        moduleRepository.save(module);
    }

    @When("{string} is removed from the Module {string}")
    public void aUserdeleteAnotherFromTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

        module.getUsersList().remove(user);
        user.getModules().remove(module);
    }

    @And("{string} is added to the Module {string}")
    public void isAddedToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElse(new User(login));
        Module module = moduleRepository.findByName(moduleName).orElse(new Module(moduleName));

        user.getModules().add(module);
        module.getUsersList().add(user);
    }

    @Then("{string} is not registered to the Module {string}")
    public void isNotRegisteredToTheModule(String login, String moduleName){
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

        Assert.assertFalse(user.getModules().contains(module));
        Assert.assertFalse(module.getUsersList().contains(user));
    }

    @When("{string} is registered to the Module {string}")
    public void isRegisteredToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

        Assert.assertTrue(user.getModules().contains(module));
        Assert.assertTrue(module.getUsersList().contains(user));
    }
}