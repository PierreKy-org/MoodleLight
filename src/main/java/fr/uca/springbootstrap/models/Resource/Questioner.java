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
    @CollectionTable(name = "questions")
    private Set<String> questions;

    public Questioner() {
        questions = new HashSet<>();
    }

    public Questioner(String name) {
        super(name);
        this.questions = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getTexts() {
        return questions;
    }

    public void setTexts(Set<String> texts) {
        this.questions = texts;
    }

}
