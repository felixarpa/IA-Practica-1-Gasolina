package model;

public class Request {
    private Coordinate coordinate;
    private int days;

    public Request(Coordinate coordinate, int days){
        this.coordinate = coordinate;
        this.days = days;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    double getProfit() {
        return 10 * (
                (days == 0) ?
                        102 :
                        100 - Math.pow(2, days)
                );
    }

    public Request clone() {
        return new Request(coordinate.clone(), days);
    }

    public  Request cloneWithOneExtraDay() {return new Request(coordinate.clone(), days+1);}

    public void print() {
        System.out.print(days + " (" + coordinate.getCoordX() + "," + coordinate.getCoordY() + ")");
    }
}
