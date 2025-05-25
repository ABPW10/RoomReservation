package com.kayak.hotelsearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayak.hotelsearch.booking.BookingRequest;
import com.kayak.hotelsearch.room.Room;
import com.kayak.hotelsearch.room.RoomDatabaseAccessService;

class Lock{
    private boolean lock=false;
    private String thread="";
    public boolean lock(String threadName){
        if(thread.isEmpty()){
            thread=threadName;
            lock=true;
            return true;
        }else{
            return false;
        }
    }
    public boolean unlock(String threadName){
        if(thread.equals(threadName)) {
            thread = "";
            lock = false;
            return true;
        }else{
            return false;
        }
    }
}

public class Main {

    private static final List<BookingRequest> incomingRequests = new ArrayList<>();

    public static void main(String[] args) {
        // Your name
        System.err.println("Your name");

        // This position (Java Search Engineer) requires presence in our Cambridge or Concord MA office at least
        //    3 days a week. Output your preferred office.
        System.out.println("your preferred office");
        System.out.println();
        System.out.println("Section 2: The code threw exception because another thread removed the first request element");
        System.out.println("in the array before it was accessed by the thread that is executing.");
        System.out.println("I added a lock with a thread identifier so that a thread will skip");
        System.out.println("if another thread is already accessing the element.");
        System.out.println("Only the thread that originally locked the resource can release the lock.");
        System.out.println();
        System.out.println("Section 3: Code may be more performant if the database engine also supports concurrent operation.");
        System.out.println("Performance can be optimized by improving searching method for guest names and room numbers");
        System.out.println("when the numbers of guests and rooms are big.");
        System.out.println("The code keeps threads running and takes up a lot of CPU resources.");
        System.out.println("We can use an on-demand response mechanism so the bandwidth is saved.");
        System.out.println();
        System.out.println("Section 4: There is no information stored for what room is booked by what guest,");
        System.out.println("which may be difficult to track when the request count gets big.");
        System.out.println("When initializing Rooms in the RoomDatabaseAccessService, the code should use an array and a loop");
        System.out.println("instead of having hard-coded variables for all rooms.");
        System.out.println();
        System.out.println("Section 5 Bonus Point 1: BookingRequest class can be immutable because");
        System.out.println("it does not change once created and will be processed in a very short time.");
        System.out.println("But making it immutable does not offer many benefits, for BookRequest does not involve status change.");
        System.out.println("Room classes can be immutable to ensure benefits in multithreading to prevent a room to be multi-booked.");
        System.out.println("However, it's generally not a good idea when there are cases of");
        System.out.println("cancelled request, cancelled booking, or room feature change.");
        System.out.println("A new instance of these immutable classes may have to be created in such cases.");
        System.out.println("To implement an immutable class, remove all setters and methods setting values for its fields.");
        System.out.println("Then make all fields final to prevent them from changing after construction.");
        System.out.println();
        System.out.println("Section 5 Bonus Point 2: Unit-testing RoomDatabaseAccessService:");
        System.out.println("Located in src/test/java/RoomDatabaseAccessServiceTests.java");
        System.out.println();

        Lock lock = new Lock();

        // Start threads
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        boolean lockSuccessful = lock.lock(Thread.currentThread().getName());
                        if(!lockSuccessful) {
                            Thread.sleep(0);
                            continue;
                        }
                        if (incomingRequests.isEmpty()) {
                            Thread.sleep(0);
                            //System.out.println("Unlocked" + Thread.currentThread().getName());
                            lock.unlock(Thread.currentThread().getName());
                            continue;
                        }
                        // The incoming requests is not empty. Take the first request.
                        System.out.println("Thread " + Thread.currentThread().getName() + " processing request");
                        BookingRequest request;
                        if(!incomingRequests.isEmpty()) {
                            request = incomingRequests.removeFirst();
                            lock.unlock(Thread.currentThread().getName());
                        }else{
                            Thread.sleep(0);
                            lock.unlock(Thread.currentThread().getName());
                            continue;
                        }

                        System.out.println("Thread " + Thread.currentThread().getName() + " processing request for room " + request.getRoomNumber() + " by " + request.getGuest());
                        // Process the booking request
                        bookRoom(request.getRoomNumber(), request.getGuest());
                        System.out.println("Thread " + Thread.currentThread().getName() + " finished processing request for room " + request.getRoomNumber() + " by " + request.getGuest());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }).start();
        }
        readBookingRequests("src/main/resources/booking_requests.json");

    }


    public static void bookRoom(int roomNumber, String guest) {
        Room room = RoomDatabaseAccessService.getInstance().loadRoom(roomNumber);
        if (room != null && room.isAvailable()) {
            room.bookRoom(guest);
            System.out.println("    Room " + roomNumber + " booked by " + guest);
        } else {
            System.out.println("    Room " + roomNumber + " is not available for " + guest);
        }
    }

    public static void readBookingRequests(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<BookingRequest> requests = mapper.readValue(new File(filename), new TypeReference<List<BookingRequest>>() {
            });

            for (BookingRequest request : requests) {
                try {
                    incomingRequests.add(request);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading booking requests: " + e.getMessage());
        }
    }
}
