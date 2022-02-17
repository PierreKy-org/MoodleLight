package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class MQCRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private int correct;

    @NotEmpty
    private List<String> answers;


    public MQCRequest() {

    }

    public MQCRequest(String name, String description, int correct, List<String> answers) {
        this.name = name;
        this.description = description;
        this.correct = correct;
        this.answers = answers;
    }

    public MQCRequest(List<String> choices) {
        this.name = "";
        this.description = "";
        this.answer = "";
        this.choices = choices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
