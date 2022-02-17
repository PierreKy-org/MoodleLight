package io.javabrains.springsecurityjwt.models.Resource;

import io.javabrains.springsecurityjwt.models.Module;
import io.javabrains.springsecurityjwt.models.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "resource_visibility",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> visibility;

    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    public Resource() {
        this.visibility = new HashSet<>();
    }

    public Resource(String name,String description) {
        this.name = name;
        this.description = description;
        this.visibility = new HashSet<>();
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
