package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Resource.Course;
import fr.uca.springbootstrap.models.Resource.Question;
import fr.uca.springbootstrap.models.Resource.Questioner;
import fr.uca.springbootstrap.models.Resource.Resource;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.QuestionRepository;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static fr.uca.springbootstrap.RunCucumberTest.PASSWORD;

public class QuestionStepDefs {
    private final SpringIntegration springIntegration = new SpringIntegration();

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @And("a Question named {string}")
    public void initQuestion(String questionName){
        Question question = questionRepository.findByName(questionName).orElse(new Question(questionName,"test description"));
        questionRepository.save(question);
    }

    @When("{string} request the id of question {string}")
    public void theUserRequestTheid(String userName, String questionName){
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            springIntegration.executeGet("api/question/"+questionName+"/id", jwt);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @When("The user {string} try to create the question {string}")
    public void theUserTryToCreateTheQuestion(String userName, String questioName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executePost("api/question/create/open", jwt, "{\"name\":\"" + questioName + "\",\"description\":\"test description\",\"answers\":[\"answer1\",\"answer2\",\"answer3\"]}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("The user {string} try to create the mqc {string}")
    public void theUserTryToCreateTheMqc(String userName, String questioName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executePost("api/question/create/mqc", jwt, "{\"name\":\"" + questioName + "\",\"description\":\"test description\",\"correct\":" + 1 + ",\"answers\":[\"answer1\",\"answer2\",\"answer3\"]}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("a Question with the id {long}")
    public void aQuestionWithTheId(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() ->  new RuntimeException("question not found"));
        questionRepository.save(question);
    }

    @When("{string} request the name of question of id {long}")
    public void requestTheNameOfQuestionOfId(String userName, Long id) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/question/"+ id +"/name", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} request the description of question of id {long}")
    public void requestTheDescriptionOfQuestionOfId(String userName, Long id) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/question/"+ id +"/description", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} request the answers of question of id {long}")
    public void requestTheAnswersOfQuestionOfId(String userName, Long id) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/question/"+ id +"/answers", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} request the answer of question of id {long}")
    public void requestTheAnswerOfQuestionOfId(String userName, Long id) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/question/"+ id +"/answer", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
