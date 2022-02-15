package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static fr.uca.springbootstrap.RunCucumberTest.PASSWORD;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashSet;

public class UserStepDefs {
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

    @ParameterType("Teacher|Student|Admin")
    public ERole role(String role) {
        return ERole.convertStringToErol(role);
    }

    @ParameterType("username|email|roles|modules")
    public String pathUser(String type) {
        return type;
    }

    @ParameterType("not registered|registered")
    public boolean registered(String str) {
        return str.equals("registered");
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

    @Then("The user {string} is {registered} the module {string}")
    public void theUserRegisterOrRemoveToTheModule(String userName, Boolean registered, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

        if (registered) {
            Assert.assertTrue(user.getModules().contains(module));
            Assert.assertTrue(module.getParticipants().contains(user));
        } else {
            Assert.assertFalse(user.getModules().contains(module));
            Assert.assertFalse(module.getParticipants().contains(user));
        }
    }

    @When("{string} change his {string} to {string}")
    public void changeHisUserNameTo(String userName, String param, String newParam) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error : The User doesn't exist"));
        String jwt = authController.generateJwt(userName, PASSWORD);
        try {
            switch (param) {
                case "userName" -> springIntegration.executePut("api/user/" + user.getId() + "/modifyName", jwt, "{\"name\":\""+ newParam + "\"}");
                case "userId" -> springIntegration.executePut("api/user/" + user.getId() + "/modifyLogin", jwt, "{\"id\":\"" + newParam + "\"}");
                case "email" -> springIntegration.executePut("api/user/" + user.getId() + "/modifyEmail", jwt, "{\"email\":\"" + newParam + "\"}");
                case "password" -> springIntegration.executePut("api/user/" + user.getId() + "/modifyPassword", jwt, "{\"password\":\"" + param + "\"}");
            }
            userRepository.save(user);
        }
        catch(IOException e){
                e.printStackTrace();
            }
        }

        @Then("{string} new {string} is {string}")
        public void hisnewParam (String userName,String param,String newParam){
            User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found"));
            switch (param){
                case "userName" -> assertEquals(newParam, user.getUsername());
                case "userId" -> assertEquals(Long.getLong(newParam), user.getId());
                case "email" -> assertEquals(newParam, user.getEmail());
                case "password" -> assertEquals(newParam, user.getPassword());
            }

        }

    @When("{string} try to change the {string} of {string} to {string}")
    public void tryToChangeTheOfTo(String userName1, String param, String userName2, String newParam) {
        User user1 = userRepository.findByUsername(userName1).orElseThrow(() -> new RuntimeException("Error : The User doesn't exist"));
        User user2 = userRepository.findByUsername(userName2).orElseThrow(() -> new RuntimeException("Error : The User doesn't exist"));
        String jwt = authController.generateJwt(userName1, user1.getPassword());
        try {
            switch (param) {
                case "userName" -> springIntegration.executePut("api/user/" + user2.getId() + "/modifyName", jwt, "{\"name\":\""+ newParam + "\"}");
                case "userId" -> springIntegration.executePut("api/user/" + user2.getId() + "/modifyLogin", jwt, "{\"id\":\"" + newParam + "\"}");
                case "email" -> springIntegration.executePut("api/user/" + user2.getId() + "/modifyEmail", jwt, "{\"email\":\"" + newParam + "\"}");
                case "password" -> springIntegration.executePut("api/user/" + user2.getId() + "/modifyPassword", jwt, "{\"password\":\"" + param + "\"}");
            }
            userRepository.save(user2);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
