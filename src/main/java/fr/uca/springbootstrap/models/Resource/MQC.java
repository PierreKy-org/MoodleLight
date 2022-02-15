package fr.uca.springbootstrap.models.Resource;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "question")
public class MQC extends Question {

    @NotNull
    private int correct;

    public MQC() {
        super();
    }

    public MQC(String name, String description, int correct) {
        super(name, description);
        this.correct = correct;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    @Override
    public boolean validate(String answer) {
        return correct == Long.parseLong(answer);
    }
}
