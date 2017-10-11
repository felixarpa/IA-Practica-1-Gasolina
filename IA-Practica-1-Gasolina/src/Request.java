public class Request {
    private Coordinate Coordinate;
    private Integer Days;

    public Request(Coordinate coordinate, Integer days){
        Coordinate = coordinate;
        Days = days;
    }


    public Coordinate getCoordinate() {
        return Coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        Coordinate = coordinate;
    }

    public Integer getDays() {
        return Days;
    }

    public void setDays(Integer days) {
        Days = days;
    }


}
