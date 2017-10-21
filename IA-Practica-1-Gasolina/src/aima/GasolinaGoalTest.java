package aima;

import aima.search.framework.GoalTest;

public class GasolinaGoalTest implements GoalTest{
    @Override
    public boolean isGoalState(Object state) {
        return ((State) state).isGoalState();
    }
}
