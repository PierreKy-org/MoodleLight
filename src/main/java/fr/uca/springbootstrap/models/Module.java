package fr.uca.springbootstrap.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static fr.uca.springbootstrap.models.ERole.ROLE_STUDENT;
import static fr.uca.springbootstrap.models.ERole.ROLE_TEACHER;

@Entity
@Table(	name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> participants;

    public Module() {
    }

    public Module(String name) {
        this.name = name;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public int getNbrOfTeacher(){
        int nbrOfTeacher = 0;
        for (User user : participants) {
            if (user.getRoles().contains(ROLE_TEACHER)) nbrOfTeacher+=1;
        }
        return nbrOfTeacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
