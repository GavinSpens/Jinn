package utility;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
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

    // Getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
