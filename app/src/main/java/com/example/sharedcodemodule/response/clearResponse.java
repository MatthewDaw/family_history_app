package com.example.sharedcodemodule.response;

/**
 * Result object of clear API call
 */

public class clearResponse {
    String message;
    boolean success;

    /**
     * Constructor for result
     * @param message message to send to user
     * @param success true if successful call, false if otherwise
     */

    public clearResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }
    public String getMessage(){
        return this.message;
    }
    public boolean getSuccess(){
        return this.success;
    }
}
