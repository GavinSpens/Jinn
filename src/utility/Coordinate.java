package utility;

import data.Settings;

public record Coordinate(int x, int y) {
    private static final int tileSize = Settings.getInstance().TILE_SIZE;

    public int getXTile() {
        return x / tileSize;
    }
    public int getYTile() {
        return y / tileSize;
    }
}
