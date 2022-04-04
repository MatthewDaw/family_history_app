package com.example.sharedcodemodule.request;

import com.example.sharedcodemodule.model.event;
import com.example.sharedcodemodule.model.person;
import com.example.sharedcodemodule.model.user;

/**
 * load request object
 */

public class loadRequest {

    user[] users;
    person[] persons;
    event[] events;

    /**
     * load request object
     * @param users array of users
     * @param persons array of persons
     * @param events array of events
     */

    public loadRequest(user[] users, person[] persons, event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public user[] getUsers(){ return this.users; }
    public person[] getPersons(){ return this.persons; }
    public event[] getEvents(){ return this.events; }
}
