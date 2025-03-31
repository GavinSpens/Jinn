package gameobject;

import java.awt.Color;

public class Enemy extends GameObject {
    public final int size = 4;
    public final Color color = Color.RED;
    public final double walkSpeed = 2;

    public Enemy(int x, int y) {
        super(4, 4, x, y, HitBoxType.ENEMY, new Sprite(4, 4));
    }
}
