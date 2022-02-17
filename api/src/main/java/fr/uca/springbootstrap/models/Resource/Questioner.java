package fr.uca.springbootstrap.models.Resource;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questioner")
public class Questioner extends Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @OrderColumn
    @CollectionTable(name = "questioner_questions")
    private Set<Question> questions;

    public Questioner() {
        questions = new HashSet<>();
    }

    public Questioner(String name, String description) {
        super(name, description);
        this.questions = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
