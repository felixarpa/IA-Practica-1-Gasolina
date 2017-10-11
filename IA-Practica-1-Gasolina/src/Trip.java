public class Trip {
    private Request request1;
    private Request request2;
    private double beneficio;

    public Trip(Request request1, Request request2, double beneficio) {
        this.request1 = request1;
        this.request2 = request2;
        this.beneficio = beneficio;
    }

    public void setRequest1(Request request1) {
        this.request1 = request1;
    }

    public void setRequest2(Request request2) {
        this.request2 = request2;
    }

    public void setBeneficio(double beneficio) {
        this.beneficio = beneficio;
    }

    public Coordinate getRequest1() {
        return request1;
    }

    public Coordinate getRequest2() {
        return request2;
    }

    public double getBeneficio() {
        return beneficio;
    }
}
