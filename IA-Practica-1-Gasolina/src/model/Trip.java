package model;

public class Trip {
    protected Request request1;
    protected Request request2;

    public Trip(Request request1, Request request2) {
        this.request1 = request1;
        this.request2 = request2;
    }

    public Trip() {
    }

    public void setRequest(int req, Request request){
        switch (req % 2) {
            case 1:
                this.request1 = request;
                break;

            case 2:
                this.request2 = request;
                break;
        }
    }

    public Request getRequest(int req) {
        switch (req % 2) {
            case 1:
                return request1;
            case 0:
                return request2;
        }
        return null;
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

    public void print() {
        request1.print();
        System.out.print(" | ");
        request2.print();
        System.out.println();
    }
}
