package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotNull;

public class RegistrationRequest {
    @NotNull
    private long userId;

    @NotNull
    private long moduleId;

    @NotNull
    private String resourceName;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String userId, String moduleId) {
        this.userId = Long.parseLong(userId);
        this.moduleId = Long.parseLong(moduleId);
    }
    public RegistrationRequest(String resourceName) {
        this.resourceName = resourceName;
    }



    public long getUserId() {
        return userId;
    }

    public long getModuleId() {
        return moduleId;
    }
}
