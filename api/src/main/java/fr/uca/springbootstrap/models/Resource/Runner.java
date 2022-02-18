package fr.uca.springbootstrap.models.Resource;

import fr.uca.springbootstrap.payload.request.JythonRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Runner extends Question {

    @ElementCollection
    @CollectionTable(name = "runner_data")
    private List<Integer> inputs;

    public Runner() {
        super();
    }

    public Runner(String name, String description, List<Integer> inputs) {
        super(name, description);
        this.inputs = inputs;
    }

    public List<Integer> getInputs() {
        return inputs;
    }

    public void setInputs(List<Integer> inputs) {
        this.inputs = inputs;
    }

    public void addInput(Integer input){inputs.add(input);}

    @Override
    public boolean validate(String answer) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://runner:8080/jython/run");
        request.addHeader("content-type", "application/json");
        JythonRequest jythonRequest = new JythonRequest(inputs, new ArrayList<>(getAnswers()), answer);
        try {
            request.setEntity(new StringEntity(jythonRequest.toString()));
            HttpResponse latestHttpResponse = httpClient.execute(request);
            String response = EntityUtils.toString(latestHttpResponse.getEntity());
            System.out.println(response);
            return response.equals("{\"message\":\"Good answer\"}");
        } catch (IOException e) {
            return false;
        }
    }
}
