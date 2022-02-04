package Models.Users;

import Models.Module;

import javax.persistence.*;
import java.util.ArrayList;


@Entity
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,length = 100)
    protected String firstName;
    @Column(nullable = false,length = 100)
    protected String lastName;
    @Column(nullable = false,length = 100)
    protected String login;
    @Column(nullable = false,length = 100)
    protected String password;


    public User(Long id, String firstName, String lastName, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    protected ArrayList<Module> modules = new ArrayList<>();

    public String getLogin() {
        return login;
    }

    public ArrayList<Module> getModules(){
        return modules;
    }

    public void setLogin(String login){
        this.login = login;
    }


}
