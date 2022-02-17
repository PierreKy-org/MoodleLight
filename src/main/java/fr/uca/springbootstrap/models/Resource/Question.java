package fr.uca.springbootstrap.models.Resource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private Set<String> answers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="question")
    private List<Answer> users_answers;

    public Question() {
        answers = new HashSet<>();
        users_answers = new ArrayList<>();
    }

    public Question(String name, String description) {
        this.name = name;
        this.description = description;
        answers = new HashSet<>();
        users_answers = new ArrayList<>();
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

    public Set<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
    }

    public List<Answer> getUsers_answers() {
        return users_answers;
    }

    public void setUsers_answers(List<Answer> users_answers) {
        this.users_answers = users_answers;
    }

    public boolean validate(String answer){
        return answers.contains(answer);
    }

}