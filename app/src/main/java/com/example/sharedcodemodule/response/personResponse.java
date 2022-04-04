package com.example.sharedcodemodule.response;

import com.example.sharedcodemodule.model.person;

/**
 * person response object class
 */

public class personResponse {
    private person[] data;
    private boolean success;

    /**
     * person response constructor
     * @param persons array of person objects
     * @param success true if load successful, false if otherwise
     */

    public personResponse(person[] persons, boolean success) {
        this.data = persons;
        this.success = success;
    }

    String message;

    /**
     * Constructor for error response
     * @param message message to send to user
     * @param success true if successful, false if otherwise
     */

    public personResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public  String getMessage(){return this.message;}
    public boolean getSuccess() {return this.success;}
    public person[] getPersons() {return this.data;}

}
