import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;

public class State {
    private ArrayList<Truck> trucks;

    public State() {
        this.trucks = new ArrayList<>();
    }

    private void addTruck(Truck truck){
        trucks.add(truck);
    }

    public static State initialState(CentrosDistribucion centrosDistribucion, Gasolineras gasolineras) {
        State stateReturn = new State();

        for (int i = 0; i < centrosDistribucion.size(); ++i){
            Coordinate origin = new Coordinate(centrosDistribucion.get(i).getCoordX(),
                    centrosDistribucion.get(i).getCoordY());
            Truck truck = new Truck(origin);
            stateReturn.trucks.add(truck);
        }

        int truckIterator = 0;
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
                        if (error){
                            ++truckIterator;
                            if (truckIterator == stateReturn.trucks.size()){
                                GhostTruck ghostTruck = new GhostTruck(new Coordinate(0, 0)); //not ideal, will do unnecesary computations
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

    public double getTotalProfit() {
        double totalProfit = 0.0;
        for(Truck truck : trucks) {
            totalProfit += truck.getTotalProfit();
        }
        return totalProfit;
    }
}
