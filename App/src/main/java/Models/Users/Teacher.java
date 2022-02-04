package Models.Users;

import java.util.ArrayList;
import Models.Module;

public class Teacher extends User{
    private String name;
    private ArrayList<Module> modules;
    public Teacher(Long id, String firstName, String lastName, String login, String password) {
        super(id, firstName, lastName, login, password);
    }

    public void addAUser(User u, Module m){
        u.getModules().add(m);
        m.addAUser(u);
    }

    public void register( Module m){
        super.modules.add(m);
        m.addAUser(this);
    }
    public void removeUserFromModule(User user, Module module) {
        user.modules.remove(module);
        module.removeUserFromTheList(user);
    }


}
