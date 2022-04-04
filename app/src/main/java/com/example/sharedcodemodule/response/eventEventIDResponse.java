package com.example.sharedcodemodule.response;

/**
 * class for event responses for API call /event/[eventID]
 */

public class eventEventIDResponse {

    private String associatedUsername;
    private String eventID;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private boolean success;

    /**
     * Constructor for successful response object
     * @param associatedUsername associated user name
     * @param eventID event id
     * @param personID person event
     * @param latitude double of latitude
     * @param longitude double fo longitude
     * @param country country of event
     * @param city city of event
     * @param eventType type of event
     * @param year integer for year of event
     * @param success true if load successful, false if otherwise
     */

    public eventEventIDResponse(String associatedUsername, String eventID, String personID, double latitude, double longitude, String country, String city, String eventType, int year, boolean success) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getYear() {
        return year;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPersonID() {
        return personID;
    }

    private String message;

    /**
     * constructor for error reponse object
     * @param message message to send to user
     * @param success true if successful, false if otherwise
     */

    public eventEventIDResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
