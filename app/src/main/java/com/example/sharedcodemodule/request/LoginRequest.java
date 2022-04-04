package com.example.sharedcodemodule.request;

/**
 * login request object
 */

public class LoginRequest {
    String userName;
    String password;

    /**
     * creates loginrequest object
     * @param userName user's username
     * @param password user's password
     */
    public LoginRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() { return this.userName; }
    public String getPassword() { return this.password; }


}
