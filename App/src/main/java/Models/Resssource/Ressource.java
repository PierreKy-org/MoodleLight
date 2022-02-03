package Models.Resssource;

public abstract class Ressource {
    private final int id;
    private final String name;
    private final String description;

    public Ressource(int id,String name,String description) {
       this.id = id;
       this.name = name;
       this.description = description;
    }
}
