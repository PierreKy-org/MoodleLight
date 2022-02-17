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
    @CollectionTable(name = "course_data")
    private Set<String> texts;

    public Course() {
        texts = new HashSet<>();
    }

    public Course(String name,String description) {
        super(name,description);
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

    @Override
    public String getContent() {
        return "[" + this.getTexts().stream().reduce("", (subtotal, element) -> subtotal + element + ",") + "]";
    }
}
