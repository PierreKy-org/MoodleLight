package CucumberSteps;
import fr.uca.springbootstrap.SpringIntegration;
import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;

public class HttpStepDefs {
    private SpringIntegration springIntegration = new SpringIntegration();
    String username;
    String password;

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;

    @Given("the user {string} with the password {string}")
    public void theUserWithThePassword(String arg0, String arg1) {
        username = arg0;
        password = arg1;
    }

    @When("a get request is made to {string}")
    public void aGetRequestIsMadeTo(String arg0) throws IOException {
        String jwt;
        try{
            jwt = authController.generateJwt(username,password);
        }
        catch (BadCredentialsException e){
            jwt = "Wrong_Token";
        }
        springIntegration.executeGet("http://localhost:8080/" + arg0,jwt);
    }

    @Then("last request status is {int}")
    public void theResponseIs(int arg0) {
        Assert.assertEquals(arg0,springIntegration.latestHttpResponse.getStatusLine().getStatusCode());
    }

    @And("the response is {string}")
    public void theResponseIs(String arg0) throws IOException {
        Assert.assertEquals(arg0, EntityUtils.toString(springIntegration.latestHttpResponse.getEntity()));
    }

    @Then("a post request is made to {string}")
    public void aPostRequestIsMadeTo(String arg0) throws IOException {
        String jwt;
        try{
            jwt = authController.generateJwt(username,password);
        }
        catch (BadCredentialsException e){
            jwt = "Wrong_Token";
        }
        springIntegration.executePost("http://localhost:8080/" + arg0,jwt);
    }

    @Then("a put request is made to {string}")
    public void aPutRequestIsMadeTo(String arg0) throws IOException {
        String jwt;
        try{
            jwt = authController.generateJwt(username,password);
        }
        catch (BadCredentialsException e){
            jwt = "Wrong_Token";
        }
        springIntegration.executePut("http://localhost:8080/" + arg0,jwt);
    }

    @Then("a delete request is made to {string}")
    public void aDeleteRequestIsMadeTo(String arg0) throws IOException {
        String jwt;
        try{
            jwt = authController.generateJwt(username,password);
        }
        catch (BadCredentialsException e){
            jwt = "Wrong_Token";
        }
        springIntegration.executeDelete("http://localhost:8080/" + arg0,jwt);
    }


    @Then("a post request is made to {string} with payload {string}")
    public void aPostRequestIsMadeToWithPayload(String arg0, String arg1) throws IOException {
        String jwt;
        try{
            jwt = authController.generateJwt(username,password);
        }
        catch (BadCredentialsException e){
            jwt = "Wrong_Token";
        }
        springIntegration.executePost("http://localhost:8080/" + arg0,jwt,arg1);
    }
}
