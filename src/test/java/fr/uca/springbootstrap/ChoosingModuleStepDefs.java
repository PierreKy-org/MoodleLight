package fr.uca.springbootstrap;
import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChoosingModuleStepDefs {
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


    @When("{string} chooses the module {string}")
    public void iChooseTheModule(String userName, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        //user.addModule(module);
        userRepository.save(user);
        moduleRepository.save(module);
    }

    @And("{string} can register to {string}")
    public void iCanRegisterTo(String userName, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertTrue(module.getParticipants().contains(user));
    }

    @And("{string} can't register to {string}")
    public void iCanTRegisterToIt(String userName, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        Assert.assertFalse(module.getParticipants().contains(user));
    }

    @When("{string} add Student {string} to {string}")
    public void addTo(String teacherName, String studentName, String moduleName) {
        User teacher = userRepository.findByUsername(teacherName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        User student = userRepository.findByUsername(studentName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        //teacher.addUserToModule(student, module);
        moduleRepository.save(module);
        userRepository.save(student);
        assertTrue(module.getParticipants().contains(student));
        assertTrue(student.getModules().contains(module));
    }

    @And("{string} is available for {string}")
    public void isAvailableFor(String moduleName, String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        assertTrue(module.getParticipants().contains(user));
    }

    @And("{string} has {int} teacher registered")
    public void hasTeacherRegistered(String moduleName, int countTeacher) {
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        //assertEquals(module.getNbrOfTeacher(), countTeacher);
    }

    @Given("A Module named {string} with no Teacher registered")
    public void aModuleNamedWithNoTeacherRegistered(String moduleName) {
        Module module = moduleRepository.findByName(moduleName).orElse(new Module(moduleName));
        for(User user : module.getParticipants()){
            for(Role role : user.getRoles()){
                if (role.getName().equals(ERole.ROLE_TEACHER)){
                    module.getParticipants().remove(user);
                }
            }

        }
        //assertEquals(0,module.getNbrOfTeacher());
        moduleRepository.save(module);
    }

    @When("the student {string} choose the module {string}")
    public void theStudentChooseTheModule(String student, String moduleName) {
        User user = userRepository.findByUsername(student).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        //user.addModule(module);
        moduleRepository.save(module);
        userRepository.save(user);
    }


}
