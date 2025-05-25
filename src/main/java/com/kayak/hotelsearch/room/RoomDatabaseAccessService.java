package com.kayak.hotelsearch.room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomDatabaseAccessService {
    private static RoomDatabaseAccessService instance;

    // Pretend this is a database
    private static Map<Integer, Room> roomCache = new HashMap<>();

    private RoomDatabaseAccessService() {
        // private constructor to prevent instantiation
    }
    public static RoomDatabaseAccessService getInstance() {
        if (instance == null) {
            instance = new RoomDatabaseAccessService();
            System.out.println("RoomDatabaseAccessService instance = " + instance);
            instance.initializeRooms();
        }
        return instance;
    }
    private void initializeRooms() {

            Room[] rooms = {
             new Room(101, RoomType.SINGLE, 100.0, true),
             new Room(102, RoomType.DOUBLE, 200.0, true),
             new Room(103, RoomType.SINGLE, 100.0, false),
             new Room(104, RoomType.DOUBLE, 200.0, true),
             new Room(105, RoomType.DELUXE, 400.0, false),
             new Room(106, RoomType.STANDARD, 200.0, true),
             new Room(107, RoomType.SUITE, 600.0, false)
            };

            for(Room room:rooms) {
                roomCache.put(room.getRoomNumber(), room);
            }

    }

    public Room loadRoom(int roomNumber) {
        // Simulate a database access
        System.out.println("Loading room " + roomNumber + " from database");
        Room room = roomCache.get(roomNumber);
        return room;
    }
}
