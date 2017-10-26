package aima;

import aima.search.framework.HeuristicFunction;

public class GasolinaHeuristicFunction2 implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object o) {
        return -((State) o).getTotalProfitWithNextDay().getKey();
    }
}
