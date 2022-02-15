package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class OpenQuestionRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    private List<String> answers;


    public OpenQuestionRequest() {

    }

    public OpenQuestionRequest(String name, String description, List<String> answers) {
        this.name = name;
        this.description = description;
        this.answers = answers;
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
