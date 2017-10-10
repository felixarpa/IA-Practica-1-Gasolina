public class Trip {
    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;
    private double beneficio;

    public Trip(Coordinate firstCoordinate, Coordinate secondCoordinate, double beneficio) {
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
        this.beneficio = beneficio;
    }

    public void setFirstCoordinate(Coordinate firstCoordinate) {
        this.firstCoordinate = firstCoordinate;
    }

    public void setSecondCoordinate(Coordinate secondCoordinate) {
        this.secondCoordinate = secondCoordinate;
    }

    public void setBeneficio(double beneficio) {
        this.beneficio = beneficio;
    }

    public Coordinate getFirstCoordinate() {
        return firstCoordinate;
    }

    public Coordinate getSecondCoordinate() {
        return secondCoordinate;
    }

    public double getBeneficio() {
        return beneficio;
    }
}
