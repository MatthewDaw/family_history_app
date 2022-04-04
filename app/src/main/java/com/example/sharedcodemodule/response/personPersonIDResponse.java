package com.example.sharedcodemodule.response;

/**
 * Class for person result
 */

public class personPersonIDResponse {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouceID;
    private boolean success = false;

    /**
     * Constructor for person result object
     * @param associatedUsername associated name of user
     * @param personID person id
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouceID
     * @param success true if successful creation, false if otherwise
     */

    public personPersonIDResponse(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouceID, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouceID = spouceID;
        this.success = success;
    }

    private String message;

    public personPersonIDResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public String getAssociatedUsername() { return this.associatedUsername; }
    public String getPersonID() { return this.personID; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getGender() { return this.getGender(); }
    public String getFatherID() { return this.fatherID; }
    public String getMotherID() { return this.motherID; }
    public String getSpouceID() { return this.spouceID; }
    public boolean getSuccess() { return this.success; }
    public String getMessage() {return this.message;}




}
