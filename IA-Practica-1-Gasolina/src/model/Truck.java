package model;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;

import java.util.ArrayList;

public class Truck {
    private final int MAX_DISTANCE = 640;
    private ArrayList<Trip> trips;
    private Coordinate origin;

    public Truck(Coordinate origin) {
        this.trips = new ArrayList<>();
        this.origin = origin;
    }

    Truck(ArrayList<Trip> trips, Coordinate origin) {
        this.trips = trips;
        this.origin = origin;
    }

    public Coordinate getOrigin() {
        return origin;
    }

    public int getTripsSize(){
        return trips.size();
    }

    public Trip getTripAt(int index) {
        return trips.get(index);
    }

    public void removeTripAt(int index) {
        trips.remove(index);
    }

    public boolean addTrip(Trip trip) {
        return newTravelDistanceWith(trip) <= MAX_DISTANCE && trips.size() < 5 && trips.add(trip);
    }

    public Boolean replaceTripIfFits(int index, Trip trip) {
        if (newTravelDistanceWith(trip) - calculateTripDistance(trips.get(index)) <= MAX_DISTANCE) {
            trips.set(index, trip);
            return true;
        } else {
            return false;
        }
    }

    public double getTotalProfit() {
        double totalProfit = 0.0;
        for(Trip trip : trips) {
            totalProfit += trip.getTotalTripProfit(origin);
        }
        return totalProfit;
    }

    private int calculateTripDistance(Trip trip) {
        return  trip.getTripDistance(origin);
    }


    private int newTravelDistanceWith(Trip trip) {
        return calculateTripDistance(trip) + getTotalDistance();
    }

    private int getTotalDistance() {
        int total = 0;
        for(Trip trip : trips) {
            total += trip.getTripDistance(origin);
        }
        return total;
    }

    public static ArrayList<Truck> createTrucksFrom(CentrosDistribucion centrosDistribucion) {
        ArrayList<Truck> result = new ArrayList<>();
        for (Distribucion distribucion : centrosDistribucion) {
            result.add(new Truck(new Coordinate(distribucion.getCoordX(), distribucion.getCoordY())));
        }
        return result;
    }

    public boolean isFull() {
        return trips.size() >= 5;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public Truck clone() {
        ArrayList<Trip> clonedTrips = new ArrayList<>();
        for (int i = 0; i < trips.size(); i++) {
            clonedTrips.add(trips.get(i).clone());
        }
        return new Truck(clonedTrips, origin.clone());
    }

    public void print(int id) {
        System.out.println("Truck " + id + ":");
        for (Trip trip : trips) {
            System.out.print("    ");
            trip.print();
        }
    }
}
