/**
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates serialization and deserialization
 * to persist system state across restarts.
 *
 * @version 12.1
 */

import java.io.*;
import java.util.*;

/* Reservation */

class Reservation implements Serializable {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

/* Inventory */

class RoomInventory implements Serializable {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void displayInventory() {

        System.out.println("\nInventory State");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* System Snapshot */

class SystemState implements Serializable {

    private List<Reservation> bookingHistory;
    private RoomInventory inventory;

    public SystemState(List<Reservation> bookingHistory, RoomInventory inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }

    public List<Reservation> getBookingHistory() {
        return bookingHistory;
    }

    public RoomInventory getInventory() {
        return inventory;
    }
}

/* Persistence Service */

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    /* Save state */

    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    /* Load state */

    public SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();

            System.out.println("\nSystem state restored successfully.");

            return state;

        } catch (FileNotFoundException e) {

            System.out.println("\nNo previous state found. Starting fresh.");
            return null;

        } catch (Exception e) {

            System.out.println("\nFailed to restore system state.");
            return null;
        }
    }
}

/* Application Entry */

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 12.1");
        System.out.println("=================================");

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.loadState();

        List<Reservation> bookingHistory;
        RoomInventory inventory;

        if (state == null) {

            bookingHistory = new ArrayList<>();
            inventory = new RoomInventory();

            bookingHistory.add(new Reservation("RSV101", "Alice", "Single Room"));
            bookingHistory.add(new Reservation("RSV102", "Bob", "Double Room"));

        } else {

            bookingHistory = state.getBookingHistory();
            inventory = state.getInventory();
        }

        System.out.println("\nBooking History");

        for (Reservation r : bookingHistory) {
            r.display();
        }

        inventory.displayInventory();

        /* Save current state */

        SystemState newState = new SystemState(bookingHistory, inventory);

        persistence.saveState(newState);
    }
}