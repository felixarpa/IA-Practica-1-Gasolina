import org.omg.CORBA.PUBLIC_MEMBER;

public class GhostTruck extends Truck {

    public GhostTruck(Coordinate origin) {
        super(origin);
    }

    @Override
    public double getTotalProfit() {
        return 0.0;
    }

    @Override
    public boolean addTrip (Trip trip){
        return true;
    }
}
