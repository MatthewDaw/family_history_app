package com.example.sharedcodemodule.response;

import com.example.sharedcodemodule.model.event;

/**
 * event response
 */

public class eventResponse {

    private  event[] data;
    private boolean success;

    /**
     * Constructor for successful call
     * @param events array of event objects
     * @param success true if successful, false if not
     */

    public eventResponse(event[] events, boolean success) {
        this.data = events;
        this.success = success;
    }

    String message;

    /**
     * Constructor for error event call
     * @param message message to send to user
     * @param success true if successful, false if not
     */

    public eventResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public event[] getEvents() {return this.data;}
    public String getMessage(){return this.message;}
    public boolean getSuccess() {return this.success;}

}
