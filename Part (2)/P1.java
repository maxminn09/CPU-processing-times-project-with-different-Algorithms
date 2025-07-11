/* Name: Max Thet Tin
 */
//This is the P1 class which takes reads input from the textfile and uses semaphores and threads to use the wormhole for travel

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class P1 {
    // Creates semaphore to control access to the wormhole
    private static Semaphore wormhole = new Semaphore(1, true);
    // Creates counter to keep track of the total number of travelers
    private static int totalTravelers = 0;

    public static void main(String[] args) throws InterruptedException {
        try {
            // Reads input from a file specified as a command line argument
            Scanner scanner = new Scanner(new File(args[0]));
            String firstLine = scanner.nextLine();
            String[] parts = firstLine.split(", ");
            int earthTravelers = Integer.parseInt(parts[0].split("=")[1]);
            int proximaBTravelers = Integer.parseInt(parts[1].split("=")[1]);
            int numberOfTrips = Integer.parseInt(parts[2].split("=")[1]);
            scanner.close();

            // Create threads for Earth travelers
            for (int i = 1; i <= earthTravelers; i++) {
                String travelerId = "Earth_Traveler_" + i;
                new Thread(new Traveler(travelerId, "Proxima-b", numberOfTrips)).start();
            }

            // Create threads for Proxima-b travelers
            for (int i = 1; i <= proximaBTravelers; i++) {
                String travelerId = "ProximaB_Traveler_" + i;
                new Thread(new Traveler(travelerId, "Earth", numberOfTrips)).start();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    // This is a traveler class that implements the Runnable interface
    public static class Traveler implements Runnable {
        private String travelerId;
        private String destination;
        private int trips;

        // Constructor for the Traveler class
        public Traveler(String travelerId, String destination, int trips) {
            this.travelerId = travelerId;
            this.destination = destination;
            this.trips = trips;
        }

        // Run method that defines the behavior of a traveler
        public void run() {
            try {
                for (int i = 0; i < trips; i++) {
                    // Waiting for access to the wormhole
                    System.out.println(travelerId + ": Waiting for wormhole. Traveling towards " + destination);
                    wormhole.acquire();

                    // Simulate crossing the wormhole in segments
                    for (int load = 25; load < 100; load += 25) {
                        System.out.println(travelerId + ": Crossing wormhole Loading: " + load + "%");
                        Thread.sleep(50); // Adds delay after 25% of each travel
                    }
                    System.out.println(travelerId + ": Across the wormhole.");
                    
                    // Update the total traveler count
                    totalTravelers++;
                    System.out.println("Total Travelers = " + totalTravelers);

                    // Release access to the wormhole
                    wormhole.release();

                    // Swap the destination for the next trip
                    if (destination.equals("Earth")) {
                        destination = "Proxima-b";
                    } else {
                        destination = "Earth";
                    }
                }
                // Traveler finished all trips
                System.out.println(travelerId + " Finished");
            } catch (InterruptedException e) {
                System.out.println("An error has occurred, please try again.");
                e.printStackTrace();
            }
        }
    }
}
