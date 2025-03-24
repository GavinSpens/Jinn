package main;

public class HitBox {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final HitBoxType type;

    public HitBox(int x, int y, int width, int height, HitBoxType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public boolean checkCollision(HitBox other) {
        // the -1's are because 1 pixel has width 1
        // x + width is the pixel next to the right edge, not on the right edge.

        if (other.x + other.width - 1 < x) {
            // other is outside to the left
            return false;
        }
        if (x + width - 1 < other.x) {
            // other is outside to the right
            return false;
        }
        if (other.y + other.height - 1 < y) {
            // other is outside above
            return false;
        }
        if (y + height - 1 < other.y) {
            // other is outside below
            return false;
        }
        return true;
    }

    public boolean checkCollision(Level level) {

        return false;
    }

    public Vector2D getVectorTo(HitBox other) {
        Vector2D center = new Vector2D(
                x + (double)width / 2,
                y + (double)height / 2);
        Vector2D otherCenter = new Vector2D(
                other.x + (double)other.width / 2,
                other.y + (double)other.height / 2);
        return center.add(otherCenter.negative());
    }

    public Vector2D getNormalizedVectorTo(HitBox other) {
        return getVectorTo(other).normalize();
    }

    public HitBoxType getType() {
        return type;
    }
}
