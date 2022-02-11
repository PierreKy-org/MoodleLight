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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;


public class GeneralStepDefs {
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

    @Given("An User with the login {string} and the role {string}")
    public void aTeacherWithLogin(String login,Long roleId){
        User user = userRepository.findByUsername(login).
                orElse(new User(login, login + "@test.fr", encoder.encode("password")));
        user.setRoles(new HashSet<Role>(){{ add(roleRepository.findByName(ERole.ROLE_STUDENT).
                orElseThrow(() -> new RuntimeException("Error: Role is not found."))); }});
        userRepository.save(user);
    }

    @And("A Module named {string}")
    public void aModuleNamed(String name) {
        Module module = moduleRepository.findByName(name).orElse(new Module(name));
        module.setParticipants(new HashSet<>());
        moduleRepository.save(module);
    }


    @Then("the last request status is {int}")
    public void theLastRequestStatusIs(int status) {
        //assertEquals(status, latestHttpResponse.getStatusLine().getStatusCode()); //TODO recuperer la derniere reponse http
    }
}
