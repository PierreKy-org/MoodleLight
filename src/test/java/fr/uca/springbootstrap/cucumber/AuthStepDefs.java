package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

public class AuthStepDefs {
    private final SpringIntegration springIntegration = new SpringIntegration();
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthController authController;

    @When("{string} try to SignUp with the email {string} and the password {string}")
    public void tryToSignUpWithTheEmailAndThePassword(String userName, String email, String password) {
        try {
            springIntegration.executePost("api/auth/signup", "", "{\"username\":\""+userName+"\",\"email\":\""+email+"\",\"password\":\""+password+"\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Given("{string} try to SignIn with the password {string}")
    public void tryToSignInWithThePassword(String username, String password) {
        try {
            springIntegration.executePost("api/auth/signin","","{\"username\":\""+username+"\",\"password\":\""+password+"\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
