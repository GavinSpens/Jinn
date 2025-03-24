package main;

public class GameObject {
    private Vector2D levelCollisions;

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
        levelCollisions = new Vector2D(0,0);
    }

    public boolean checkCollision(GameObject other) {
        return bodyHitBox.checkCollision(other.bodyHitBox);
    }

    private void resetLevelCollisions() {
        levelCollisions.setX(0);
        levelCollisions.setY(0);
    }

    public void checkCollisions(Level level) {
        resetLevelCollisions();

        checkLeftCollisions(level);

    }

    private void checkLeftCollisions(Level level) {
        Coordinate[] pixels = new Coordinate[2];
        pixels[0] = new Coordinate(x - 1, y);
        pixels[1] = new Coordinate(x - 1, y + height - 1);

        for (Coordinate pixel : pixels) {
            if (pixel == null) {
                throw new RuntimeException("pixel was null");
            }
            TileType type = level.getTileAtPixel(pixel.x, pixel.y);
            switch (type) {
                case FLOOR:
                    levelCollisions.setX(1);
                case NOTHING:
                    // do nothing
                default:
                    throw new RuntimeException("oopsie Daisy TileType was not in list");
            }
        }
    }
}
