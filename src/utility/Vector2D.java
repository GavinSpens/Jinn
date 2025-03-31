package utility;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D normalize() {
        return new Vector2D(x / magnitude(), y / magnitude());
    }

    public Vector2D normalize(boolean inplace) {
        if (inplace) {
            x = x / magnitude();
            y = y / magnitude();
            return this;
        } else {
            return new Vector2D(x / magnitude(), y / magnitude());
        }
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D negative() {
        return new Vector2D(-x, -y);
    }
}
