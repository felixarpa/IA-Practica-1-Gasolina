package model;

public class Coordinate {
    private int coordX;
    private int coordY;

    public Coordinate(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public static int distance(Coordinate from, Coordinate to) {
        return Math.abs(from.coordX - to.coordX) + Math.abs(from.coordY - to.coordY);
    }

    public Coordinate clone() {
        return new Coordinate(coordX, coordY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return this.coordX == that.coordX && this.coordY == that.coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
}
