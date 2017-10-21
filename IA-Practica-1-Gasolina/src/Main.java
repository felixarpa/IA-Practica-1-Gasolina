import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;
import aima.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import model.Truck;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        //Gasolineras gasolineras = new Gasolineras(100, 1234); // valores del experimento
        //CentrosDistribucion centrosDistribucion = new CentrosDistribucion(10, 1, 1234);
        Gasolineras gasolineras = new Gasolineras(10, 1234); // valores del experimento
        print(gasolineras);
        CentrosDistribucion centrosDistribucion = new CentrosDistribucion(1, 1, 1234);
        print(centrosDistribucion);
        State state = State.simpleInitialState(centrosDistribucion, gasolineras);
        print(state);

        Problem problem = new Problem(state,
                        new GasolinaSuccessorFunction2(),
                        new GasolinaGoalTest(),
                        new GasolinaHeuristicFunction());

        Search hillClimbingSearch = new HillClimbingSearch();
        //Search alg2 = new SimulatedAnnealingSearch();
        SearchAgent agent = new SearchAgent(problem, hillClimbingSearch);

        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
    }

    private static void print(Gasolineras gasolineras) {
        for (int i = 0; i < gasolineras.size(); i++) {
            int x = gasolineras.get(i).getCoordX();
            int y = gasolineras.get(i).getCoordY();
            for (int j = 0; j < gasolineras.get(i).getPeticiones().size(); j++) {
                int dias = gasolineras.get(i).getPeticiones().get(j);
                System.out.println("Petición: " + dias + " (" + x + "," + y + ")");
            }
        }
    }

    private static void print(CentrosDistribucion centros) {
        for (int i = 0; i < centros.size(); i++) {
            int x = centros.get(i).getCoordX();
            int y = centros.get(i).getCoordY();
            System.out.println("Centro: (" + x + "," + y + ")");
        }
    }

    private static void print(State state) {
        for (int i = 0; i < state.getTrucks().size(); i++) {
            Truck truck = state.getTrucks().get(i);
            truck.print(i);
        }
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
