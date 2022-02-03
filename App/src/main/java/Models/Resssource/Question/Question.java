package Models.Resssource.Question;

import Models.Resssource.Ressource;

public abstract class Question extends Ressource {
    public Question(int id, String name, String description) {
        super(id, name, description);
    }
}
