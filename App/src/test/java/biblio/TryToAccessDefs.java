package biblio;

import Models.Users.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.fasterxml.jackson.databind.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

import static org.junit.Assert.*;

public class TryToAccessDefs {


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
    public void aTeacherNamedWithNoModule(String login,String role) {
        User user = userRepository.findUserName(login).orElse(new User(login));
        user.setRoles(new HashSet<BeanDefinitionDsl.Role>(){{ add(roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found."))); }});
        userRepository.save(user);
    }

    @When("{string} try to access to {string}")
    public void tryToAccessTo(String login, String name) {
        User user = userRepository.findUserName(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

        executePost("http://localhost:8080/api/module/"+module.getModuleName()+"/participants/"+user.getLogin());
    }

    @And("{string} is not allowed to access to {string}")
    public void isNotAllowedToAccesTo(String login, String moduleName) {
        User user = userRepository.findUserName(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

        assertFalse(user.getModules().contains(module));
    }

    @When("{string} want to know her number of the modules her follows")
    public void wantToKnowHerNumberOfTheModulesHerFollows(String login) {
    }

    @Then("{string} has {int} modules")
    public void thereIs(String login,int number) {
        User user = userRepository.findUserName(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        assertEquals(number,user.getModules().size());
    }

    @When("{string} want to know his number of the modules his follows")
    public void wantToKnowHisNumberOfTheModulesHisFollows(String login) {
        User user = userRepository.findUserName(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @When("{string} try to add the Module with the wrong name {string}")
    public void tryToAddTheModuleWithTheWrongName(String login, String moduleName) {
        User user = userRepository.findUserName(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.FindByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        user.getModules().add(module);
    }

    @And("{string} doesn't have access to the Module {string}")
    public void doesnTHaveAccessToTheModule(String login, String moduleName) {
        User user = userRepository.findUserName(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.FindByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertFalse(user.getModules().contains(module));
    }
}
