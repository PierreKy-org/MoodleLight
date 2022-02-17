package fr.uca.springbootstrap.spring;

import fr.uca.springbootstrap.SpringBootSecurityPostgresqlApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@CucumberContextConfiguration
@SpringBootTest(classes = SpringBootSecurityPostgresqlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringIntegration {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    public static HttpResponse latestHttpResponse;
    private final String host = "http://localhost:8080/";

    public void executeGet(String url, String jwt) throws IOException {
        HttpGet request = new HttpGet(host + url);
        request.addHeader("Accept", "application/json");
        if (jwt != null) {
            request.addHeader("Authorization", "Bearer " + jwt);
        }
        latestHttpResponse = httpClient.execute(request);
    }

    public void executePost(String url, String jwt, String payload) throws IOException {
        HttpPost request = new HttpPost(host + url);
        request.addHeader("content-type", "application/json");
        if (jwt != null) {
            request.addHeader("Authorization", "Bearer " + jwt);
        }
        request.setEntity(new StringEntity(payload));
        latestHttpResponse = httpClient.execute(request);
    }

    public void executePut(String url, String jwt, String payload) throws IOException {
        HttpPut request = new HttpPut(host + url);
        request.addHeader("content-type", "application/json");
        if (jwt != null) {
            request.addHeader("Authorization", "Bearer " + jwt);
        }
        request.setEntity(new StringEntity(payload));
        latestHttpResponse = httpClient.execute(request);
    }
}