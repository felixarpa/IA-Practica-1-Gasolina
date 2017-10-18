import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;

public class State {
    private ArrayList<Truck> trucks;

    public State() {
        this.trucks = new ArrayList<>();
    }

    public State(ArrayList<Truck> trucks) {
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
                        trip.setRequest1(request);
                        requestNumber = 1;
                        break;
                    case 1:
                        trip.setRequest2(request);
                        requestNumber = 0;
                        boolean error = stateReturn.trucks.get(truckIterator).addTrip(trip);
                        if (error){ //violated restricction
                            ++truckIterator; //try next truck
                            if (truckIterator == stateReturn.trucks.size()){ //No more trucks
                                GhostTruck ghostTruck = new GhostTruck(new Coordinate(0, 0));
                                stateReturn.addTruck(ghostTruck);
                            }
                            stateReturn.trucks.get(truckIterator).addTrip(trip); //adding trips to ghosttucks
                        }
                        break;
                }

            }
        }
        return stateReturn;
    }

    public void swapRequest(int truck1, int trip1, int req1, int truck2, int trip2, int req2) {
        Request request1 = trucks.get(truck1).getTripAt(trip1).getRequest(req1);
        Request request2 = trucks.get(truck2).getTripAt(trip2).getRequest(req2);


    }

    public double getTotalProfit() {
        double totalProfit = 0.0;
        for(Truck truck : trucks) {
            totalProfit += truck.getTotalProfit();
        }
        return totalProfit;
    }

    public State copy(){
        State aux = new State(this.trucks);
        return aux;
    }
}
