/**
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates how confirmed bookings are stored
 * and later used to generate administrative reports.
 *
 * @version 8.1
 */

import java.util.*;

/* Reservation Object */

class Reservation {

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
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("-----------------------------");
    }
}

/* Booking History */

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    /* Add confirmed booking */

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    /* Retrieve history */

    public List<Reservation> getHistory() {
        return history;
    }
}

/* Reporting Service */

class BookingReportService {

    public void generateReport(List<Reservation> history) {

        System.out.println("\nBooking History Report");
        System.out.println("-----------------------");

        if (history.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation reservation : history) {
            reservation.display();
        }

        System.out.println("Total Bookings : " + history.size());
    }
}

/* Application Entry */

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 8.1");
        System.out.println("=================================\n");

        BookingHistory bookingHistory = new BookingHistory();

        /* Simulated confirmed bookings */

        bookingHistory.addReservation(
                new Reservation("RSV-101", "Alice", "Single Room"));

        bookingHistory.addReservation(
                new Reservation("RSV-102", "Bob", "Double Room"));

        bookingHistory.addReservation(
                new Reservation("RSV-103", "Charlie", "Suite Room"));

        /* Admin generates report */

        BookingReportService reportService = new BookingReportService();

        reportService.generateReport(bookingHistory.getHistory());
    }
}