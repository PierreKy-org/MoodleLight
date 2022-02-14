package fr.uca.springbootstrap.models.Resource;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course extends Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "texts")
    private Set<String> texts;

    public Course() {
        texts = new HashSet<>();
    }

    public Course(String name) {
        super(name);
        this.texts = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getTexts() {
        return texts;
    }

    public void setTexts(Set<String> texts) {
        this.texts = texts;
    }
}