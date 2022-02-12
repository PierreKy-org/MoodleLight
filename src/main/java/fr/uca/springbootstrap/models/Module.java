package fr.uca.springbootstrap.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

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
    @JoinTable(	name = "user_modules",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;

    public Module() {
        participants = new HashSet<>();
    }

    public Module(String name) {

        this.name = name;
        participants = new HashSet<>();
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User[] getParticipantsOfRole(ERole role){
        return participants.stream().filter(u -> u.getRoles().stream().anyMatch(r -> r.getName().equals(role))).toArray(User[]::new);
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ", \"name\":" + name + "}";
    }
}