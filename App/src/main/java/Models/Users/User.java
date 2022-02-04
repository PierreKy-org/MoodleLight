package Models.Users;

import Models.Module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users",
                uniqueConstraints = {
                        @UniqueConstraint(columnNames = "login")
                })
public abstract class User {

    @NotBlank
    @Size(max = 50)
    protected String login;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "user_modules",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @Join Column(name = "module_id"))
    protected Set<Module> modules = new HashSet<>();

    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Module> getModules() { return modules;}

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }


}
