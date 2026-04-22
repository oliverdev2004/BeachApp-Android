package com.example.beachseatreservationapp;

public class Beach {
    private int id;
    private String name;
    private String location;
    private String description;
    private int seats;


    public Beach(int id, String name, String location, String description,int seats) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.seats=seats;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
    public int getSeats() {
        return seats;
    }

}