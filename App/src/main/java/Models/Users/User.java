package Models.Users;

import Models.Module;

import java.util.ArrayList;

public abstract class User {
    private int id;
    protected String firstName;
    protected String lastName;
    protected String login;
    protected String password;


    public User(int id, String firstName, String lastName, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    private ArrayList<Module> modules;

    public String getLogin() {
        return login;
    }

    public ArrayList<Module> getModules(){
        return modules;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void addModules(Module module) {
        modules.add(module);
    }

    public void removeUserFromModule(User user, Module module) {
        user.modules.remove(module);
        module.getUsersList().remove(user);
    }
}
