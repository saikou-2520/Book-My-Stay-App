/**
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates how optional services can be attached
 * to existing reservations without modifying core booking logic.
 *
 * @version 7.1
 */

import java.util.*;

/* Service Class */

class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " : ₹" + cost);
    }
}

/* Add-On Service Manager */

class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    /* Attach service to reservation */

    public void addService(String reservationId, Service service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added: " + service.getServiceName()
                + " for Reservation " + reservationId);
    }

    /* Display services for reservation */

    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation: " + reservationId);

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service service : services) {
            service.displayService();
        }
    }

    /* Calculate additional cost */

    public double calculateTotalServiceCost(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null) return 0;

        double total = 0;

        for (Service service : services) {
            total += service.getCost();
        }

        return total;
    }
}

/* Application Entry */

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" BookMyStay Hotel Booking System ");
        System.out.println(" Version 7.1");
        System.out.println("=================================\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RSV-101";

        Service breakfast = new Service("Breakfast", 500);
        Service airportPickup = new Service("Airport Pickup", 1200);
        Service spaAccess = new Service("Spa Access", 1500);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spaAccess);

        manager.displayServices(reservationId);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("\nTotal Additional Cost: ₹" + totalCost);
    }
}