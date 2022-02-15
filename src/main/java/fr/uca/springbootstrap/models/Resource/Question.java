package fr.uca.springbootstrap.models.Resource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @ElementCollection
    @CollectionTable(name = "question_answers")
    private Set<String> good_answers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="question")
    private Set<Answer> users_answers;

    public Question() {
        good_answers = new HashSet<>();
        users_answers = new HashSet<>();
    }

    public Question(String name, String description) {
        this.name = name;
        this.description = description;
        good_answers = new HashSet<>();
        users_answers = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<String> getGood_answers() {
        return good_answers;
    }

    public void setGood_answers(Set<String> good_answers) {
        this.good_answers = good_answers;
    }

    public Set<Answer> getUsers_answers() {
        return users_answers;
    }

    public void setUsers_answers(Set<Answer> users_answers) {
        this.users_answers = users_answers;
    }

    public boolean validate(String answer){
        return good_answers.contains(answer);
    }
}