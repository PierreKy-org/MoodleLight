package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotNull;

public class ModuleRequest {
    @NotNull
    private long userId;

    @NotNull
    private long moduleId;

    public ModuleRequest() {
    }

    public ModuleRequest(String userId, String moduleId) {
        System.out.println("IN");
        this.userId = 3L;
        this.moduleId = 2L;
        //this.userId = Long.parseLong(userId);
        //this.moduleId = Long.parseLong(moduleId);
    }

    public long getUserId() {
        return userId;
    }

    public long getModuleId() {
        return moduleId;
    }
}
