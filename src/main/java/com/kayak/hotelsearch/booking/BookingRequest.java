package com.kayak.hotelsearch.booking;

public class BookingRequest {
    private int roomNumber;
    private String guest;

    public BookingRequest() {
    }

    public BookingRequest(int roomNumber, String guest) {
        this.roomNumber = roomNumber;
        this.guest = guest;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getGuest() {
        return guest;
    }
}
