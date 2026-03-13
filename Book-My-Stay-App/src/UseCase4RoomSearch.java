import java.util.HashMap;
import java.util.Map;

/* Room Domain Model */
abstract class Room {

    protected String type;
    protected double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Price     : ₹" + price);
    }
}

/* Concrete Room Types */

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 5000);
    }
}

/* Inventory Class */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/* Main Application */

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 4.1");
        System.out.println("==================================\n");

        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        System.out.println("Available Rooms\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println("---------------------------");
            }
        }
    }
}