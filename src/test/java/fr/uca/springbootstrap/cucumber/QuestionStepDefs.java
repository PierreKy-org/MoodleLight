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

    @ParameterType("create|delete")
    public String createOrDelete(String type){
        return type;
    }

    @ParameterType("open|mqc|runner")
    public String question(String type) {
        return type;
    }

    @ParameterType("answers|description")
    public String answersOrDescription(String type){
        return type;
    }

    @ParameterType("id|name|description")
    public String questionData(String type) {
        return type;
    }

    @Given("a {question} named {string}")
    public void initQuestion(String questionType, String questionName) {
        Question question = questionRepository.findByName(questionName).orElse(switch (questionType) {
            case "open" -> new Question(questionName, "test description");
            case "mqc" -> new MQC(questionName, "test description", new ArrayList<>(Arrays.asList("Paris", "Nice", "Lyon","Brest")));
            case "runner" -> new Runner(questionName, "test description", new ArrayList<>(Arrays.asList(0, 1, 2)));
            default -> throw new IllegalStateException("Unexpected value: " + questionType);
        });
        questionRepository.save(question);
    }

    @When("{string} request the {questionData} of question {string}")
    public void theUserRequestTheid(String userName,String param, String questionName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Question question = questionRepository.findByName(questionName).orElseThrow(()->new RuntimeException("Error: Question is not found"));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            switch (param){
                case "id"-> springIntegration.executeGet("api/question/" + questionName + "/id", jwt);
                case "description" -> springIntegration.executeGet("api/question/" + question.getId() + "/description", jwt);
                case "name" -> springIntegration.executeGet("api/question/" + question.getId() + "/name", jwt);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("The user {string} try to {createOrDelete} the {question} {string}")
    public void theUserTryToCreateTheQuestion(String userName,String addOrDelete, String question, String questionName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);

        String payload = switch (question) {
            case "open" -> "{\"name\":\"" + questionName + "\",\"description\":\"test description\",\"answers\":[\"answer1\",\"answer2\",\"answer3\"]}";
            case "mqc" -> "{\"name\":\"" + questionName + "\",\"description\":\"test description\",\"correct\":" + 1 + ",\"answers\":[\"answer1\",\"answer2\",\"answer3\"]}";
            case "runner" -> "{\"name\":\"" + questionName + "\",\"description\":\"test description\",\"inputs\":[0,2,3,6,10],\"outputs\":[0,4,9,36,100]}";
            default -> throw new RuntimeException();
        };
        try {
            if (addOrDelete.equals("create")){
                springIntegration.executePost("api/question/create/" + question, jwt, payload);
            }else{
                springIntegration.executePost("api/question/delete/" + question, jwt, payload);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("{string} request the answers of question {string}")
    public void requestTheQuestionDataOfName(String userName,String questionName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Question question = questionRepository.findByName(questionName).orElseThrow(() -> new RuntimeException("Error: Question is not found."));
        String jwt = authController.generateJwt(user.getUsername(), PASSWORD);
        try {
            springIntegration.executeGet("api/question/allAnswers/" + question.getId(), jwt);
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

    @When("{string} add an input {string} to the question {string}")
    public void addAnInputToTheQuestion(String userName, String valueInput, String questionName) {
        User user = userRepository.findByUsername(userName).orElseThrow(()->new RuntimeException("User is not found"));
        Question question = questionRepository.findByName(questionName).orElseThrow(()->new RuntimeException("Question is not found"));
        String jwt = authController.generateJwt(userName,PASSWORD);
        try{
            springIntegration.executePut("api/question/"+question.getId()+"/addInput",jwt,"{\"answer\":\"" + valueInput + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
