package com.example.ermanyafay.photomapnavigation.logic;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Erman Yafay on 09.11.2014.
 */
public abstract class Event {

    private int eventId;
    private Calendar createdDate, endDate;
    private String title, description;
    private Photo rootPhoto;
    private ArrayList<Photo> photos;
    private RegisteredUser creator;
    public boolean eventGoing;

    public Event(int eventId, Photo rootPhoto, RegisteredUser creator) {
        this.eventId = eventId;

        createdDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        endDate.add(Calendar.HOUR_OF_DAY, 1);
        title = "";
        description = "";

        this.rootPhoto = rootPhoto;

        photos = new ArrayList<Photo>();
        photos.add(rootPhoto);

        this.creator = creator;

        eventGoing = true;
    }

    //Getters

    public int getEventId() {
        return eventId;
    }
    public Calendar getCreatedDate() {
        return createdDate;
    }
    public Calendar getEndDate() {
        return endDate;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Photo getRootPhoto() {
        return rootPhoto;
    }
    public ArrayList<Photo> getPhotos() {
        return photos;
    }
    public RegisteredUser getCreator() {
        return creator;
    }

    //Setters

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRootPhoto(Photo rootPhoto) {
        this.rootPhoto = rootPhoto;
    }

    public void addPhoto(Photo p) {
        if(eventGoing) {
            photos.add(p);
        }
    }
}
