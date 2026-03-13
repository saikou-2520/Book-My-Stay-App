/**
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates how synchronized access prevents
 * race conditions when multiple threads allocate rooms.
 *
 * @version 11.1
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

/* Shared Inventory */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    /* Critical section protected by synchronization */

    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            return true;
        }

        return false;
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* Booking Processor Thread */

class BookingProcessor extends Thread {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> bookingQueue,
                            RoomInventory inventory) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty())
                    return;

                reservation = bookingQueue.poll();
            }

            boolean allocated =
                    inventory.allocateRoom(reservation.getRoomType());

            if (allocated) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " confirmed booking for "
                                + reservation.getGuestName()
                                + " (" + reservation.getRoomType() + ")"
                );

            } else {

                System.out.println(
                        Thread.currentThread().getName()
                                + " failed booking for "
                                + reservation.getGuestName()
                                + " (No rooms available)"
                );
            }
        }
    }
}

/* Application Entry */

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 11.1");
        System.out.println("=================================\n");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();

        /* Simulated concurrent booking requests */

        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Double Room"));
        bookingQueue.add(new Reservation("Eve", "Suite Room"));

        /* Create multiple threads */

        BookingProcessor t1 =
                new BookingProcessor(bookingQueue, inventory);

        BookingProcessor t2 =
                new BookingProcessor(bookingQueue, inventory);

        BookingProcessor t3 =
                new BookingProcessor(bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        inventory.displayInventory();
    }
}