package utility;

import data.Settings;

public record Coordinate(int x, int y) {
    public int getXTile() {
        return x / Settings.tileSize;
    }
    public int getYTile() {
        return y / Settings.tileSize;
    }
}
