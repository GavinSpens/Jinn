package gameobject;

import level.Level;
import level.Tile;
import main.KeyHandler;

import static level.TileType.*;

public class Player {
    private final int floor = 500;

    public final int startX = floor;
    public final int startY = floor;

    public final int size = 50;
    public final int height = size;
    public final int width = size;

    public final double maxHorizontalSpeed = 10;
    public final double maxVerticalSpeed = 20;
    
    public final double accelX = 1;
    public final double accelJump = 16;
    public final double friction = 1;
    public final double gravity = 2;
    public final double gravityWhileHoldingJump = 1;

    public final double bumpHeadFraction = 0.2;

    public double velX;
    public double velY;

    public int x;
    public int y;

    private boolean collisionLeft = false;
    private boolean collisionRight = false;
    private boolean collisionUp = false;
    private boolean collisionDown = false;

    private int distToTop;
    private int distToBottom;
    private int distToLeft;
    private int distToRight;

    public Player() {
        velX = 0;
        velY = 0;
        x = startX;
        y = startY;
    }

    public void update(KeyHandler keyHandler, Level level) {
        // Calculate x velocity from upLeft/upRight key presses
        if (keyHandler.leftPressed ^ keyHandler.rightPressed) {
            if (keyHandler.leftPressed) {
                velX -= accelX;
            } else {
                velX += accelX;
            }
        } else {
            if (velX > 0) {
                velX -= friction;
            } else if (velX < 0) {
                velX += friction;
            }
        }

        // Reset velocity by collisions
        if (collisionRight && velX > 0) {
            velX = 0;
        }
        if (collisionLeft && velX < 0) {
            velX = 0;
        }

        // jump and gravity
        if (collisionDown) {
            velY = 0;
            if (keyHandler.spaceJustPressed) {
                velY = -accelJump;
                keyHandler.spaceJustPressed = false;
            }
        } else {
            if (keyHandler.spacePressed && velY < 0) {
                velY += gravityWhileHoldingJump;
            } else {
                velY += gravity;
            }
        }

        accountForMaxSpeed();

        // move player coordinates
        x += velX;
        y += velY;

        // checks collisions and corrects player position
        checkCollisions_(level);
    }

    private void checkCollisions_(Level level) {
        levelCollisions(level);

        if (collisionRight && collisionLeft) {
            if (collisionDown) {
                y -= distToTop;
            } else if (collisionUp) {
                y += distToBottom;
            }
            levelCollisions(level);
        }

        if (collisionUp && collisionDown) {
            if (collisionRight) {
                x -= distToLeft;
            } else if (collisionLeft) {
                x += distToRight;
            }
            levelCollisions(level);
        }

        if (collisionRight && collisionDown
                && distToTop != 0 && distToLeft != 0) {
            if (distToTop > distToLeft) {
                x -= distToLeft;
            } else {
                y -= distToTop;
            }
            levelCollisions(level);
        }

        if (collisionLeft && collisionDown
                && distToTop != 0 && distToRight != 0) {
            if (distToTop > distToRight) {
                x += distToRight;
            } else {
                y -= distToTop;
            }
            levelCollisions(level);
        }

        if (collisionRight && collisionUp
                && distToBottom != 0 && distToLeft != 0) {
            if (distToBottom > distToLeft) {
                x -= distToLeft;
            } else {
                y += distToBottom;
            }
            levelCollisions(level);
        }

        if (collisionLeft && collisionUp
                && distToBottom != 0 && distToRight != 0) {
            if (distToBottom > distToRight) {
                x += distToRight;
            } else {
                y += distToBottom;
            }
            levelCollisions(level);
        }

        if (collisionDown) {
            y -= distToTop;
        }
        if (collisionUp) {
            y += distToBottom;
            velY = velY * -bumpHeadFraction;
        }
        if (collisionRight) {
            x -= distToLeft;
        }
        if (collisionLeft) {
            x += distToRight;
        }
    }

    private void accountForMaxSpeed() {
        if (velX > maxHorizontalSpeed) {
            velX = maxHorizontalSpeed;
        } else if (velX < -maxHorizontalSpeed) {
            velX = -maxHorizontalSpeed;
        }
        if (velY > maxVerticalSpeed) {
            velY = maxVerticalSpeed;
        } else if (velY < -maxVerticalSpeed) {
            velY = -maxVerticalSpeed;
        }
    }
    
    private void levelCollisions(Level level) {
        distToTop = y % size;
        distToBottom = size - 1 - ((y - 1) % size);
        distToLeft = x % size;
        distToRight = size - 1 - ((x - 1) % size);

        int leftPixel = x - 1;
        int rightPixel = x + size;
        int topPixel = y - 1;
        int bottomPixel = y + size;

        int rightEdge = rightPixel - 1;
        int bottomEdge = bottomPixel - 1;

        Tile bottomTileLeft = level.tiles[x / size][bottomPixel / size];
        Tile bottomTileRight = level.tiles[rightEdge / size][bottomPixel / size];
        Tile topTileLeft = level.tiles[x / size][topPixel / size];
        Tile topTileRight = level.tiles[rightEdge / size][topPixel / size];
        Tile leftTileTop = level.tiles[leftPixel / size][y / size];
        Tile leftTileBottom = level.tiles[leftPixel / size][bottomEdge / size];
        Tile rightTileTop = level.tiles[rightPixel / size][y / size];
        Tile rightTileBottom = level.tiles[rightPixel / size][bottomEdge / size];

        collisionDown =
                bottomTileLeft.tileType == FLOOR
                || bottomTileRight.tileType == FLOOR;

        collisionUp =
                topTileLeft.tileType == FLOOR
                || topTileRight.tileType == FLOOR;

        collisionLeft =
                leftTileTop.tileType == FLOOR
                || leftTileBottom.tileType == FLOOR;

        collisionRight =
                rightTileTop.tileType == FLOOR
                || rightTileBottom.tileType == FLOOR;
    }
}
