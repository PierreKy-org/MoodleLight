package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Resource.Resource;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.IOException;

import static fr.uca.springbootstrap.RunCucumberTest.PASSWORD;

public class AuthStepDefs {
    private final SpringIntegration springIntegration = new SpringIntegration();
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ResourceRepository resourceRepository;
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

    @When("the user {string} request his id with the token {string}")
    public void theUserRequestHisIdWithTheToken(String userName, String token) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            springIntegration.executeGet("api/user/" + user.getUsername() + "/id", token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("The user {string} try to have the description of the resource {string} but he doesn't have the visibility")
    public void theUserTryToHaveTheDescriptionOfTheResourceButHeDoesnTHaveTheVisibility(String userName, String resourceName) {
        User user = userRepository.findByUsername(userName).orElseThrow(()->new RuntimeException("User is not found"));
        Resource resource = resourceRepository.findByName(resourceName).orElseThrow(()-> new RuntimeException("Resource is not found"));
        String jwt = authController.generateJwt(userName,PASSWORD);
        try{
            springIntegration.executeGet("api/resource/"+resource.getId()+"/description",jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Given("a Teacher who doesn't exists with the login {string}")
    public void aTeacherWhoDoesnTExistsWithTheLogin(String userName) {
        userRepository.findByUsername(userName).ifPresent(user -> userRepository.delete(user));
    }
}
