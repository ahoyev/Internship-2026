import java.time.LocalDateTime;
import java.time.Duration;

public class RideRequest {

    private String passengerName;
    private String pickupLocation;
    private String destination;

    private LocalDateTime requestTime;
    private LocalDateTime assignedTime;

    public RideRequest(String passengerName, String pickupLocation, String destination) {
        this.passengerName = passengerName;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.requestTime = LocalDateTime.now();
        this.assignedTime = null;
    }

    public void assignNow() {
        this.assignedTime = LocalDateTime.now();
    }

    public long getWaitTimeSeconds() {
        if (assignedTime == null) {
            return 0;
        }
        return Duration.between(requestTime, assignedTime).getSeconds();
    }

    @Override
    public String toString() {
        return "Ride Request:\n" +
               "Passenger: " + passengerName + "\n" +
               "Pickup: " + pickupLocation + "\n" +
               "Destination: " + destination + "\n" +
               "Requested At: " + requestTime + "\n" +
               "Assigned At: " + (assignedTime == null ? "Not Assigned" : assignedTime) + "\n" +
               "Wait Time (sec): " + getWaitTimeSeconds();
    }
}
