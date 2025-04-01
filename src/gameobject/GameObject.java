package gameobject;


import data.Settings;
import level.Level;
import level.TileType;
import utility.Coordinate;
import utility.Vector2D;

import javax.swing.*;
import java.awt.*;

public class GameObject {
    private final int tileSize;

    public final int width;
    public final int height;
    public int x;
    public int y;
    public Vector2D vel;

    private final int extraWidthChecks;
    private final int extraHeightChecks;

    public HitBox bodyHitBox;
    public Sprite sprite;

    public GameObject(int width, int height, int x, int y, HitBoxType hitboxType, Sprite sprite) {
        tileSize = Settings.getInstance().TILE_SIZE;

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.vel = new Vector2D();

        extraWidthChecks = (width - 2) / tileSize;
        extraHeightChecks = (height - 2) / tileSize;

        if (sprite != null) {
            this.sprite = sprite;
        }
        if (hitboxType != null) {
            bodyHitBox = new HitBox(x, y, width, height, hitboxType);
        }
    }

    public void draw(Graphics2D g2, JPanel jPanel) {
        sprite.draw(x, y, g2, jPanel);
    }

    public boolean checkCollision(GameObject other) {
        return bodyHitBox.checkCollision(other.bodyHitBox);
    }

    public TileType checkCollisions(Level level) {
        // rightmost and lowermost pixel of object
        int right = x + width - 1;
        int down = y + height - 1;

        var upLeftPixel = new Coordinate(x, y);
        var upRightPixel = new Coordinate(right, y);
        var downLeftPixel = new Coordinate(x, down);
        var downRightPixel = new Coordinate(right, down);

        // get corner TileTypes
        var upLeft = level.getTileAtPixel(upLeftPixel);
        var upRight = level.getTileAtPixel(upRightPixel);
        var downLeft = level.getTileAtPixel(downLeftPixel);
        var downRight = level.getTileAtPixel(downRightPixel);

        var highestPriority = getHigherPriority(upLeft, upRight);
        highestPriority = getHigherPriority(highestPriority, downLeft);
        highestPriority = getHigherPriority(highestPriority, downRight);

        // check collisions along edges
        // if width or height is greater than tile size
        for (int i = 0; i < extraWidthChecks; i++) {

            int pixel_x = x + ((i + 1) * tileSize);

            var upPixel = new Coordinate(pixel_x, y);
            var downPixel = new Coordinate(pixel_x, down);

            var thisUpTile = level.getTileAtPixel(upPixel);
            var thisDownTile = level.getTileAtPixel(downPixel);

            highestPriority = getHigherPriority(highestPriority, thisUpTile);
            highestPriority = getHigherPriority(highestPriority, thisDownTile);
        }

        for (int i = 0; i < extraHeightChecks; i++) {

            int pixel_y = y + ((i + 1) * tileSize);

            var leftPixel = new Coordinate(x, pixel_y);
            var rightPixel = new Coordinate(right, pixel_y);

            var thisLeftTile = level.getTileAtPixel(leftPixel);
            var thisRightTile = level.getTileAtPixel(rightPixel);

            highestPriority = getHigherPriority(highestPriority, thisLeftTile);
            highestPriority = getHigherPriority(highestPriority, thisRightTile);
        }

        return highestPriority;
    }

    private TileType getHigherPriority(TileType tile1, TileType tile2) {
        return tile1.compareTo(tile2) > 0 ? tile1 : tile2;
    }

    public int distToNextTileUp() {
        return (y + height - 1) % tileSize + 1;
    }

    public int distToNextTileDown() {
        return tileSize - y % tileSize;
    }

    public int distToNextTileLeft() {
        return (x + width - 1) % tileSize + 1;
    }

    public int distToNextTileRight() {
        return tileSize - x % tileSize;
    }
}
