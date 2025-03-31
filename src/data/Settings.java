package data;

import java.awt.*;

public class Settings {
    private static Settings instance;

    public final int FPS = 5;
    public final int TILE_SIZE = 8;

    public final double GRAVITY = 0.2;

    public final int PLAYER_WIDTH = 6;
    public final int PLAYER_HEIGHT = 8;
    public final int PLAYER_START_X = 24;
    public final int PLAYER_START_Y = 64;
    public final double PLAYER_MAX_SPEED_X = 1;
    public final double PLAYER_MAX_SPEED_Y = 4;
    public final double PLAYER_WALK_ACCEL = 0.5;
    public final double PLAYER_JUMP_V = -4;
    public final double PLAYER_JUMP_GRAVITY = 0.1;
    public final double PLAYER_BUMP_HEAD_FRAC = 0.2;

    public final String IMAGE_FOLDER = "C:\\Users\\gavin\\OneDrive\\Desktop\\Random_programming\\Java\\Jinn\\src\\data\\images\\";

    private final int scale;
    private final int tileSizeInRealPixels;

    public Settings(Dimension screenSize) {
        int screenHeight = screenSize.height;
        tileSizeInRealPixels = screenHeight / 18;
        scale = tileSizeInRealPixels / TILE_SIZE;
        instance = this;
    }

    public static Settings getInstance() {
        if (instance == null) {
            throw new RuntimeException("No instance of Settings_ class exists");
        }
        return instance;
    }

    public int getScale() {
        return scale;
    }

    public int getTileSizeInRealPixels() {
        return tileSizeInRealPixels;
    }
}
