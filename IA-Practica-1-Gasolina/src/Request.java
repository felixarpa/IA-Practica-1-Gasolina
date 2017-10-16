public class Request {
    private Coordinate coordinate;
    private Integer days;

    public Request(Coordinate coordinate, Integer days){
        this.coordinate = coordinate;
        this.days = days;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public double calculateProfit() {
        return 10 * (
                (days == 0) ?
                        102 :
                        100 - Math.pow(2, days)
                );
    }

}
