package com.example.sharedcodemodule.model;

import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 *  life event for persons
 */
public class event {
    /**
     * unique event id
     */
    private String eventID;
    /**
     * associated username for event
     */
    private String associatedUsername;
    /**
     * associated person id for event
     */
    private String personID;
    /**
     * longitude for event
     */
    private double latitude;
    /**
     *lattitude for event
     */
    private double longitude;
    /**
     * country of event
     */
    private String country;
    /**
     * city of event
     */
    private String city;
    /**
     * type of event
     */
    private String eventType;
    /**
     * year of event
     */
    private int year;

    private LatLng eventCoord;


    private float eventColor;

    private int eventColor2;

    /**
     * Event constructor
     * @param eventID
     * @param associatedUserName
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public event(String eventID, String associatedUserName, String personID, double latitude, double longitude, String country, String city, String eventType, int year ){

        DecimalFormat df = new DecimalFormat("##.####");

        this.associatedUsername = associatedUserName;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = Double.valueOf(df.format(latitude));
        this.longitude = Double.valueOf(df.format(longitude));
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;

        this.eventCoord = new LatLng(latitude,longitude);

    }

    public void chooseEventColor(){

        String compare = this.eventType.toLowerCase();

        this.eventColor2 = Color.BLUE;

        if(compare.contains("birth")){
            this.eventColor = BitmapDescriptorFactory.HUE_ROSE;
            return;
        }

        if(compare.contains("death")){
            this.eventColor = BitmapDescriptorFactory.HUE_ORANGE;
            return;
        }

        if(compare.contains("marriage")){
            this.eventColor = BitmapDescriptorFactory.HUE_AZURE;
            return;
        }
        if(compare.contains("completed asteroids")){
            this.eventColor = BitmapDescriptorFactory.HUE_MAGENTA;
            return;
        }

        if(compare.contains("graduated from byu")){
            this.eventColor = BitmapDescriptorFactory.HUE_VIOLET;
            return;
        }

        if(compare.contains("did a blackflip")){
            this.eventColor = BitmapDescriptorFactory.HUE_CYAN;
            return;
        }

        if(compare.contains("learned java")){
            this.eventColor = BitmapDescriptorFactory.HUE_GREEN;
            return;
        }

        if(compare.contains("caught a frog")){
            this.eventColor = BitmapDescriptorFactory.HUE_RED;
            return;
        }

        if(compare.contains("ate")){
            this.eventColor = BitmapDescriptorFactory.HUE_YELLOW;
            return;
        }

        if(compare.contains("learned")){
            this.eventColor = BitmapDescriptorFactory.HUE_BLUE;
            return;
        }


        this.eventColor = BitmapDescriptorFactory.HUE_BLUE;
        return;
    }

    public String getEventID(){
        return this.eventID;
    }
    public String getPersonID() { return this.personID; }
    public double getLongitude(){ return this.longitude; }
    public double getLatitude(){
        return this.latitude;
    }
    public int getYear(){ return this.year; }
    public String getAssociatedUserName(){
        return this.associatedUsername;
    }
    public String getCountry(){
        return this.country;
    }
    public String getCity(){
        return this.city;
    }
    public String getEventType(){
        return this.eventType;
    }
    public LatLng getEventCoord() { return this.eventCoord; }

    public float getEventColor() {return this.eventColor;}
    public float getEventColor2() {return this.eventColor2;}

    public void setLongitude(int longitude){
        this.longitude = longitude;
    }
    public void setLatitude(int latitude){
        this.latitude = latitude;
    }
    public void setYear(int year){
        this.year = year;
    }
    public void setAssociatedUserName(String associatedUserName){
        this.associatedUsername = associatedUserName;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setEventType(String eventType){
        this.eventType = eventType;
    }

    public void setEventCoord(){
        this.eventCoord = new LatLng(latitude, longitude);
    }

    /**
     * check if two objects are equal to each other
     * @param o object to compare against
     * @return true if objcts are the same, false if otherwise
     */

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o instanceof event){
            event oEvent = (event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getAssociatedUserName().equals(getAssociatedUserName()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        } else {
            return false;
        }
    }

}
