package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static fr.uca.springbootstrap.RunCucumberTest.PASSWORD;

import java.io.IOException;
import java.util.HashSet;

public class UserStepDefs {
    private final SpringIntegration springIntegration = new SpringIntegration();

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthController authController;

    @ParameterType("Teacher|Student|Admin")
    public ERole role(String role) {
        return ERole.convertStringToErol(role);
    }

    @ParameterType("username|email|roles|modules")
    public String pathUser(String type) {
        return type;
    }

    @Given("a {role} with the login {string}")
    public void aUserWithLogin(ERole userRole, String userId) {
        User user = userRepository.findByUsername(userId).orElse(new User(userId, userId + "@test.fr", encoder.encode(PASSWORD)));
        user.setRoles(new HashSet<>() {{
            add(roleRepository.findByName(userRole).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        }});
        userRepository.save(user);
    }

    @When("the user {string} request his {pathUser}")
    public void theStudentRequestHisPath(String userName, String path) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            springIntegration.executeGet("api/user/" + user.getId() + "/" + path, jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("the user {string} request his id")
    public void theStudentRequestHisId(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            springIntegration.executeGet("api/user/" + user.getUsername() + "/id", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
