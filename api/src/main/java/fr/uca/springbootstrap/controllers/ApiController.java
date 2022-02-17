package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.payload.request.JythonRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static fr.uca.springbootstrap.SpringBootSecurityPostgresqlApplication.RUNNER;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ApiController {

    @GetMapping("/api/doc")
    public String welcome() {
        try {
            InputStream inputStream = new ClassPathResource("static/index.html").getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");

            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            return "Aucune documentation disponible";
        }
    }

    @GetMapping("/api/test")
    public String test() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://"+RUNNER+":8080/jython/test");
        try {
            HttpResponse latestHttpResponse = httpClient.execute(request);
            String response = EntityUtils.toString(latestHttpResponse.getEntity());
            System.out.println(response);
            return response;
        } catch (IOException e) {
            return "ERROR";
        }
    }

}
