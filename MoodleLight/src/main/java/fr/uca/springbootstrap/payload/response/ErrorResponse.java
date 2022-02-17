package fr.uca.springbootstrap.payload.response;

public class ErrorResponse {
    private String error;
    //TODO faire ça au propre
    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
