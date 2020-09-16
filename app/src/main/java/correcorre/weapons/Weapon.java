package correcorre.weapons;

import android.graphics.Canvas;
import android.graphics.Rect;

import correcorre.enemy.Enemy;
import correcorre.penguin.Penguin;

public interface Weapon {
    void moveX(long speed);
    void moveY(long speed);
    Rect getRect();
    void printWeapon(Canvas c);
    boolean getEnemy();
    boolean getPenguin();
    void setEnemy(Enemy e);
    void setPenguin(Rect p,boolean rLeft,boolean rRight, int orientation);
    String getType();
    void setSprite(String direction);
    String getWeaponType();
    void setDirection(String direction);
    void setOrientation(int o);
    void setBulletSpeed(int percent);
    int[] getBulletSpeed();
    int getOrientation();
}
