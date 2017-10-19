import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Gasolineras gasolineras = new Gasolineras(100, 1234); // valores del experimento
        CentrosDistribucion centrosDistribucion = new CentrosDistribucion(10, 1, 1234); //no esto seguro de que argumento es mult
        State state = State.initialState(centrosDistribucion, gasolineras);

        Problem p = new Problem(state,
                        new GasolinaSuccessorFunction(),
                        new GasolinaGoalTest(),
                        new GasolinaHeuristicFunction());

        Search alg = new HillClimbingSearch();
        //Search alg2 = new SimulatedAnnealingSearch();
        SearchAgent agent = new SearchAgent(p, alg);

        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}
