package biblio;

import Models.Module;
import Models.Users.Student;
import Models.Users.Teacher;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertFalse;


public class DeleteToModuleStepdefs {
    Teacher teacher;
    Module module;
    Teacher teacherToDelete;
    Student student;


    @Given("a Teacher named {string}")
    public void initaliseTeacher(int id,String nameTeacher,String lastName,String login,String password) {
        teacher = new Teacher(id,nameTeacher,lastName,login,password);

    }

    @And("a Module named {string}")
    public void initaliseModule(String moduleName) {
        module = new Module(moduleName);

    }
    @And("a teacher named {string} registered on {string}")
    public void initialiseTeacherToRemove(int id,String nameTeacher,String lastName,String login,String password){
        teacherToDelete = new Teacher(id,nameTeacher,lastName,login,password);
        teacherToDelete.addModules(module);
        module.addUserList(teacherToDelete);

    }
    @And("a Student with the id {int} is registered on {string}")
    public void initialiseStudent(int id,String nameTeacher,String lastName,String login,String password){
        student = new Student(id,nameTeacher,lastName,login,password);
        student.addModules(module);
        module.addUserList(student);

    }



    @Given("{string}")
    public void essays(){

    }
    @When("{string} delete {string} from the module")
    public void removeTeacher(String loginTeacher, String loginTeacherToDelete){
        teacher.setLogin(loginTeacher);
        teacherToDelete.setLogin(loginTeacherToDelete);
        teacher.removeUserFromModule(teacherToDelete, module);

    }
    @Then("{string} has no longer {string} in his modules")
    public void teacherCantAccess(){
        assertFalse(teacherToDelete.getModules().stream().anyMatch(module1 -> module1 == module));
    }


    @When("{string} delete the student with the id {int} from {string}")
    public void deleteStudentFromModule(){
        teacher.removeUserFromModule(student,module);
    }
    @Then("{string} is unavailable for {int}")
    public void studentCantAccess(){
        assertFalse(student.getModules().stream().anyMatch(module1 -> module1 == module));
    }



}
