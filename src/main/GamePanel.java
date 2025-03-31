package main;

import data.Settings;
import gameobject.*;
import level.Level;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final Settings settings;

    private final Level level;
    private final PlayerObject player;

    Thread gameThread;
    KeyHandler keyHandler;

    public GamePanel() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        settings = new Settings(screenSize);

        keyHandler = new KeyHandler();
        level = new Level(1);
        player = new PlayerObject();

        this.setPreferredSize(screenSize);
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
        double drawInterval = (double) 1000000000 / settings.FPS; // number of nanoseconds (ticks) between frames
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

        level.draw(g2, this);

        player.draw(g2, this);

        g2.dispose();
    }
}
