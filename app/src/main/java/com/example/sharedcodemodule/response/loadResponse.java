package com.example.sharedcodemodule.response;

/**
 * class for responses related to load API calls
 */

public class loadResponse {
    private String message;
    private boolean success;

    /**
     * load request object
     * @param message message to send to user
     * @param success true if successful load, false if otherwise
     */

    public loadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {return this.message;}
    public boolean getSuccess() {return this.success;}
}
