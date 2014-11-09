package com.example.ermanyafay.photomapnavigation.logic;

import java.util.ArrayList;

/**
 * Created by Erman Yafay on 09.11.2014.
 */
public abstract class RegisteredUser extends UnregisteredUser {

    //Fields
    private int userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private ArrayList<Event> ownEvents, contributedEvents, onGoingEvents;

    //Constructor
    public RegisteredUser(int userId, String email, String password, String firstName, String lastName, ArrayList<Event> accEvent) {
        super(accEvent);
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

        ownEvents = new ArrayList<Event>();
        contributedEvents = new ArrayList<Event>();
        onGoingEvents = new ArrayList<Event>();
    }

    //Getters
    public int getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public ArrayList<Event> getOwnEvents() {
        return ownEvents;
    }
    public ArrayList<Event> getContributedEvents() {
        return contributedEvents;
    }
    public ArrayList<Event> getOnGoingEvents() {
        return onGoingEvents;
    }

    //Setters
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setContributableEvents(ArrayList<Event> contributableEvents) {
        this.contributedEvents = contributableEvents;
    }
    public void setOwnEvents(ArrayList<Event> ownEvents) {
        this.ownEvents = ownEvents;
    }
    public void setOnGoingEvents(ArrayList<Event> onGoingEvents) {
        this.onGoingEvents = onGoingEvents;
    }

    public void createEvent(Event e) {
        ownEvents.add(e);

        if (e.eventGoing) {
            onGoingEvents.add(e);
        }
    }

}



