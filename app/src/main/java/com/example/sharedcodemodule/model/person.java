package com.example.sharedcodemodule.model;

import com.example.mygeoquiz.activities.MainActivity;
import com.example.mygeoquiz.struct.pEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * person is ancestor of given user
 */

public class person {
    String personID;
    String associatedUsername;
    String firstName;
    String lastName;
    String gender;
    String fatherID;
    String motherID;
    String spouseID;

    private String familySide;

    public PriorityQueue<pEntry> familyList = new PriorityQueue<pEntry>();

    public ArrayList<String> events = new ArrayList<String>();

    public PriorityQueue<pEntry> eventsTemp = new PriorityQueue<pEntry>();

    public String birthID;
    public String deathID;


    /**
     * constructs person
     *
     * @param associatedUserName
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouceID
     */

    public person(String personID, String associatedUserName, String firstName, String lastName, String gender, String fatherID, String motherID, String spouceID) {
        this.personID = personID;
        this.associatedUsername = associatedUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouceID;
    }

    public String getRelation(String personID) {
        if (personID.equals(spouseID)) {
            return "Spouce";
        }
        if (personID.equals(fatherID)) {
            return "Father";
        }
        if (personID.equals(motherID)) {
            return "Mother";
        }
        return "Child";

    }

    public void addToFamilyList(person person, String relation) {
        if (person == null) {
            return;
        }
        if (familyList == null) {
            familyList = new PriorityQueue<pEntry>();
        }
        if (relation.toLowerCase().equals("father")) {
            pEntry myEntry = new pEntry(0, person.getPersonID());
            familyList.add(myEntry);
            fatherID = person.getPersonID();
            return;
        }
        if (relation.toLowerCase().equals("mother")) {
            pEntry myEntry = new pEntry(1, person.getPersonID());
            familyList.add(myEntry);
            motherID = person.getPersonID();
            return;
        }
        if (relation.toLowerCase().equals("spouce")) {

            pEntry myEntry = new pEntry(2, person.getPersonID());
            familyList.add(myEntry);
            spouseID = person.getPersonID();
            return;
        }
        if (relation.toLowerCase().equals("child")) {
            pEntry myEntry = new pEntry(3, person.getPersonID());
            familyList.add(myEntry);
            return;
        }
        pEntry myEntry = new pEntry(4, person.getPersonID());
        familyList.add(myEntry);
        return;
    }

    public void addToEvent(event event) {
        if (eventsTemp == null) {
            eventsTemp = new PriorityQueue<pEntry>();
        }
        if (event.getEventType().toLowerCase().equals("birth")) {
            pEntry myEntry = new pEntry(0, event.getEventID());
            eventsTemp.add(myEntry);
            return;
        }
        pEntry myEntry = new pEntry(event.getYear(), event.getEventID());
        eventsTemp.add(myEntry);
    }

    public String getTopEventID() {
        if (eventsTemp.size() > 0) {
            return eventsTemp.peek().value;
        }
        return null;
    }

    private boolean checkEvent(event event) {
        person person = MainActivity.familyData.personMap.get(event.getPersonID());
        if (person == null) {
            return false;
        }
        if (person.getGender() != null){
            if (!MainActivity.settings[5] && person.getGender().equals("m")) {
                return false;
            } else if (!MainActivity.settings[6] && person.getGender().equals("f")) {
                return false;
            }
        }
        if(person.getFamilySide() == null){
            return true;
        }
        if(!MainActivity.settings[3] && person.getFamilySide().equals("dad")){
            return false;
        }
        else if(!MainActivity.settings[4] && person.getFamilySide().equals("mom")){
            return false;
        }
        return true;
    }

    public List<String> getEvents() {

    List<String> lifeEvents = new ArrayList<String>();
    if(this.eventsTemp !=null)
    {
        Iterator value = this.eventsTemp.iterator();
        pEntry entry1 = null;
        while (value.hasNext()) {
            entry1 = (pEntry) value.next();
            event event = MainActivity.familyData.eventMap.get(entry1.value);
            if (checkEvent(event)) {
                lifeEvents.add(event.getEventID());
            }
        }
    }
        return lifeEvents;
}


    public List<String> getPersonData(){
        List<String> family = new ArrayList<String>();
        if(this.familyList != null) {
            Iterator value = this.familyList.iterator();
            pEntry entry1 = null;

            while (value.hasNext()) {
                entry1 = (pEntry) value.next();
                person person = MainActivity.familyData.personMap.get(entry1.value);
                family.add(person.getPersonID());
            }
        }
        return family;
    }

    public String getPersonID() {
        return this.personID;
    }

    public String getFatherID() {
        return this.fatherID;
    }

    public String getMotherID() {
        return this.motherID;
    }

    public String getSpouceID() {
        return this.spouseID;
    }

    public String getAssociatedUserName() {
        return this.associatedUsername;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public String getFamilySide() {return this.familySide;}


    public void setFatherID(String i) {
        this.fatherID = i;
    }

    public void setMotherID(String i) {
        this.motherID = i;
    }

    public void setSpouceID(String i) {
        this.spouseID = i;
    }

    public void setAssociatedUserName(String s) {
        this.associatedUsername = s;
    }

    public void setFirstName(String s) {
        this.firstName = s;
    }

    public void setLastName(String s) {
        this.lastName = s;
    }

    public void setGender(String s) {
        this.gender = s;
    }

    public void setFamilySide(String s) {this.familySide = s;}


    /**
     * check if two objects are equal to each other
     * @param o object to compare against
     * @return true if objcts are the same, false if otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof person) {
            person oPerson = (person) o;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getFatherID().equals(getFatherID()) &&
                    oPerson.getMotherID().equals(getMotherID()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getSpouceID().equals(getSpouceID()) &&
                    oPerson.getAssociatedUserName().equals(getAssociatedUserName()) &&
                    oPerson.getGender().equals(getGender());
        } else {
            return false;
        }
    }
}
