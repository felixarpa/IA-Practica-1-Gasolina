import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;
import aima.*;
import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import model.Truck;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.lang.*;


public class Main {


    public static void main(String[] args) throws Exception {
        int ncen = 0;
        int ntruck = 0;
        int ngas = 0;
        int seed = 0;
        int heuristic = 0;
        int search  = 0;

        if (args.length != 6) {
            Scanner capt = new Scanner(System.in);

            System.out.println("Specify number of Centers:");
            ncen = capt.nextInt();

            System.out.println("Specify number of trucks per Center:");
            ntruck = capt.nextInt();

            System.out.println("Specify number of Petrol Stations:");
            ngas = capt.nextInt();

            System.out.println("Specify the Seed:");
            seed = capt.nextInt();

            while (heuristic != 1 && heuristic != 2) {
                System.out.println("Specify the Heuristic Function: today's profit (1) or today's and tomorrow's profit (2)");
                heuristic = capt.nextInt();
            }

            while (search != 1 && search != 2) {
                System.out.println("Specify the Search Method: Hill Climbing (1) or Simulated Annealing (2)");
                search = capt.nextInt();
            }
        } else {
            ncen = Integer.parseInt(args[0]);
            ntruck = Integer.parseInt(args[1]);
            ngas = Integer.parseInt(args[2]);
            seed = Integer.parseInt(args[3]);
            heuristic = Integer.parseInt(args[4]);
            search  = Integer.parseInt(args[5]);
        }

        System.out.println("Starting local search...");

        long initTime = System.currentTimeMillis();

        Gasolineras gasolineras = new Gasolineras(ngas, seed);
        CentrosDistribucion centrosDistribucion = new CentrosDistribucion(ncen, ntruck, seed);
        State state = State.simpleInitialState(centrosDistribucion, gasolineras);

        Problem problem;
        Search typeOfSearch;
        SuccessorFunction successorFunction;
        HeuristicFunction heuristicFunction;

        if (search == 1) {
            typeOfSearch = new HillClimbingSearch();
            successorFunction =  new GasolinaSuccessorFunctionHC();
        } else {
            typeOfSearch = new SimulatedAnnealingSearch();
            successorFunction =  new GasolinaSuccessorFunctionSA();
        }

        if (heuristic == 1) {
            heuristicFunction = new GasolinaHeuristicFunction1();
        } else {
            heuristicFunction = new GasolinaHeuristicFunction2();
        }

        problem = new Problem(state, successorFunction, new GasolinaGoalTest(), heuristicFunction);
        SearchAgent agent = new SearchAgent(problem, typeOfSearch);
        State goalState = (State) typeOfSearch.getGoalState();

        switch (heuristic) {
            case 1:
                goalState.printProfit();
                break;
            case 2:
                goalState.printProfitNextDay();
        }

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
