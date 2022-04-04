package com.example.mygeoquiz.struct;

import com.example.mygeoquiz.activities.MainActivity;
import com.example.sharedcodemodule.response.eventResponse;
import com.example.sharedcodemodule.response.personResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.sharedcodemodule.model.event;
import com.example.sharedcodemodule.model.person;

public class familyData {

    public Map<String, person> personMap;
    public Map<String, event> eventMap;

    public person userPerson;

    public personResponse personResponseG;
    public eventResponse eventResponseG;

    public ArrayList<recyclerViewData> recycleDList;

    public familyData(personResponse personResponse, eventResponse eventResponse, String userPersonID){

        personResponseG = personResponse;
        eventResponseG = eventResponse;

        this.personMap = new HashMap<String, person>();
        this.eventMap = new HashMap<String, event>();

        this.recycleDList = new ArrayList<recyclerViewData>();

        loopPersons(personResponse);
        loopEvents(eventResponse);
        this.userPerson = personMap.get(userPersonID);

        userPerson.setFamilySide("user");
        if(userPerson.getFatherID()!= null && !userPerson.getFatherID().equals("")){
            recursiveSetFamilySide(getDad(userPerson), "dad");
        }
        if(userPerson.getMotherID()!= null && !userPerson.getMotherID().equals("")){
            recursiveSetFamilySide(getMom(userPerson), "mom");
        }

    }



    public void recursiveSetFamilySide(person person, String side){
        if(person == null){
            return;
        }
        person.setFamilySide(side);
        if(person.getFatherID()!= null && !person.getFatherID().equals("")){
            recursiveSetFamilySide(personMap.get(person.getFatherID()), side);
        }
        if(person.getMotherID()!= null && !person.getMotherID().equals("")){
            recursiveSetFamilySide(personMap.get(person.getMotherID()), side);
        }
    }

    public void loopPersons(personResponse personResponse){

        for(int i = 0; i < personResponse.getPersons().length; i++){
            personResponse.getPersons()[i].events = new ArrayList<String>();
            this.personMap.put(personResponse.getPersons()[i].getPersonID(), personResponse.getPersons()[i]);
        }
        for(int i = 0; i < personResponse.getPersons().length; i++){
            if(personMap.containsKey(personResponse.getPersons()[i].getPersonID())) {
                if(personMap.get(personResponse.getPersons()[i].getPersonID()) != null ) {

                    person person = personMap.get(personResponse.getPersons()[i].getPersonID());
                    personMap.get(personResponse.getPersons()[i].getPersonID()).addToFamilyList(getSpouce(personResponse.getPersons()[i]), "spouce");
                    personMap.get(personResponse.getPersons()[i].getPersonID()).addToFamilyList(getDad(personResponse.getPersons()[i]), "father");
                    personMap.get(personResponse.getPersons()[i].getPersonID()).addToFamilyList(getMom(personResponse.getPersons()[i]), "mother");

                if(getMom(personResponse.getPersons()[i]) != null) {
                    getMom(personResponse.getPersons()[i]).addToFamilyList(personResponse.getPersons()[i], "child");
                }

                if(getDad(personResponse.getPersons()[i]) != null) {
                    getDad(personResponse.getPersons()[i]).addToFamilyList(personResponse.getPersons()[i], "child");
                }
            }
            }
        }
    }

    public void loopEvents(eventResponse eventResponse){
        for(int i = 0; i < eventResponse.getEvents().length; i++){
            eventResponse.getEvents()[i].chooseEventColor();
            if(eventResponse.getEvents()[i].getEventCoord() == null){
                eventResponse.getEvents()[i].setEventCoord();
            }

            this.eventMap.put(eventResponse.getEvents()[i].getEventID(), eventResponse.getEvents()[i]);
            if(eventResponse.getEvents()[i].getEventType().toLowerCase().equals("birth")){
                this.personMap.get(eventResponse.getEvents()[i].getPersonID()).birthID = eventResponse.getEvents()[i].getEventID();
            }
            if(eventResponse.getEvents()[i].getEventType().toLowerCase().equals("death")){
                this.personMap.get(eventResponse.getEvents()[i].getPersonID()).deathID = eventResponse.getEvents()[i].getEventID();
            }
            this.personMap.get(eventResponse.getEvents()[i].getPersonID()).events.add(eventResponse.getEvents()[i].getEventID());
            this.personMap.get(eventResponse.getEvents()[i].getPersonID()).addToEvent(eventResponse.getEvents()[i]);
        }
    }

    public boolean searchString(String compare, String input, Boolean type, String id){
        compare = compare.toLowerCase();
        input = input.toLowerCase();
        if(compare.contains(input)){
            if(type){
                recycleDList.add(new recyclerViewData(id, "person"));
            } else {
                recycleDList.add(new recyclerViewData(id, "event"));
            }
            return true;
        }
        return false;
    }

    public boolean checkEvent(event event) {
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

    public void getSearchResults(String input, boolean[] settings){
        recycleDList.clear();

        if(input == null || input.equals("")){
            return;
        }

        Iterator<Map.Entry<String, person>> iterator = personMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, person> entry = iterator.next();
                 if(!searchString(entry.getValue().getFirstName(), input, true, entry.getValue().getPersonID())){
                    searchString(entry.getValue().getLastName(), input, true, entry.getValue().getPersonID());
                }
        }
        Iterator<Map.Entry<String, event>> iterator2 = eventMap.entrySet().iterator();
        while(iterator2.hasNext()){
            Map.Entry<String, event> entry = iterator2.next();
            if(checkEvent(entry.getValue())) {
                if(!searchString(entry.getValue().getCountry(), input, false, entry.getValue().getEventID())){
                    if(!searchString(entry.getValue().getCity(), input, false, entry.getValue().getEventID())){
                        if(!searchString(entry.getValue().getEventType(), input, false, entry.getValue().getEventID())){
                            searchString(Integer.toString(entry.getValue().getYear()), input, false, entry.getValue().getEventID());
                        }
                    }
                }
            }
        }
    }

    public person getDad(person person){
        if(person.getFatherID() != null){
            return personMap.get(person.getFatherID());
        }
        return null;
    }
    public person getMom(person person){
        if(person.getFatherID() != null){
            return personMap.get(person.getMotherID());
        }
        return null;
    }

    public person getSpouce(person person){
        if(person.getSpouceID() != null){
            return personMap.get(person.getSpouceID());
        }
        return null;
    }

}
