
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : ₹" + price);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 1500);
    }
}

/* Double Room Class */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 2500);
    }
}

/* Suite Room Class */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 5000);
    }
}

/* Application Entry Point */
public class BasicRoomTypes {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Welcome to BookMyStay ");
        System.out.println(" Hotel Booking System v2.1 ");
        System.out.println("=====================================\n");

        /* Room Objects (Polymorphism) */
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        /* Static Availability Variables */
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Room Details & Availability\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available : " + singleAvailable);
        System.out.println("--------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + doubleAvailable);
        System.out.println("--------------------------------");

        suiteRoom.displayRoomDetails();
        System.out.println("Available : " + suiteAvailable);
        System.out.println("--------------------------------");
    }
}