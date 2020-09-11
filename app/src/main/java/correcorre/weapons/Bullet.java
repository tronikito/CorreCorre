package correcorre.weapons;

import android.graphics.Canvas;

public interface Bullet {
    void moveX(long speed);
    void moveY(long speed);
    void printBullet(Canvas c);
    boolean checkColissionBullet();
    int[] getSpeed();
    void setActualSpeed(int[] speed);
    void moveBulletActualSpeed();
    boolean checkColissionBulletEnemy();
    void moveBulletSprite();
}