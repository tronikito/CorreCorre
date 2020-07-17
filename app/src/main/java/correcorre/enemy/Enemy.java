package correcorre.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface Enemy {
    int[] getActualSpeed();
    void setActualSpeed(int[] speed);
    void checkColissionEnemy();
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
    int getWidth();
    int getHeight();
    void setDirection();
    Rect getHitBox();
    void checkColissionPenguin(Rect pen);
}
