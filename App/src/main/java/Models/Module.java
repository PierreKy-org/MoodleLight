package Models;

import Models.Resssource.Resource;
import Models.Users.User;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private final String name;
    private List<User> usersList;
    private List<Resource> resourcesList;

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
    public List<Resource> getResourcesList() {
        return resourcesList;
    }

    public void addAUser(User u){
        this.usersList.add(u);
    }

    public void addResource(Resource resource){resourcesList.add(resource);}
    public void removeResourceFromTheList(Resource resource){
        resourcesList.remove(resource);
    }

    public void addUsertoTheList(User user){
        usersList.add(user);
    }
    public void removeUserFromTheList(User user){
        usersList.remove(user);
    }
}
