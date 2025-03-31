package gameobject;

import data.Settings;
import level.Level;
import level.TileType;
import main.KeyHandler;

public class PlayerObject extends GameObject {
    private final Settings settings = Settings.getInstance();

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

        TileType collisions = checkCollisions(level);

        if ((int)vel.y == 0) {
            vel.y = 1;
        }
        x += (int)vel.x;
        y += (int)vel.y;

        updateForCollisions(collisions, keyHandler);
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
        if (keyHandler.spacePressed) {
            vel.y += settings.PLAYER_JUMP_GRAVITY;
        } else {
            vel.y += settings.GRAVITY;
        }
    }

    private void updateForCollisions(TileType tileType, KeyHandler keyHandler) {
        if (tileType != TileType.NONE) {
            if (movingRight()) {
                if (movingDown()) {
                    if (distToNextTileLeft() < distToNextTileUp()) {
                        updateForRightCollision();
                    } else {
                        updateForDownCollision(keyHandler);
                    }
                } else if (movingUp()) {
                    if (distToNextTileLeft() < distToNextTileDown()) {
                        updateForRightCollision();
                    } else {
                        updateForUpCollision();
                    }
                } else {
                    updateForRightCollision();
                }
            } else if (movingLeft()) {
                if (movingDown()) {
                    if (distToNextTileRight() < distToNextTileUp()) {
                        updateForLeftCollision();
                    } else {
                        updateForDownCollision(keyHandler);
                    }
                } else if (movingUp()) {
                    if (distToNextTileRight() < distToNextTileDown()) {
                        updateForLeftCollision();
                    } else {
                        updateForUpCollision();
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
                    throw new RuntimeException("Uhh... How'd you manage that?\nColliding Tile: " + tileType + "\nVelocity: " + vel);
                }
            }
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
        y -= distToNextTileUp();

        if (keyHandler.spaceJustPressed) {
            y -= (int) vel.y; // undo pre-collision-check move
            vel.y = settings.PLAYER_JUMP_V;
            y += (int) vel.y; // do the updated move
            keyHandler.spaceJustPressed = false;
        } else {
            vel.y = 0;
        }
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
