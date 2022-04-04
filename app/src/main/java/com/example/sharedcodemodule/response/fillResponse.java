package com.example.sharedcodemodule.response;

/**
 * fill response object to fill request
 */

public class fillResponse {
    String message;
    boolean success;

    /**
     * fill response constructor
     * @param message message to send to user
     * @param success true if successful, false if otherwise
     */

    public fillResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public String getMessage(){return this.message;}

    public boolean getSuccess() {
        return success;
    }
}
