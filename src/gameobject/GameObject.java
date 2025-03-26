package gameobject;


import level.Level;
import level.TileType;
import utility.Coordinate;
import utility.Vector2D;

import static level.TileType.*;

public class GameObject {
    private LevelCollisionsObject levelCollisions;

    public final int width;
    public final int height;
    public int x;
    public int y;

    public HitBox bodyHitBox;

    public Vector2D vel;

    public GameObject(int width, int height, int x, int y, HitBoxType hitboxType) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        if (hitboxType != null) {
            bodyHitBox = new HitBox(x, y, width, height, hitboxType);
        }
        vel = new Vector2D(0,0);
        levelCollisions = new LevelCollisionsObject();
    }

    public boolean checkCollision(GameObject other) {
        return bodyHitBox.checkCollision(other.bodyHitBox);
    }

    public void checkCollisions(Level level) {
        var topLeft = checkTopLeftCollisions(level);
        var topRight = checkTopRightCollisions(level);
        var bottomLeft = checkBottomLeftCollisions(level);
        var bottomRight = checkBottomRightCollisions(level);
        
        levelCollisions = new LevelCollisionsObject(topLeft, topRight, bottomLeft, bottomRight);
    }

    private TileType checkTopLeftCollisions(Level level) {
        Coordinate pixel = new Coordinate(x, y);
        return level.getTileAtPixel(pixel);
    }

    private TileType checkTopRightCollisions(Level level) {
        Coordinate pixel = new Coordinate(x + width - 1, y);
        return level.getTileAtPixel(pixel);
    }

    private TileType checkBottomLeftCollisions(Level level) {
        Coordinate pixel = new Coordinate(x, y + height - 1);
        return level.getTileAtPixel(pixel);
    }

    private TileType checkBottomRightCollisions(Level level) {
        Coordinate pixel = new Coordinate(x + width - 1, y + height - 1);
        return level.getTileAtPixel(pixel);
    }
}
