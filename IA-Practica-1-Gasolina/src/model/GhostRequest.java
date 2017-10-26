package model;

public class GhostRequest extends Request {

    public GhostRequest(Request request) {
        super(request.getCoordinate().clone(), 0);
    }

    public GhostRequest() {
        super(new Coordinate(0, 0), 0);
    }

    @Override
    double getProfit() {
        return 0;
    }

    @Override
    public GhostRequest clone() {
        return new GhostRequest(this);
    }
}
