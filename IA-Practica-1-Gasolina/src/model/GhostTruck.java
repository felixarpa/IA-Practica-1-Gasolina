package model;

public class GhostTruck extends Truck {

    public GhostTruck(Coordinate origin) {
        super(origin);
    }

    public GhostTruck(Trip trip) {
        super(new Coordinate(0, 0));
        this.addTrip(trip);

       /* this.addTrip(new GhostTrip());
        this.addTrip(new GhostTrip());
        this.addTrip(new GhostTrip());
        this.addTrip(new GhostTrip());*/
    }

    @Override
    public double getTotalProfit() {
        return 0.0;
    }

  /*  @Override
    public boolean addTrip (Trip trip){ return true; }*/
}
