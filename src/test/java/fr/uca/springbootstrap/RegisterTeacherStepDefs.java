package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class RegisterTeacherStepDefs {
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


    @When("{string} registers to module {string}")
    public void registersToModule(String arg0, String arg1) throws Exception {
        Module module = moduleRepository.findByName(arg1).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        User user = userRepository.findByUsername(arg0).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(arg0, PASSWORD);

        springIntegration.executePost("http://localhost:8080/api/module/register/" + user.getId() + "/" + module.getId(), jwt);
    }


    @Then("{string} is registered to module {string}")
    public void isRegisteredToModule(String arg0, String arg1) {
        Module module = moduleRepository.findByName(arg1).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        User user = userRepository.findByUsername(arg0).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        assertTrue(module.getParticipants().contains(user));
    }

    @And("{string} is not registered to module {string}")
    public void isNotRegisteredToModule(String arg0, String arg1) {
        Module module = moduleRepository.findByName(arg1).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        User user = userRepository.findByUsername(arg0).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        assertFalse(module.getParticipants().contains(user));
    }
}

