package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class RunnerRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    private List<Integer> inputs;

    @NotEmpty
    private List<String> outputs;


    public RunnerRequest() {

    }

    public RunnerRequest(String name, String description, List<Integer> inputs, List<String> outputs) {
        this.name = name;
        this.description = description;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getInputs() {
        return inputs;
    }

    public void setInputs(List<Integer> inputs) {
        this.inputs = inputs;
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<String> outputs) {
        this.outputs = outputs;
    }
}
