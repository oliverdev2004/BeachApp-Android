package com.example.beachseatreservationapp;

public class ReservationModel {
    private int id;
    private int beach_id;
    private String beach_name;
    private String user_name;
    private String reservation_date;
    private int seats;

    public ReservationModel(int id, int beach_id, String beach_name, String user_name, String reservation_date, int seats) {
        this.id = id;
        this.beach_id = beach_id;
        this.beach_name = beach_name;
        this.user_name = user_name;
        this.reservation_date = reservation_date;
        this.seats = seats;
    }

    public int getId() { return id; }
    public int getBeachId() { return beach_id; }
    public String getBeachName() { return beach_name; }
    public String getUserName() { return user_name; }
    public String getReservationDate() { return reservation_date; }
    public int getSeats() { return seats; }
}