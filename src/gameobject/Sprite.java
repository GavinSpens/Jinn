package gameobject;

import data.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Sprite {
    private final int scale = Settings.getInstance().getScale();
    private final int width;
    private final int height;
    private final String imgPath = Settings.getInstance().IMAGE_FOLDER + "player.png";

    public Sprite(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(int x, int y, Graphics2D g2, JPanel jPanel) {
        Image image;
        try {
            image = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(image, x * scale, y * scale, width * scale, height * scale, jPanel);
    }
}
