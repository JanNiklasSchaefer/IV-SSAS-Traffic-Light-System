package de.tub.aot.timeservice;

public class TimeValidationResponse {
    
    private boolean valid;
    private String message;
    
    public TimeValidationResponse() {
    }
    
    public TimeValidationResponse(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}

