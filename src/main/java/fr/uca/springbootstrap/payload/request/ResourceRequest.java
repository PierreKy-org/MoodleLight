package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;

public class ResourceRequest {
    @NotBlank
    private String name;

    public ResourceRequest() {
    }

    public ResourceRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}