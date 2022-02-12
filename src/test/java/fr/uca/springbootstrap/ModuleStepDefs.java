package fr.uca.springbootstrap;
import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.IOException;
import java.util.HashSet;
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

    @When("{string} is removed from the Module {string}")
    public void aUserdeleteAnotherFromTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        user.getModules().remove(module);
        module.getParticipants().remove(user);
        userRepository.save(user);
        moduleRepository.save(module);

    }

    @And("{string} is added to the Module {string}")
    public void isAddedToTheModule(String login, String moduleName) {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        user.getModules().add(module);
        userRepository.save(user);
    }

    @Then("{string} is not registered to the Module {string}")
    public void isNotRegisteredToTheModule(String login, String moduleName) throws IOException {
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertFalse(user.getModules().contains(module));
        assertFalse(module.getParticipants().contains(user));
        String jwt;
        try{
            jwt = authController.generateJwt(login,PASSWORD);
        }
        catch (BadCredentialsException e){
            jwt = "Wrong_Token";
        }
        springIntegration.executeGet("http://localhost:8080/api/test/"+login+"/modules/"+moduleName,jwt);
    }
    @When("{string} is registered to the Module {string}")
    public void isRegisteredToTheModule(String login, String moduleName){
        User user = userRepository.findByUsername(login).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertTrue(user.getModules().stream().anyMatch(module1 -> module1.getId().equals(module.getId())));
        assertTrue(module.getParticipants().contains(user));
    }

    @Given("a Student with the login {string} and doesn't have any Module")
    public void aStudentWithLogin(String arg0) {
        User user = userRepository.findByUsername(arg0).
                orElse(new User(arg0, arg0 + "@test.fr", encoder.encode(PASSWORD)));
        user.setRoles(new HashSet<>() {{
            add(roleRepository.findByName(ERole.ROLE_STUDENT).
                    orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        }});
        user.setModules(new HashSet<>());
        userRepository.save(user);
    }



}
