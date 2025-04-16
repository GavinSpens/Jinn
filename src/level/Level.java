package level;

import data.Settings;
import utility.Coordinate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Level {
    private final int tileSize;
    private int tilesX;
    private int tilesY;
    private final String imgPath = Settings.getInstance().IMAGE_FOLDER + "block.png";
    private final Image block;

    public Tile[][] tiles;

    public Level(int levelNum) {
        tileSize = Settings.getInstance().getTileSizeInRealPixels();
        getLevelData("Level" + levelNum + ".txt");

        try {
            block = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TileType getTileAtPixel(Coordinate coordinate) {
        int xTile = coordinate.getXTile();
        int yTile = coordinate.getYTile();
        return tiles[xTile][yTile].tileType;
    }

    private void getLevelData(String filename) {
        int[][] levelData;
        String filePath = "C:\\Users\\gavin\\OneDrive\\Desktop\\Random_programming\\Java\\Jinn\\src\\data\\" + filename;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 0;
            if ((line = reader.readLine()) != null) {
                String[] dimensions = line.split(" ");
                tilesX = Integer.parseInt(dimensions[0]);
                tilesY = Integer.parseInt(dimensions[1]);
            }
            levelData = new int[tilesX][tilesY];
            while ((line = reader.readLine()) != null) {
                line = line.replace(" ", "");
                for (int i = 0; i < line.length(); i++) {
                    levelData[i][lineNum] = line.charAt(i) - '0';
                }
                lineNum++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        createLevel(levelData);
    }

    private void createLevel(int[][] levelData) {
        tiles = new Tile[tilesX][tilesY];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(levelData[i][j]);
            }
        }
    }

    public void draw(Graphics2D g2, JPanel jPanel) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int x = i * tileSize;
                int y = j * tileSize;
                switch (tiles[i][j].tileType) {
                    case FLOOR:
                        g2.drawImage(block, x, y, tileSize, tileSize, jPanel);
                    case NONE:
                        continue;
                    default:
                        throw new RuntimeException();
                }
            }
        }
    }
}
