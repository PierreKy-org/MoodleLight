package Models.Users;

import java.util.ArrayList;
import Models.Module;
import Models.Module;
public class Teacher extends User{
    private String name;
    private ArrayList<Module> modules;
    public Teacher(int id, String firstName, String lastName, String login, String password) {
        super(id, firstName, lastName, login, password);
    }

    public void addAUser(User u, Module m){
        m.addAUser(u);
    }


}
