package fr.uca.springbootstrap.models;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class Course extends Resource{

    Set<String> text;

    public Course() {
    }

    public Course(String name) {
        super(name);
    }

    public Set<String> getText() {
        return text;
    }

    public void setText(Set<String> text) {
        this.text = text;
    }
}
