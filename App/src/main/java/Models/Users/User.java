package Models.Users;

import Models.Module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
@Table(name = "users",
                uniqueConstraints = {
                        @UniqueConstraint(columnNames = "login")
                })


public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @NotBlank
    @Size(max = 50)
    protected String firstName;

    @NotBlank
    @Size(max = 50)
    protected String lastName;

    @NotBlank
    @Size(max = 50)
    protected String login;

    @NotBlank
    @Size(max = 50)
    protected String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "user_modules",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @Join Column(name = "module_id"))
    protected Set<Module> modules = new HashSet<>();



    public User(int id, String firstName, String lastName, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }


}
