/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates how booking requests are processed
 * and rooms are safely allocated while preventing
 * duplicate assignments.
 *
 * @version 6.1
 */

import java.util.*;

/* Reservation Object */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* Inventory Management */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {

        int available = inventory.get(roomType);
        inventory.put(roomType, available - 1);
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory");
        System.out.println("--------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* Booking Allocation Service */

class BookingService {

    private Queue<Reservation> requestQueue;
    private Set<String> allocatedRooms;
    private HashMap<String, Set<String>> roomAllocations;

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {

        this.inventory = inventory;

        requestQueue = new LinkedList<>();
        allocatedRooms = new HashSet<>();
        roomAllocations = new HashMap<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRooms.add(roomId);

                roomAllocations
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decrementRoom(roomType);

                System.out.println(
                        "Reservation confirmed for "
                                + reservation.getGuestName()
                                + " | Room ID: "
                                + roomId
                );

            } else {

                System.out.println(
                        "Reservation failed for "
                                + reservation.getGuestName()
                                + " | No rooms available"
                );
            }
        }
    }

    private String generateRoomId(String roomType) {

        String roomId;

        do {
            roomId = roomType.substring(0, 2).toUpperCase()
                    + "-"
                    + (100 + allocatedRooms.size());
        } while (allocatedRooms.contains(roomId));

        return roomId;
    }

    public void displayAllocations() {

        System.out.println("\nRoom Allocations");
        System.out.println("----------------");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {

            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

/* Application Entry */

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 6.1");
        System.out.println("=================================\n");

        RoomInventory inventory = new RoomInventory();

        BookingService bookingService = new BookingService(inventory);

        bookingService.addRequest(new Reservation("Alice", "Single Room"));
        bookingService.addRequest(new Reservation("Bob", "Double Room"));
        bookingService.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingService.addRequest(new Reservation("David", "Suite Room"));

        bookingService.processBookings();

        bookingService.displayAllocations();

        inventory.displayInventory();
    }
}