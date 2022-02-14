package fr.uca.springbootstrap.cucumber;

import fr.uca.springbootstrap.spring.SpringIntegration;
import io.cucumber.java.en.Then;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import java.io.IOException;

public class HttpStepDefs {

    @Then("last request status is {int}")
    public void theStatusIs(int arg0) {
        Assert.assertEquals(arg0, SpringIntegration.latestHttpResponse.getStatusLine().getStatusCode());
    }

    @Then("the response is {string}")
    public void theResponseIs(String payload){
        try {
            String body = EntityUtils.toString(SpringIntegration.latestHttpResponse.getEntity());
            System.out.println(body);
            Assert.assertTrue(body.matches(payload));
        } catch (IOException e) {
            Assert.fail();
        }
    }
}
