package Models;

import Models.Users.User;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private final String name;
    private List<User> usersList;

    public Module(String name) {
        this.name = name;
        usersList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void addAUser(User u){
        this.usersList.add(u);
    }

    public void addUsertoTheList(User user){
        usersList.add(user);
    }
    public void removeUserFromTheList(User user){
        usersList.remove(user);
    }
}
