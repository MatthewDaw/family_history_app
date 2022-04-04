package com.example.sharedcodemodule.response;

/**
 * Generate login response
 */

public class registerResponse {
    String authToken;
    String userName;
    String personID;
    boolean success;
    String message;

    /**
     * If login is successful, this constructor will be called
     * @param authToken user authtoken
     * @param userName user's username
     * @param personID user's person id
     */

    public registerResponse(com.example.sharedcodemodule.model.authToken authToken, String userName, String personID){
        this.authToken = authToken.getTokenID();
        this.userName = userName;
        this.personID = personID;
        this.success = true;
    }

    public String getAuthToken() {return authToken;}
    public String getUserName() {return this.userName;}
    public String getPersonID() {return this.personID;}
    public boolean getSuccess() {return this.success;}
    /**
     * If login is uncessful this constructor will be called
     * @param message error message to send to user
     */

    public registerResponse(String message){
        this.message = message;
        this.success = false;

    }
    public String getMessage(){return this.message;}
}
