import java.util.ArrayList;

public class Truck {
    private ArrayList<Trip> trips;
    private int kmLeft;
    private Coordinate origin;
    private double totalProfit;

    public Truck(Coordinate origin) {
        this.trips = new ArrayList<>(5);
        this.kmLeft = 640;
        this.origin = origin;
        this.totalProfit = 0;
    }

    public double getTotalProfit() {
        return totalProfit;
    }
}
