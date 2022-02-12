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
        User user = userRepository.findByUsername(userName).orElse(null);
        Module module = moduleRepository.findByName(moduleName).orElse(null);
        user.getModules().add(module);
        userRepository.save(user);
        moduleRepository.save(module);
    }

    @And("{string} has {int} teacher registered")
    public void hasTeacherRegistered(String moduleName, int countTeacher) {
        Module module = moduleRepository.findByName(moduleName).orElse(null);
        assertEquals(module.getParticipantsOfRole(ERole.ROLE_TEACHER).length, countTeacher);
    }

    @Given("A Module named {string} with no Teacher registered")
    public void aModuleNamedWithNoTeacherRegistered(String moduleName) {
        Module module = moduleRepository.findByName(moduleName).orElse(new Module(moduleName));
        User[] teachers = module.getParticipantsOfRole(ERole.ROLE_TEACHER);
        for (int i = 0; i < teachers.length; i++) {
            int finalI = i;
            if (module.getParticipants().stream().anyMatch(user -> user.equals(teachers[finalI]))){
                module.getParticipants().remove(teachers[i]);
            }
        }
        assertEquals(0,module.getParticipantsOfRole(ERole.ROLE_TEACHER).length);
        moduleRepository.save(module);
    }



}
