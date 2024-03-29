package correcorrerework.game.enemy;


import android.graphics.Canvas;
import android.graphics.Rect;


public interface Enemy {
    int[] getActualSpeed();
    void setActualSpeed(int[] speed);
    boolean checkCollisionEnemy();
    void calcAcelerationEnemy();
    void randomControls();
    void moveSprite();
    void printEnemyGrid(Canvas c);
    void moveY(long speed);
    void moveX(long speed);
    void moveEnemyPos(int[] speed);
    void printEnemy(Canvas c);
    int[] getSpeed();
    void moveEnemyActualSpeed();
    Rect getRect();
    void setDirection();
    Rect getHitBox();
    void checkCollisionPenguin(Rect pen);
}
