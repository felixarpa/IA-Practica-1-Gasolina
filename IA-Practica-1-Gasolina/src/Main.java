import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;
import aima.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import model.Truck;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.lang.*;


public class Main {


    public static void main(String[] args) throws Exception {
        long initTime = System.currentTimeMillis();

        // valores del experimento especial
        Gasolineras gasolineras = new Gasolineras(100, 1234);
        CentrosDistribucion centrosDistribucion = new CentrosDistribucion(10, 1, 1234);
        State state = State.simpleInitialState(centrosDistribucion, gasolineras);

        print(gasolineras);
        print(centrosDistribucion);
        print(state);

        Problem problem = new Problem(state,
                        new GasolinaSuccessorFunction(),
                        new GasolinaGoalTest(),
                        new GasolinaHeuristicFunction2());

        Search hillClimbingSearch = new HillClimbingSearch();
        Search simulatedAnnealingSearch = new SimulatedAnnealingSearch();

        SearchAgent agent = new SearchAgent(problem, hillClimbingSearch);
        //SearchAgent agent = new SearchAgent(problem, simulatedAnnealingSearch);

        State goalState = (State) hillClimbingSearch.getGoalState();
        //State goalState = (State) simulatedAnnealingSearch.getGoalState();

        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
        goalState.print();
        //goalState.printProfit();
        goalState.printProfitNextDay();
        long endTime = System.currentTimeMillis();
        System.out.println("The task has taken "+ ( endTime - initTime ) +" milliseconds");
    }

    private static void print(Gasolineras gasolineras) {
        for (int i = 0; i < gasolineras.size(); i++) {
            System.out.println("Gasolinera :" + i);
            int x = gasolineras.get(i).getCoordX();
            int y = gasolineras.get(i).getCoordY();
            for (int j = 0; j < gasolineras.get(i).getPeticiones().size(); j++) {
                int dias = gasolineras.get(i).getPeticiones().get(j);
                System.out.println("Petición: " + dias + " (" + x + "," + y + ")");
            }
        }
    }

    private static void print(CentrosDistribucion centros) {
        for (Distribucion centro : centros) {
            int x = centro.getCoordX();
            int y = centro.getCoordY();
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
        for (Object o : properties.keySet()) {
            String key = (String) o;
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (Object action : actions) {
            System.out.println((String) action);
        }
    }
}
