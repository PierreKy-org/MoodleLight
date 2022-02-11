package fr.uca.springbootstrap.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static fr.uca.springbootstrap.models.ERole.ROLE_TEACHER;

@Entity
@Table(name = "modules")
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

    public int getNbrOfTeacher() {
        AtomicInteger nbrOfTeacher = new AtomicInteger();
        for (User user : participants) {
            user.getRoles().forEach(role -> {
                if (role.getName().equals(ROLE_TEACHER)) nbrOfTeacher.addAndGet(1);
            });
        }
        return nbrOfTeacher.get();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ", \"name\":" + name + "}";
    }
}