package aima;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import model.Trip;
import model.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GasolinaSuccessorFunctionSA implements SuccessorFunction {

    public List getSuccessors(Object o){
        ArrayList<Successor> successors = new ArrayList<>();
        State state = (State) o;

        int i = randomNumber(state.getTrucksSize());
        int j = randomNumber(state.getTruckAt(i).getTripsSize());

        int k = i;
        while (k == i) {
            k = randomNumber(state.getTrucksSize());
        }
        int l = randomNumber(state.getTruckAt(k).getTripsSize());

        State newState = state.clone();
        newState.swapTrip(i, j, k, l);
        successors.add(new Successor(
                "Swap trip " + j + " from truck " + i + " with trip " + l + " from truck " + k,
                newState
        ));

        return successors;
    }

    private static int randomNumber(int max) {
        return new Random().nextInt(max);
    }
}
