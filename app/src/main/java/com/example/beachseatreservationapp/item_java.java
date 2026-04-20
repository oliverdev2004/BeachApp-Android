package com.example.beachseatreservationapp;

public class item_java {
    private String title;
    private int imageId;
    private String description;

    public item_java(String title, int imageId, String description ) {
        this.title = title;
        this.imageId=imageId;
        this.description=description;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDescription() {
        return description;
    }
}
