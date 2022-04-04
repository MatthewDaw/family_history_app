package com.example.sharedcodemodule.request;

/**
 * register request takes a register request, checks if valid, then registers a user
 */

public class registerRequest {
    /**
     * user's user name
     */
    String userName;/**
     * user's password
     */

    String password;
    /**
     * user's email
     */
    String email;
    /**
     * user's first name
     */
    String firstName;
    /**
     * user's last name
     */
    String lastName;
    /**
     * user's gender
     */
    String gender;

    /**
     * creates register request
     * @param userName user's username
     * @param password user's password
     * @param email user's email
     * @param firstName user's firstname
     * @param lastName user's lastname
     * @param gender user's gender
     */

    public registerRequest(String userName, String password, String email, String firstName, String lastName, String gender){
        this.userName = userName;
        this.password = password;
        this.email  = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUserName(){ return this.userName;}
    public String getPassword(){ return this.password;}
    public String getEmail(){ return this.email;}
    public String getFirstName(){ return this.firstName;}
    public String getLastName(){ return this.lastName;}
    public String getGender(){ return this.gender;}

}
