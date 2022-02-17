package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Resource.MQC;
import fr.uca.springbootstrap.models.Resource.Question;
import fr.uca.springbootstrap.models.Resource.Runner;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.QuestionRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static fr.uca.springbootstrap.RunCucumberTest.PASSWORD;

public class QuestionStepDefs {
    private final SpringIntegration springIntegration = new SpringIntegration();

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @ParameterType("open|mqc|runner")
    public String question(String type) {
        return type;
    }

    @ParameterType("name|description|multiple answers|mqc answer")
    public String questionData(String type) {
        return switch (type) {
            case "multiple answers" -> "answers";
            case "mqc answer" -> "correct";
            default -> type;
        };
    }

    @Given("a {question} named {string}")
    public void initQuestion(String questionType, String questionName) {
        Question question = questionRepository.findByName(questionName).orElse(switch (questionType) {
            case "open" -> new Question(questionName, "test description");
            case "mqc" -> new MQC(questionName, "test description", 0);
            case "runner" -> new Runner(questionName, "test description", new ArrayList<>(Arrays.asList(0, 1, 2)));
            default -> throw new IllegalStateException("Unexpected value: " + questionType);
        });
        questionRepository.save(question);
    }

    @When("{string} request the id of question {string}")
    public void theUserRequestTheid(String userName, String questionName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            springIntegration.executeGet("api/question/" + questionName + "/id", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("The user {string} try to create the {question} {string}")
    public void theUserTryToCreateTheQuestion(String userName, String question, String questionName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        String payload = switch (question) {
            case "open" -> "{\"name\":\"" + questionName + "\",\"description\":\"test description\",\"answers\":[\"answer1\",\"answer2\",\"answer3\"]}";
            case "mqc" -> "{\"name\":\"" + questionName + "\",\"description\":\"test description\",\"correct\":" + 1 + ",\"answers\":[\"answer1\",\"answer2\",\"answer3\"]}";
            case "runner" -> "{\"name\":\"" + questionName + "\",\"description\":\"test description\",\"inputs\":[0,2,3,6,10],\"outputs\":[0,4,9,36,100]}";
            default -> throw new RuntimeException();
        };
        try {
            springIntegration.executePost("api/question/create/" + question, jwt, payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} request the {questionData} of question {string}")
    public void requestTheQuestionDataOfName(String userName, String data, String questionName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Question question = questionRepository.findByName(questionName).orElseThrow(() -> new RuntimeException("Error: Question is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executeGet("api/question/" + question.getId() + "/" + data, jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} answer the question {string} with {string}")
    public void answerTheQuestion(String userName, String questionName, String response) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Question question = questionRepository.findByName(questionName).orElseThrow(() -> new RuntimeException("Error: Question is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executePut("api/question/answer/" + question.getId(), jwt, "{\"answer\":\"" + response + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} add the valid answer {string} to the question {string}")
    public void addTheValidAnswerToTheQuestion(String userName, String answer, String questionName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Question question = questionRepository.findByName(questionName).orElseThrow(() -> new RuntimeException("Error: Question is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        try {
            springIntegration.executePut("api/question/" + question.getId() + "/addAnswer", jwt, "{\"answer\":\"" + answer + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
