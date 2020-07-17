package correcorre.weapons;

import android.graphics.Canvas;

public interface Bullet {
    void moveX(long speed);
    void moveY(long speed);
    void printBullet(Canvas c);
    void checkColissionBullet();
    int[] getSpeed();
    void setActualSpeed(int[] speed);
    void moveBulletActualSpeed();
    void checkColissionBulletEnemy();
    void moveBulletSprite();
}