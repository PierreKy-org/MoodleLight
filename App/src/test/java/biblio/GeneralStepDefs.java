package biblio;

import Models.Module;
import Models.Resssource.Resource;
import Models.Users.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

public class GeneralStepDefs {
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RessourceRepository ressourceRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    PasswordEncoder encoder;

    @Given("An User with the login {string} and the role {string}")
    public void aTeacherWithLogin(String login,String role){
        User user = userRepository.findByUsername(login).orElse(new User(login));
        user.setRoles(new HashSet<BeanDefinitionDsl.Role>(){{ add(roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found."))); }});
        userRepository.save(user);
    }

    @And("A Module named {string}")
    public void aModuleNamed(String name) {
        Module module = moduleRepository.findByName(name).orElse(new Module(name));
        module.setParticipants(new HashSet<>());
        moduleRepository.save(module);
    }

    @And("A Resource named {string}")
    public void aResourceNamed(String name) {
        Resource resource = ressourceRepository.findByName(name).orElse(new Resource(name));
        resourceRepository.save(resource);
    }
}
