package gameobject;

import level.TileType;

import static level.TileType.NONE;

public record LevelCollisionsObject(
        TileType left,
        TileType right,
        TileType top,
        TileType bottom
) {
    public LevelCollisionsObject() {
        this(NONE, NONE, NONE, NONE);
    }
}