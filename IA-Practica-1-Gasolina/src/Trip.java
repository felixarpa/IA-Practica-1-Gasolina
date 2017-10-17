public class Trip {
    private Request request1;
    private Request request2;

    public Trip(Request request1, Request request2) {
        this.request1 = request1;
        this.request2 = request2;
    }

    public Trip(){

    }

    public void setRequest1(Request request1) {
        this.request1 = request1;
    }

    public void setRequest2(Request request2) {
        this.request2 = request2;
    }

    public Request getRequest1() {
        return request1;
    }

    public Request getRequest2() {
        return request2;
    }

    public double getProfit(Coordinate truckCoordinate) {
        double profit1 = request1.calculateProfit();
        double profit2 = request2.calculateProfit();
        int distance1 = Coordinate.distance(truckCoordinate, request1.getCoordinate());
        int distance2 = Coordinate.distance(request1.getCoordinate(), request2.getCoordinate());
        int distance3 = Coordinate.distance(request2.getCoordinate(), truckCoordinate);
        double loss = (distance1 + distance2 + distance3) * 2;
        return profit1 + profit2 - loss;
    }
}
