package model;

public class GhostTrip extends Trip {

    public GhostTrip(Request request1, Request request2) {
        super(request1, request2);
    }

    public GhostTrip() {
        super(new GhostRequest(), new GhostRequest());
    }

    @Override
    int getTripDistance(Coordinate origin) {
        return 0;
    }

    @Override
    public double getTotalTripProfit(Coordinate truckCoordinate) {
        return 0.0;
    }

    public GhostTrip clone() {
        return new GhostTrip(request1.clone(), request2.clone());
    }
}
