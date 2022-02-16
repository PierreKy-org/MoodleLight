package fr.uca.springbootstrap.payload.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class JythonRequest {

    @NotEmpty
    private List<Integer> inputs;

    @NotEmpty
    private List<String> outputs;

    @NotNull
    private String code;


    public JythonRequest() {

    }

    public JythonRequest(List<Integer> inputs, List<String> outputs, String code) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"inputs\":[");
        sb.append(inputs.stream().map(Object::toString).reduce("", (subtotal, element) -> subtotal + element + ","));
        sb.deleteCharAt(sb.length()-1);
        sb.append("]").append(",\"outputs\":[");
        sb.append(outputs.stream().map(Object::toString).reduce("", (subtotal, element) -> subtotal + element + ","));
        sb.deleteCharAt(sb.length()-1);
        sb.append("],\"code\":\"").append(code).append("\"}");
        return sb.toString().replace("\n", "\\n").replace("\t","\\t");
    }
}
