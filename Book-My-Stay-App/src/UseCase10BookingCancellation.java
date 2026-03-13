/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation of bookings by
 * reversing allocation state using a Stack.
 *
 * @version 10.1
 */

import java.util.*;

/* Reservation Object */

class Reservation {

    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

/* Inventory */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void incrementRoom(String roomType) {

        int count = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, count + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* Cancellation Service */

class CancellationService {

    private Map<String, Reservation> confirmedBookings;
    private Stack<String> rollbackStack;

    private RoomInventory inventory;

    public CancellationService(RoomInventory inventory) {

        this.inventory = inventory;

        confirmedBookings = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    public void addConfirmedBooking(Reservation reservation) {
        confirmedBookings.put(reservation.getReservationId(), reservation);
    }

    public void cancelBooking(String reservationId) {

        if (!confirmedBookings.containsKey(reservationId)) {

            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        Reservation reservation = confirmedBookings.remove(reservationId);

        String releasedRoomId = reservation.getRoomId();

        rollbackStack.push(releasedRoomId);

        inventory.incrementRoom(reservation.getRoomType());

        System.out.println("Reservation " + reservationId + " cancelled.");
        System.out.println("Room Released: " + releasedRoomId);
    }

    public void displayRollbackStack() {

        System.out.println("\nRollback Stack (Recently Released Rooms)");

        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}

/* Application Entry */

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 10.1");
        System.out.println("=================================\n");

        RoomInventory inventory = new RoomInventory();

        CancellationService cancellationService =
                new CancellationService(inventory);

        /* Simulated confirmed bookings */

        Reservation r1 = new Reservation("RSV101", "Single Room", "SR-201");
        Reservation r2 = new Reservation("RSV102", "Double Room", "DR-105");

        cancellationService.addConfirmedBooking(r1);
        cancellationService.addConfirmedBooking(r2);

        /* Guest cancels reservation */

        cancellationService.cancelBooking("RSV101");

        cancellationService.displayRollbackStack();

        inventory.displayInventory();
    }
}