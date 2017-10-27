package aima;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;
import javafx.util.Pair;
import model.*;

import java.util.ArrayList;

public class State {
    private ArrayList<Truck> trucks;

    private State(ArrayList<Truck> trucks) {
        this.trucks = new ArrayList<>(trucks);
    }

    public static State simpleInitialState(CentrosDistribucion centrosDistribucion, Gasolineras gasolineras) {

        // Create all requests from Gas Stations
        ArrayList<Request> requests = new ArrayList<>();
        for (Gasolinera gasolinera : gasolineras) {
            for (Integer request : gasolinera.getPeticiones()) {
                requests.add(
                        new Request(new Coordinate(gasolinera.getCoordX(), gasolinera.getCoordY()), request)
                );
            }
        }

        // Create trips with pairs of requests
        ArrayList<Trip> trips = new ArrayList<>();
        for (int i = 0; i < requests.size(); ++i) {
            boolean hasPair = false;
            for (int j = i + 1; j < requests.size() && !hasPair; ++j) {
                if (requests.get(i).getCoordinate().equals(requests.get(j).getCoordinate())) {
                    trips.add(new Trip(requests.get(i), requests.get(j)));
                    requests.remove(j);
                    requests.remove(i);
                    --i;
                    hasPair = true;
                }
                else j = requests.size(); // skip unnecessary iterations
            }
        }

        for (int i = 0; i < requests.size(); ++i) {
            Request request = requests.get(i);
            if (i + 1 == requests.size()) {
                trips.add(new Trip(request, new GhostRequest(requests.get(i))));
            } else {
                int minimum = 200;
                int minimumPosition = i+1;
                for (int j = i+1; j < requests.size(); ++j) {
                    int distance = Coordinate.distance(request.getCoordinate(), requests.get(j).getCoordinate());
                    if (distance < minimum) {
                        minimum = distance;
                        minimumPosition = j;
                    }
                }
                trips.add(new Trip(request, requests.get(minimumPosition)));
                requests.remove(i);
                requests.remove(minimumPosition-1);
                //requests.remove(minimumPosition);
                --i;
            }
        }

        // Add trucks to the list with the maximum number of trips each
        ArrayList<Truck> trucks = Truck.createTrucksFrom(centrosDistribucion);
        for (Truck truck: trucks) {
            for (int i = 0; i < trips.size() && !truck.isFull(); i++) {
                Trip trip = trips.get(i);
                if((trip.getTotalTripProfit(truck.getOrigin()) >= 0) && truck.addTrip(trip)) {
                    trips.remove(i);
                    --i;
                }
                else truck.addTrip(new GhostTrip(new GhostRequest(), new GhostRequest()));
            }

            while (!truck.isFull()) {
                truck.addTrip(new GhostTrip(new GhostRequest(), new GhostRequest()));
            }
        }

        // Add Ghost Trucks at the end
        for (Trip trip : trips) {
            trucks.add(new GhostTruck(trip));
        }

        return new State(trucks);
    }

    int getTrucksSize() {
        return trucks.size();
    }

    Boolean isGoalState(){
        return false;
    }

    Boolean swapTrip(int truck1, int trip1, int truck2, int trip2) {
        Truck truckOne = trucks.get(truck1);
        Trip tripOne = truckOne.getTripAt(trip1);
        Truck truckTwo = trucks.get(truck2);
        Trip tripTwo = truckTwo.getTripAt(trip2);

        if (!truckOne.replaceTripIfFits(trip1, tripTwo)) {
            return false;
        }

        if (!truckTwo.replaceTripIfFits(trip2, tripOne)) {
            truckOne.replaceTripIfFits(trip1, tripOne);
            return false;
        }
        return true;
    }

    Truck getTruckAt(int i){
        return trucks.get(i);
    }

    Pair<Double, Integer> getTotalProfit() {
        double totalProfit = 0.0;
        int countNonAnsweredPetitons = 0;
        for(Truck truck : trucks) {
            totalProfit += truck.getTotalProfit();
            if (truck instanceof GhostTruck) ++countNonAnsweredPetitons;
        }
        Pair ret = new Pair(totalProfit, countNonAnsweredPetitons);
        return ret;
    }

    Pair<Double, Integer> getTotalProfitWithNextDay() {
        double totalProfit = 0.0;
        int countNonAnsweredPetitons = 0;

        double profitForNextDay = 0.0;
        for(Truck truck : trucks) {
            totalProfit += truck.getTotalProfit();
            if (truck instanceof GhostTruck){
                double maxProfit = 0.0;
                Trip trip2 = truck.getTripAt(0);
                if (!(trip2 instanceof GhostTrip)) ++countNonAnsweredPetitons;

                for(int i = 0; i<trucks.size(); ++i) {
                    Truck truck2 = trucks.get(i);
                    double profit = trip2.getTotalTripProfitNextDay(truck2.getOrigin());
                    //System.out.println(profit);
                    if (profit > maxProfit && truck2.addExtraTrip(trip2)) {
                        //System.out.println("Extra trip added to truck" + Double.toString(profit));
                        maxProfit = profit;
                    }

                }
                totalProfit += maxProfit;
            }
        }
        return new Pair<>(totalProfit, countNonAnsweredPetitons*2);
    }

    public void printProfit(){
        Pair print = getTotalProfit();
        System.out.println("Total Profit = " + print.getKey());
        System.out.println("Non answered petitions = " + print.getValue());
    }

    public void printProfitNextDay(){
        Pair today = getTotalProfit();
        Pair twoDays = getTotalProfitWithNextDay();
        Double profitTomorrow = (Double) twoDays.getKey() - (Double) today.getKey();
        System.out.println("Total Profit for today = " + today.getKey());
        System.out.println("Profit for tomorrow = " + profitTomorrow);
        System.out.println("Petitions answered next day = " + twoDays.getValue());
    }
    public State clone() {
        ArrayList<Truck> clonedTrucks = new ArrayList<>();
        for (Truck truck : trucks) {
            clonedTrucks.add(truck.clone());
        }
        return new State(clonedTrucks);
    }

    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public void print() {
        for (int i = 0; i < trucks.size(); ++i) {
            trucks.get(i).print(i);
        }
    }
}
