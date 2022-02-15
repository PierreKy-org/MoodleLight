package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;

import static fr.uca.springbootstrap.RunCucumberTest.PASSWORD;

public class QuestionStepDefs {
    private final SpringIntegration springIntegration = new SpringIntegration();

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;


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
}
