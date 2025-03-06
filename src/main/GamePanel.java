package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Settings
    final int originalTileSize = 10; // 16x16 tile
    final int scale = 5;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player();
    Level level = new Level(1);

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
        double drawInterval = (double) 1000000000 / FPS;
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
