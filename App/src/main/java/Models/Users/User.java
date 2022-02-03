package Models.Users;

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

}
