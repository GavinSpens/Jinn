package gameobject;

import java.awt.Color;

public class Enemy extends GameObject {
    public enum EnemyType {
        WALKER,
    }

    public final int size = 50;
    
    public final Color color = Color.RED;

    public final double walkSpeed = 2;

    public EnemyType type;

    public Enemy(int x, int y, EnemyType type) {
        super(50, 50, x, y, HitBoxType.ENEMY);
        this.type = type;
    }
}
