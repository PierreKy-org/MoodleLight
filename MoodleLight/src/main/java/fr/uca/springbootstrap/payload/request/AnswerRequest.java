package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;

public class AnswerRequest {
    @NotBlank
    private String answer;

    public AnswerRequest() {
    }

    public AnswerRequest(String answer) {
        this.answer = answer;
    }

    public AnswerRequest(Long answer) {
        this.answer = Long.toString(answer);
    }

    public String getAnswer() {
        return answer;
    }
}
