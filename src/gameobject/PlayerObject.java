package gameobject;

import data.Settings;
import level.Level;
import level.TileType;
import main.KeyHandler;
import utility.Coordinate;

public class PlayerObject extends GameObject {
    private final Settings settings = Settings.getInstance();

    private boolean grounded;

    public PlayerObject() {
        super(
                Settings.getInstance().PLAYER_WIDTH,
                Settings.getInstance().PLAYER_HEIGHT,
                Settings.getInstance().PLAYER_START_X,
                Settings.getInstance().PLAYER_START_Y,
                HitBoxType.PLAYER,
                new Sprite(
                        Settings.getInstance().PLAYER_WIDTH,
                        Settings.getInstance().PLAYER_HEIGHT
                )
        );
    }

    public void update(KeyHandler keyHandler, Level level) {
        updateVelX(keyHandler);
        updateVelY(keyHandler);

        accountForMaxSpeed();

//        if ((int)vel.y == 0) {
//            vel.y = 1;
//        }

        x += (int)vel.x;
        y += (int)vel.y;

        updateForCollisions(keyHandler, level);
    }

    private void updateVelX(KeyHandler keyHandler) {
        if (keyHandler.leftPressed ^ keyHandler.rightPressed) {
            if (keyHandler.leftPressed) {
                vel.x -= settings.PLAYER_WALK_ACCEL;
            } else {
                vel.x += settings.PLAYER_WALK_ACCEL;
            }
        } else {
            if (vel.x > 0) {
                vel.x -= settings.PLAYER_WALK_ACCEL;
            } else if (vel.x < 0) {
                vel.x += settings.PLAYER_WALK_ACCEL;
            }
        }
    }

    private void updateVelY(KeyHandler keyHandler) {
        if (grounded) {
            if (keyHandler.spacePressed) {
                vel.y = settings.PLAYER_JUMP_V;
                grounded = false;
            } else {
                vel.y = 0;
            }
        } else {
            if (keyHandler.spacePressed) {
                vel.y += settings.PLAYER_JUMP_GRAVITY;
            } else {
                vel.y += settings.GRAVITY;
            }
            if ((int)vel.y == 0) {
                vel.y = 1;
            }
        }
    }

    private void updateForCollisions(KeyHandler keyHandler, Level level) {
        if (grounded) {
            TileType leftGround = level.getTileAtPixel(new Coordinate(x, y + height));
            TileType rightGround = level.getTileAtPixel(new Coordinate(x + width - 1, y + height));
            if (leftGround != TileType.FLOOR && rightGround != TileType.FLOOR) {
                grounded = false;
            }
        }

        TileType collisions = checkCollisions(level);
        if (collisions != TileType.NONE) {
            if (movingRight()) {
                if (movingDown()) {
                    if (distToNextTileLeft() > distToNextTileUp()) {
                        updateForDownCollision(keyHandler);
                    } else {
                        updateForRightCollision();
                    }
                } else if (movingUp()) {
                    if (distToNextTileLeft() > distToNextTileDown()) {
                        updateForUpCollision();
                    } else {
                        updateForRightCollision();
                    }
                } else {
                    updateForRightCollision();
                }
            } else if (movingLeft()) {
                if (movingDown()) {
                    if (distToNextTileRight() > distToNextTileUp()) {
                        updateForDownCollision(keyHandler);
                    } else {
                        updateForLeftCollision();
                    }
                } else if (movingUp()) {
                    if (distToNextTileRight() > distToNextTileDown()) {
                        updateForUpCollision();
                    } else {
                        updateForLeftCollision();
                    }
                } else {
                    updateForLeftCollision();
                }
            } else {
                if (movingUp()) {
                    updateForUpCollision();
                } else if (movingDown()) {
                    updateForDownCollision(keyHandler);
                } else {
                    throw new RuntimeException("Uhh... How'd you manage that?\nColliding Tile: " + collisions + "\nVelocity: " + vel);
                }
            }
            updateForCollisions(keyHandler, level);
        }
    }

    private boolean movingRight() {
        return vel.x > 0;
    }

    private boolean movingLeft() {
        return vel.x < 0;
    }

    private boolean movingDown() {
        return vel.y > 0;
    }

    private boolean movingUp() {
        return vel.y < 0;
    }

    private void updateForDownCollision(KeyHandler keyHandler) {
        vel.y = 0;
        y -= distToNextTileUp();
        grounded = true;
//        if (keyHandler.spacePressed) {
//            vel.y = settings.PLAYER_JUMP_V;
//        } else {
//            vel.y = 0;
//        }
    }

    private void updateForUpCollision() {
        vel.y = 0;
        y += distToNextTileDown();
    }

    private void updateForLeftCollision() {
        vel.x = 0;
        x += distToNextTileRight();
    }

    private void updateForRightCollision() {
        vel.x = 0;
        x -= distToNextTileLeft();
    }

    private void accountForMaxSpeed() {
        double maxSpeedX = settings.PLAYER_MAX_SPEED_X;
        double maxSpeedY = settings.PLAYER_MAX_SPEED_Y;

        if (vel.x > maxSpeedX) {
            vel.x = maxSpeedX;
        } else if (vel.x < -maxSpeedX) {
            vel.x = -maxSpeedX;
        }
        if (vel.y > maxSpeedY) {
            vel.y = maxSpeedY;
        } else if (vel.y < -maxSpeedY) {
            vel.y = -maxSpeedY;
        }
    }
}
