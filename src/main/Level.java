package main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Level {
    public Tile[][] tiles;



    public Level(int levelNum) {
        getLevelData("Level" + levelNum + ".txt");
    }

    public TileType getTileAtPixel(int x, int y) {
        return tiles[x / Settings.tileSize][y / Settings.tileSize].tileType;
    }

    private void getLevelData(String filename) {
        int[][] levelData;
        levelData = new int[16][12];
        String filePath = "C:\\Users\\gavin\\OneDrive\\Desktop\\Random_programming\\Java\\Jinn\\src\\data\\" + filename;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
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
        tiles = new Tile[16][12];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(levelData[i][j]);
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int x = i * 50;
                int y = j * 50;
                switch (tiles[i][j].tileType) {
                    case FLOOR:
                        g2.setColor(Color.WHITE);
                        g2.fillRect(x, y, 50, 50);
                    case NOTHING:
                        continue;
                    default:
                        throw new RuntimeException();
                }
            }
        }
    }
}
