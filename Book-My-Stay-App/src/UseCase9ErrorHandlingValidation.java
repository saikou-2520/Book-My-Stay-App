/**
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates structured validation and
 * custom exception handling to protect system state.
 *
 * @version 9.1
 */

import java.util.*;

/* Custom Exception */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
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

    public boolean roomTypeExists(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) throws InvalidBookingException {

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for type: " + roomType);
        }

        inventory.put(roomType, available - 1);
    }
}

/* Validator */

class BookingValidator {

    public void validate(String guestName,
                         String roomType,
                         RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException(
                    "Guest name cannot be empty.");
        }

        if (!inventory.roomTypeExists(roomType)) {
            throw new InvalidBookingException(
                    "Invalid room type selected: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException(
                    "Selected room type is not available.");
        }
    }
}

/* Application Entry */

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 9.1");
        System.out.println("=================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingValidator validator = new BookingValidator();

        String guestName = "Alice";
        String roomType = "Suite Room"; // purposely unavailable

        try {

            validator.validate(guestName, roomType, inventory);

            inventory.decrementRoom(roomType);

            System.out.println("Booking confirmed for " + guestName);

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed:");
            System.out.println(e.getMessage());
        }

        System.out.println("\nSystem continues running safely.");
    }
}