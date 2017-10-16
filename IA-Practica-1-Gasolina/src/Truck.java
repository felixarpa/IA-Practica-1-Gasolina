import java.util.ArrayList;

public class Truck {
    private ArrayList<Trip> trips;
    private int kmLeft;
    private Coordinate origin;

    public Truck(Coordinate origin) {
        this.trips = new ArrayList<>(5);
        this.kmLeft = 640;
        this.origin = origin;
    }

    public double getTotalProfit() {
        double totalProfit = 0.0;
        for(Trip trip : trips) {
            totalProfit += trip.getProfit(origin);
        }
        return totalProfit;
    }
}
