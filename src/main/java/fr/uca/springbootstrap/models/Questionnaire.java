package fr.uca.springbootstrap.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Questionnaire extends Resource{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "questions_modules",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "questionnaire_id"))
    private Set<Question> questions;

    public Questionnaire() {
    }

    public Questionnaire(String name) {
        super(name);
    }


    public Set<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
