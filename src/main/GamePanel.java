package main;

import data.Settings;
import gameobject.Player;
import level.Level;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player();
    Level level = new Level(1);

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(Settings.screenWidth, Settings.screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / Settings.FPS; // number of nanoseconds (ticks) between frames
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            long remainingTime = (long) (nextDrawTime - System.nanoTime()) / 1000000;
            if (remainingTime > 0) {
                try {
                    Thread.sleep(remainingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            nextDrawTime += drawInterval;
        }
    }

    public void update() {
        player.update(keyHandler, level);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        level.draw(g2);

        g2.setColor(Color.WHITE);
        g2.fillRect(player.x, player.y, player.width, player.height);

        g2.dispose();
    }
}
