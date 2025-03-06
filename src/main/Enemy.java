package main;

import java.awt.Color;

public class Enemy {
    public enum EnemyType {
        WALKER,
    }

    public final int size = 50;
    public final int height = size;
    public final int width = size;
    
    public final Color color = Color.RED;

    public final double walkSpeed = 2;

    public double velX;
    public double velY;

    public int x;
    public int y;

    public EnemyType type;

    public Enemy(int x, int y, EnemyType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.velX = 0;
        this.velY = 0;
    }
}
