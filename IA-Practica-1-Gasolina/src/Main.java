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
import java.util.Scanner;
import java.lang.*;


public class Main {


    public static void main(String[] args) throws Exception {


        // CUSTOM PARAMETERS
        Scanner capt = new Scanner(System.in);
        int heuristic = 0, search  = 0, ncen, ngas, seed;
        System.out.println("Specify number of Centers:");
        ncen = capt.nextInt();
        System.out.println("Specify number of Petrol Stations:");
        ngas = capt.nextInt();
        System.out.println("Specify the Seed:");
        seed = capt.nextInt();
        while(heuristic != 1 && heuristic != 2){
            System.out.println("Specify the Heuristic Function: 1 or 2");
            heuristic = capt.nextInt();
        }
        while(search != 1 && search != 2){
            System.out.println("Specify the Search Method: Hill Climbing (1) or Simulated Annealing (2)");
            search = capt.nextInt();
        }

        long initTime = System.currentTimeMillis();

        Gasolineras gasolineras = new Gasolineras(ngas, seed);
        CentrosDistribucion centrosDistribucion = new CentrosDistribucion(ncen, 1, seed);
        State state = State.simpleInitialState(centrosDistribucion, gasolineras);

        print(gasolineras);
        print(centrosDistribucion);
        //print(state);  // why printing the initial state?

        Problem problem;
        Search typeOfSearch;
        if(search == 1) typeOfSearch = new HillClimbingSearch();
        else typeOfSearch = new SimulatedAnnealingSearch();

        if(heuristic == 1) {
            problem = new Problem(state,
                    new GasolinaSuccessorFunction(),
                    new GasolinaGoalTest(),
                    new GasolinaHeuristicFunction());
        }
        else{
            problem = new Problem(state,
                    new GasolinaSuccessorFunction(),
                    new GasolinaGoalTest(),
                    new GasolinaHeuristicFunction2());
        }

        SearchAgent agent = new SearchAgent(problem, typeOfSearch);
        // Search hillClimbingSearch = new HillClimbingSearch();
        // Search simulatedAnnealingSearch = new SimulatedAnnealingSearch();
        // SearchAgent agent = new SearchAgent(problem, hillClimbingSearch);
        // SearchAgent agent = new SearchAgent(problem, simulatedAnnealingSearch);
        State goalState = (State) typeOfSearch.getGoalState();
        // State goalState = (State) hillClimbingSearch.getGoalState();
        // State goalState = (State) simulatedAnnealingSearch.getGoalState();

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
