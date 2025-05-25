package com.kayak.hotelsearch.room;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private double price;
    private boolean isAvailable;
    private String bookedByGuest;

    public Room(int roomNumber, RoomType roomType, double price, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public Room() {

    }

    public boolean bookRoom(String guestName) {
        if (isAvailable == true) {
            isAvailable = false;
            bookedByGuest = guestName;
            return true; // Booking successful
        } else {
            return false;
        }
    }

    public boolean unbookRoom() {
        if (isAvailable == false) {
            isAvailable = true;
            return true; // Unbooking successful
        } else {
            return false;
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getGuest() {return bookedByGuest;}
}
