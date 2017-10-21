package aima;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import model.Truck;

import java.util.ArrayList;
import java.util.List;


public class GasolinaSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        State presentState = (State) state;

        State aux = presentState.copy();
        for (int i = 0; i < presentState.getTrucksSize(); ++i){
            Truck truck1 = presentState.getTruckAt(i);
            for (int j = 0; j < truck1.getTripsSize(); ++j){
                for (int i2 = 0; i2 < presentState.getTrucksSize(); ++i2){
                    Truck truck2 = presentState.getTruckAt(i2);
                    for (int j2 = 0; j2 < truck2.getTripsSize(); ++j2){
                        if (i2 != i){
                            double profitTruck1 = truck1.getTripAt(j).getTotalTripProfit(truck1.getOrigin());
                            double profitTruck2 = truck2.getTripAt(j2).getTotalTripProfit(truck2.getOrigin());
                            double newProfitTruck1, newProfitTruck2;

                            aux.swapRequest(i, j, 1, i2, j2, 1); // swaps
                            Successor newSuccessor = new Successor("Swaped request 1 from trip number "
                                    + Integer.toString(j) + " from truck " + Integer.toString(i) +
                                    " with request 1 from trip number " + Integer.toString(j2) +
                                    " from truck " + Integer.toString(i2), aux);

                            newProfitTruck1 = aux.getTruckAt(i).getTripAt(j).getTotalTripProfit(aux.getTruckAt(i).getOrigin());
                            newProfitTruck2 = aux.getTruckAt(i2).getTripAt(j2).getTotalTripProfit(aux.getTruckAt(i2).getOrigin());
                            if(newProfitTruck1 > profitTruck1 || newProfitTruck2 > profitTruck2) retval.add(newSuccessor);
                            aux.swapRequest(i, j, 1, i2, j2, 1); // resets

                            aux.swapRequest(i, j, 1, i2, j2, 2); // swaps
                            newSuccessor = new Successor("Swaped request 1 from trip number "
                                    + Integer.toString(j) + " from truck " + Integer.toString(i) +
                                    " with request 2 from trip number " + Integer.toString(j2) +
                                    " from truck " + Integer.toString(i2), aux);
                            retval.add(newSuccessor);
                            aux.swapRequest(i, j, 1, i2, j2, 2); // resets

                            aux.swapRequest(i, j, 2, i2, j2, 1); // swaps
                            newSuccessor = new Successor("Swaped request 2 from trip number "
                                    + Integer.toString(j) + " from truck " + Integer.toString(i) +
                                    " with request 1 from trip number " + Integer.toString(j2) +
                                    " from truck " + Integer.toString(i2), aux);
                            newProfitTruck1 = aux.getTruckAt(i).getTripAt(j).getTotalTripProfit(aux.getTruckAt(i).getOrigin());
                            newProfitTruck2 = aux.getTruckAt(i2).getTripAt(j2).getTotalTripProfit(aux.getTruckAt(i2).getOrigin());
                            if(newProfitTruck1 > profitTruck1 || newProfitTruck2 > profitTruck2) retval.add(newSuccessor);
                            aux.swapRequest(i, j, 2, i2, j2, 1); //resets

                            aux.swapRequest(i, j, 2, i2, j2, 2); // swaps
                            newSuccessor = new Successor("Swaped request 2 from trip number "
                                    + Integer.toString(j) + " from truck " + Integer.toString(i) +
                                    " with request 2 from trip number " + Integer.toString(j2) +
                                    " from truck " + Integer.toString(i2), aux);
                            newProfitTruck1 = aux.getTruckAt(i).getTripAt(j).getTotalTripProfit(aux.getTruckAt(i).getOrigin());
                            newProfitTruck2 = aux.getTruckAt(i2).getTripAt(j2).getTotalTripProfit(aux.getTruckAt(i2).getOrigin());
                            if(newProfitTruck1 > profitTruck1 || newProfitTruck2 > profitTruck2) retval.add(newSuccessor);
                            aux.swapRequest(i, j, 2, i2, j2, 2); // resets
                        }
                    }
                }
            }
        }

        return retval;
    }
}
