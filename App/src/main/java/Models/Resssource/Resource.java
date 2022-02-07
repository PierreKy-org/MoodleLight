package Models.Resssource;

public abstract class Resource {
    private final int id;
    private final String name;
    private final String description;

    public Resource(int id, String name, String description) {
       this.id = id;
       this.name = name;
       this.description = description;
    }
}
