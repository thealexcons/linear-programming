package main.other;

import java.util.Arrays;

public class Coordinate2D {
    private final double x;
    private final double y;

    public Coordinate2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(x = " + getX() + ", y = " + getY() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate2D)) {
            return false;
        }
        Coordinate2D other = (Coordinate2D) obj;
        return x == other.getX() && y == other.getY();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
