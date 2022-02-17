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
    private List<String> choices;

    @NotEmpty
    private String answer;


    public MQCRequest() {

    }

    public MQCRequest(String name, String description, String answer, List<String> choices) {
        this.name = name;
        this.description = description;
        this.choices = choices;
        this.answer = answer;
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

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
