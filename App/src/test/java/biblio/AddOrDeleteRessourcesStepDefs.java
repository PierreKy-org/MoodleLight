package biblio;

import Models.Module;
import Models.Resssource.Resource;
import Models.Users.Teacher;
import Models.Users.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AddOrDeleteRessourcesStepDefs {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    AuthController authController;

    @Autowired
    PasswordEncoder encoder;

    @When("{string} add Resource {string} to the module {string}")
    public void addResourceTo(String login, String moduleName, String resourceName) {
        User user = userRepository.findByUsername(login).orElse(new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElse(new RuntimeException("Error: Module is not found."));
        Resource resource = resourceRepository.findByUsername(resourceName).orElse(new RuntimeException("Error: Resource@ is not found."));

        List<Module> userModules = user.getModules().stream().toList();
        userModules.get(userModules.indexOf(module)).addResource(resource); //TODO changer ca c'est horrible mais je vois pas comment ajouter a partir du user pour l'instant
        module.getResourcesList().add(resource);
    }

    @And("the number of Resource of {string} is {int}")
    public void theNumberOfResourceOfIs(String moduleName, int numberOfResources) {
        Module module = moduleRepository.findByName(moduleName).orElse(new RuntimeException("Error: Module is not found."));
        assertEquals(numberOfResources,module.getResourcesList().size());
    }

    @When("{string} delete {string} from the module {string}")
    public void deleteFrom(String login, String resourceName, String moduleName) {
        User user = userRepository.findByUsername(login).orElse(new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElse(new RuntimeException("Error: Module is not found."));
        Resource resource = resourceRepository.findByUsername(resourceName).orElse(new RuntimeException("Error: Resource@ is not found."));

        List<Module> userModules = user.getModules().stream().toList();
        userModules.get(userModules.indexOf(module)).removeResourceFromTheList(resource); //TODO changer ca c'est horrible mais je vois pas comment ajouter a partir du user pour l'instant
        module.getResourcesList().remove(resource);

    }

    @Then("The Module {string} no longer contains the resource {string}")
    public void noLongerContains(String moduleName, String resourceName) {
        Module module = moduleRepository.findByName(moduleName).orElse(new RuntimeException("Error: Module is not found."));
        Resource resource = resourceRepository.findByUsername(resourceName).orElse(new RuntimeException("Error: Resource@ is not found."));
        assertFalse(module.getResourcesList().contains(resource));
    }

    @And("The Module {string} no longer contains any Resource")
    public void noLongerContainsResource(String moduleName) {
        Module module = moduleRepository.findByName(moduleName).orElse(new RuntimeException("Error: Module is not found."));
        assertEquals(0, module.getResourcesList().size());
    }

    @Then("The Module {string} contains the resource {string}")
    public void theModuleContainsTheResource(String moduleName, String resourceName) {
        Module module = moduleRepository.findByName(moduleName).orElse(new RuntimeException("Error: Module is not found."));
        Resource resource = resourceRepository.findByUsername(resourceName).orElse(new RuntimeException("Error: Resource@ is not found."));
        assertTrue(module.getResourcesList().contains(resource));
    }
}
