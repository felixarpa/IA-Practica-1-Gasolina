public class Coordinate {
    private int coordX;
    private int coordY;

    public Coordinate(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public static int distance(Coordinate from, Coordinate to) {
        return Math.abs(from.coordX - to.coordX) + Math.abs(from.coordY - to.coordY);
    }
}
