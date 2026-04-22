package com.example.beachseatreservationapp;

public class ReservationModel {
    private int id;
    private int beach_id;
    private String beach_name;
    private String user_name;
    private String reservation_date;
    private int seats;
    private String seat_type;

    public ReservationModel(int id, int beach_id, String beach_name, String user_name, String reservation_date, int seats,String seat_type) {
        this.id = id;
        this.beach_id = beach_id;
        this.beach_name = beach_name;
        this.user_name = user_name;
        this.reservation_date = reservation_date;
        this.seats = seats;
        this.seat_type=seat_type;
    }

    public int getId() { return id; }
    public int getBeachId() { return beach_id; }
    public String getBeachName() { return beach_name; }
    public String getUserName() { return user_name; }
    public String getReservationDate() { return reservation_date; }
    public int getSeats() { return seats; }
    public String getSeat_type(){
        return seat_type;
    }
}