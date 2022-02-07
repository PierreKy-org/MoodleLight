package Models.Resssource.Question;

import Models.Resssource.Resource;

public abstract class Question extends Resource {
    public Question(int id, String name, String description) {
        super(id, name, description);
    }
}
