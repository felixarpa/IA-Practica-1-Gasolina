package model;

public class Trip {
    Request request1;
    Request request2;

    public Trip(Request request1, Request request2) {
        this.request1 = request1;
        this.request2 = request2;
    }


    public double getTotalTripProfit(Coordinate truckCoordinate) {
        double profit1 = request1.getProfit();
        double profit2 = request2.getProfit();
        double loss = (getTripDistance(truckCoordinate)) * 2;
        return profit1 + profit2 - loss;
    }

    public double getTotalTripProfitNextDay(Coordinate truckCoordinate) {
        double profit1 = request1.cloneWithOneExtraDay().getProfit();
        double profit2 = request2.cloneWithOneExtraDay().getProfit();
        double loss = (getTripDistance(truckCoordinate)) * 2;
        return profit1 + profit2 - loss;
    }

    int getTripDistance(Coordinate origin) {
        return  Coordinate.distance(origin, request1.getCoordinate()) +
                Coordinate.distance(request1.getCoordinate(), request2.getCoordinate()) +
                Coordinate.distance(request2.getCoordinate(), origin);
    }

    public Trip clone() {
        return new Trip(request1.clone(), request2.clone());
    }

    void print() {
        request1.print();
        System.out.print(" | ");
        request2.print();
        System.out.println();
    }
}
