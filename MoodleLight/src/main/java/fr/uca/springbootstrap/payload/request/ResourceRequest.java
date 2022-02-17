package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;

public class ResourceRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String type;

    public ResourceRequest() {

    }

    public ResourceRequest(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
