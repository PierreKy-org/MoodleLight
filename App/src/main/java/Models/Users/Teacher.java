package Models.Users;

import Models.Module;
public class Teacher extends User{

    public Teacher(int id, String firstName, String lastName, String login, String password) {
        super(id, firstName, lastName, login, password);
    }

    public void addAUser(User u, Module m){
        m.addAUser(u);
    }
}
