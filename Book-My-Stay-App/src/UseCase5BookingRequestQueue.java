import java.util.LinkedList;
import java.util.Queue;

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

    public void displayRequest() {
        System.out.println("Guest : " + guestName);
        System.out.println("Requested Room : " + roomType);
        System.out.println("-----------------------------");
    }
}

/* Booking Queue Manager */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayRequests() {

        System.out.println("\nCurrent Booking Requests (FIFO)");
        System.out.println("--------------------------------");

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
    }
}

/* Application Entry */

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 5.1");
        System.out.println("=================================\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulated guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayRequests();
    }
}