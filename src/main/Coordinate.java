package main;

public class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getXTile() {
        return x / Settings.tileSize;
    }

    public int getYTile() {
        return y / Settings.tileSize;
    }
}
