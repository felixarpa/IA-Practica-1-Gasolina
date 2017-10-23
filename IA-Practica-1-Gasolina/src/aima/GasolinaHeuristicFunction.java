package aima;

import aima.search.framework.HeuristicFunction;

public class GasolinaHeuristicFunction implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object object) {
        return -((State) object).getTotalProfit();
    }
}
