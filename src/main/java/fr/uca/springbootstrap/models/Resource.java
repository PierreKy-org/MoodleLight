package fr.uca.springbootstrap.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import fr.uca.springbootstrap.models.Module;

@Entity
@Table(name = "resource")
public class Resource{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name="module_id")
    private Module module;

    public Resource() {

    }

    public Resource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ", \"name\":" + name + "}";
    }
}
