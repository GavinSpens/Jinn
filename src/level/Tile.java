package level;

public class Tile {
    public TileType tileType;

    public Tile(TileType type) {
        this.tileType = type;
    }

    public Tile(int type) {
        switch (type) {
            case (0) ->
                tileType = TileType.NONE;
            case (1) ->
                tileType = TileType.FLOOR;
            default ->
                throw new RuntimeException("No TileType matching " + type);
        }
    }
}
