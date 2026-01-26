import java.util.Scanner;

public class RideDispatcher {

    private LinkedQueue<RideRequest> rideQueue = new LinkedQueue<>();

    private int totalRides = 0;
    private long totalWaitSeconds = 0;
    private long longestWaitSeconds = 0;

    public void run() {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- RIDE DISPATCH MENU ---");
            System.out.println("1. Add Ride Request");
            System.out.println("2. Assign Driver");
            System.out.println("3. View Ride Queue");
            System.out.println("4. View Dispatch Statistics");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1: addRideRequest(input); break;
                case 2: assignDriver(); break;
                case 3: rideQueue.printQueue(); break;
                case 4: printStats(); break;
                case 5: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    private void addRideRequest(Scanner input) {
        System.out.print("Passenger Name: ");
        String name = input.nextLine();

        System.out.print("Pickup Location: ");
        String pickup = input.nextLine();

        System.out.print("Destination: ");
        String dest = input.nextLine();

        RideRequest req = new RideRequest(name, pickup, dest);
        rideQueue.enqueue(req);

        System.out.println("Ride request added!");
    }

    private void assignDriver() {
        if (rideQueue.isEmpty()) {
            System.out.println("No passengers waiting!");
            return;
        }

        RideRequest req = rideQueue.dequeue();
        req.assignNow();

        long wait = req.getWaitTimeSeconds();
        totalRides++;
        totalWaitSeconds += wait;
        longestWaitSeconds = Math.max(longestWaitSeconds, wait);

        System.out.println("\n--- DRIVER ASSIGNED ---");
        System.out.println(req);
    }

    private void printStats() {
        if (totalRides == 0) {
            System.out.println("No rides assigned yet.");
            return;
        }

        double avg = (double) totalWaitSeconds / totalRides;

        System.out.println("\n--- DISPATCH STATISTICS ---");
        System.out.println("Total Rides Served: " + totalRides);
        System.out.println("Average Wait Time: " + avg + " seconds");
        System.out.println("Longest Wait Time: " + longestWaitSeconds + " seconds");
    }

    public static void main(String[] args) {
        new RideDispatcher().run();
    }
}
