package aima;

import aima.search.framework.HeuristicFunction;

public class GasolinaHeuristicFunction implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object o) {
        return -((State) o).getTotalProfit();
    }
}
