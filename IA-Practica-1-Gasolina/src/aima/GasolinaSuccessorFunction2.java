package aima;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import model.Trip;
import model.Truck;

import java.util.ArrayList;
import java.util.List;


public class GasolinaSuccessorFunction2 implements SuccessorFunction {

    public List getSuccessors(Object o){
        ArrayList<Successor> successors = new ArrayList<>();
        State state = (State) o;

        for (int i = 0; i < state.getTrucksSize(); i++) {
            Truck truck = state.getTruckAt(i);
            for (int j = 0; j < truck.getTripsSize(); j++) {
                for (int k = i; k < state.getTrucksSize(); k++) {
                    Truck truck2 = state.getTruckAt(k);
                    for (int l = 0; l < truck2.getTripsSize(); l++) {
                        State newState = state.clone();
                        newState.swapTrip(i, j, k, l);
                        if (newState.getTotalProfit() > state.getTotalProfit()) {
                            successors.add(new Successor(
                                    "Swap trip " + j + " from truck " + i + " with trip " + l + " from truck " + k,
                                    newState
                            ));
                        }
                    }
                }
            }
        }

        return successors;
    }
}
