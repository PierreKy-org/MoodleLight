package fr.uca.springbootstrap.models.Resource;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
public class MQC extends Question {

    @ElementCollection
    @CollectionTable(name = "data")
    private List<String> choices;

    public MQC() {
        super();
        choices = new ArrayList<>();
    }

    public MQC(String name, String description, List<String> choices) {
        super(name, description);
        this.choices = choices;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
