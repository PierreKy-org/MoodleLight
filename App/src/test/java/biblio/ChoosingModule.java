package biblio;

import Models.Module;
import Models.Users.Student;
import Models.Users.Teacher;
import Models.Users.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ChoosingModule {
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Given("A Teacher {string}")
    public void asATeacher(String teacherName) {
        Teacher teacher = teacherRepository.findByUsername(teacherName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }


    @When("{string} chooses the module {string}")
    public void iChooseTheModule(String userName, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        user.selectModule(module);
        Assert.assertTrue(user.getModules().contains(module));
        Assert.assertTrue(module.getUsersList().contains(user));
    }

    @Then("last request status is {int}")
    public void lastRequestStatusIs(int answerRequest) {
        //TODO refaire le gherkin car on ne sait pas qui renvoit le status
    }

    @And("{string} can register to {string}")
    public void iCanRegisterTo(String userName, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        Assert.assertTrue(module.availableFor.contains(user));
    }

    @And("{string} can't register to {string}")
    public void iCanTRegisterToIt(String userName, String moduleName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        Assert.assertFalse(module.availableFor.contains(user));
    }

    @When("{string} add {string} to {string}")
    public void addTo(String teacherName, String studentName, String moduleName) {
        Teacher teacher = teacherRepository.findByUsername(teacherName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Student student = studentRepository.findByUsername(studentName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        teacher.addStudent(module, student);
        Assert.assertTrue(module.getUsersList().contains(student));
        Assert.assertTrue(student.getModules().contains(module));
    }

    @And("{string} is available for {string}")
    public void isAvailableFor(String moduleName, String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        Assert.assertTrue(module.availableFor.contains(user));
    }

    @Given("A Student named {string}")
    public void anUserNamed(String studentName) {
        Student student = studentRepository.findByUsername(studentName).orElseThrow(() -> new RuntimeException("Error: User is not found."));

    }

    @Given("A Module {string}")
    public void aModule(String moduleName) {
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));

    }

    @And("{string} has {int} teacher registered")
    public void hasTeacherRegistered(String moduleName, int countTeacher) {
        Module module = moduleRepository.findByName(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
        Assert.assertTrue(module.getNbTeacher() == countTeacher);
    }
}
