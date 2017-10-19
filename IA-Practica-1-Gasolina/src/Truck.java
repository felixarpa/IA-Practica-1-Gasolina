import java.util.ArrayList;

public class Truck {
    private ArrayList<Trip> trips;
    private int tripsLeft;
    private int kmLeft;
    private Coordinate origin;

    private int calculateTripDistance(Trip trip){
        int distance = Coordinate.distance(origin, trip.getRequest1().getCoordinate());
        distance += Coordinate.distance(trip.getRequest1().getCoordinate(), trip.getRequest2().getCoordinate());
        distance += Coordinate.distance(trip.getRequest2().getCoordinate(), origin);
        return distance;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public Coordinate getOrigin() {
        return origin;
    }

    public int getTripsSize(){
        return trips.size();

    }

    public Truck(Coordinate origin) {
        this.trips = new ArrayList<>(5);
        this.tripsLeft = 5;
        this.kmLeft = 640;
        this.origin = origin;
    }

    public Trip getTripAt(int index) {
        return trips.get(index);
    }

    // Returns false if truck can't do an more trips
    public boolean addTrip (Trip trip){
        if (tripsLeft > 5) {
            int distance = calculateTripDistance(trip);
            if (kmLeft - distance >= 0) {
                kmLeft -= distance;
                tripsLeft -= 1;
                trips.add(trip);
                return true;
            }
        }
        return false;
    }


    public double getTotalProfit() {
        double totalProfit = 0.0;
        for(Trip trip : trips) {
            totalProfit += trip.getProfit(origin);
        }
        return totalProfit;
    }

}
