package aima;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;
import model.*;

import java.util.ArrayList;

public class State {
    private ArrayList<Truck> trucks;

    private State() {
        this.trucks = new ArrayList<>();
    }

    private State(ArrayList<Truck> trucks) {
        this.trucks = new ArrayList<>(trucks);
    }

    private void addTruck(Truck truck){
        trucks.add(truck);
    }

    public static State initialState(CentrosDistribucion centrosDistribucion, Gasolineras gasolineras) {
        State stateReturn = new State();

        for (Distribucion distribucion : centrosDistribucion){
            Coordinate origin = new Coordinate(distribucion.getCoordX(), distribucion.getCoordY());
            Truck truck = new Truck(origin);
            stateReturn.trucks.add(truck);
        }

        int truckIterator = 0; // iterates when truck can't handle more requests
        int requestNumber = 0;
        for (Gasolinera gasolinera : gasolineras) {
            Coordinate coordinate = new Coordinate(gasolinera.getCoordX(), gasolinera.getCoordY());
            Trip trip = new Trip();
            for (int i = 0; i < gasolinera.getPeticiones().size(); ++i) {
                Request request = new Request(coordinate, gasolinera.getPeticiones().get(i));
                switch (requestNumber) {
                    case 0:
                        trip.setRequest(1, request);
                        requestNumber = 1;
                        break;
                    case 1:
                        trip.setRequest(2, request);
                        requestNumber = 0;
                        boolean error = stateReturn.trucks.get(truckIterator).addTrip(trip);
                        if (error){ //violated restriction
                            ++truckIterator; //try next truck
                            if (truckIterator == stateReturn.trucks.size()){ //No more trucks
                                GhostTruck ghostTruck = new GhostTruck(new Coordinate(0, 0));
                                stateReturn.addTruck(ghostTruck);
                            }
                            stateReturn.trucks.get(truckIterator).addTrip(trip); //adding trips to ghost tucks
                        }
                        break;
                }

            }
        }
        return stateReturn;
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
            }
        }

        for (int i = 0; i < requests.size(); ++i) {
            Request request = requests.get(i);
            if (i + 1 == requests.size()) {
                trips.add(new Trip(request, new GhostRequest(requests.get(i))));
            } else {
                int minimum = 200;
                int minimumPosition = i;
                for (int j = i; j < requests.size(); ++j) {
                    int distance = Coordinate.distance(request.getCoordinate(), requests.get(j).getCoordinate());
                    if (distance < minimum) {
                        minimum = distance;
                        minimumPosition = i;
                    }
                }
                trips.add(new Trip(request, requests.get(minimumPosition)));
                requests.remove(i);
                requests.remove(minimumPosition);
                --i;
            }
        }

        // Add trucks to the list with the maximum number of trips each
        ArrayList<Truck> trucks = Truck.createTrucksFrom(centrosDistribucion);
        for (Truck truck: trucks) {
            for (int i = 0; i < trips.size() && !truck.isFull(); i++) {
                Trip trip = trips.get(i);
                if(truck.addTrip(trip)) {
                    trips.remove(i);
                    --i;
                }
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

    Boolean swapRequest(int truck1, int trip1, int req1, int truck2, int trip2, int req2) {
        Truck truckOne = trucks.get(truck1);
        Trip auxTripOne = truckOne.getTripAt(trip1);

        Truck truckTwo = trucks.get(truck2);

        Trip newTripForTruckOne = new Trip(
                truckOne.getTripAt(trip1).getRequest(req1 + 1), // The request we want to keep for truck one
                truckTwo.getTripAt(trip2).getRequest(req2)); // The request we want to change


        if (!truckOne.replaceTripIfFits(trip1, newTripForTruckOne)) {
            return false;
        }

        Trip newTripForTruckTwo = new Trip(
                truckTwo.getTripAt(trip2).getRequest(req2 + 1), // The request we want to keep for truck one
                truckOne.getTripAt(trip1).getRequest(req1)); // The request we want to change

        if (!truckTwo.replaceTripIfFits(trip2, newTripForTruckTwo)) {
            truckOne.replaceTripIfFits(trip1, auxTripOne);
            return false;
        }

        return true;

        /*
        Request request1 = trucks.get(truck1).getTripAt(trip1).getRequest(req1);
        Request request2 = trucks.get(truck2).getTripAt(trip2).getRequest(req2);

        trucks.get(truck1).getTripAt(trip1).setRequest(req1, request2);
        trucks.get(truck2).getTripAt(trip2).setRequest(req2, request1);
        */
    }

    Boolean moveTrip(int target, int truck, int trip) {
        if (trucks.get(target).addTrip(trucks.get(truck).getTripAt(trip))) {
            trucks.get(truck).removeTripAt(trip);
            return true;
        }
        return false;
    }

    Boolean swapTrip(int truck1, int trip1, int truck2, int trip2) {
        Truck truckOne = trucks.get(truck1);
        Trip tripOne = truckOne.getTripAt(trip1);
        Truck truckTwo = trucks.get(truck2);
        Trip tripTwo = truckOne.getTripAt(trip2);

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

    double getTotalProfit() {
        double totalProfit = 0.0;
        for(Truck truck : trucks) {
            totalProfit += truck.getTotalProfit();
        }
        return totalProfit;
    }

    State copy(){
        State aux = new State(this.trucks);
        return aux;
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
}
