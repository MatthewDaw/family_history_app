package com.example.sharedcodemodule.response;

/**
 * Generate login response message
 */

public class loginResponse {
    String authToken;
    String userName;
    String personID;
    boolean success;
    String message;

    /**
     * This constructor is called if login is verified
     * @param authToken user auth token
     * @param userName user's username
     * @param personID user's person id
     */
    public loginResponse(com.example.sharedcodemodule.model.authToken authToken, String userName, String personID){
        this.authToken = authToken.getTokenID();
        this.userName = userName;
        this.personID = personID;
        this.success = true;
    }

    public String getAuthToken(){return this.authToken;}
    public String getUserName(){return this.userName;}
    public String getPersonID(){return this.personID;}
    public boolean getSuccess(){return this.success;}


    /**
     * This constructor is called if login is falsified
     * @param message login error to send to user
     */

    public loginResponse(String message){
        this.message = message;
        this.success = false;
    }

    public String getMessage(){return this.message;}
}
