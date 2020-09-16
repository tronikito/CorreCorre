package correcorre.weapons;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;
import correcorre.enemy.Enemy;
import correcorre.graficos.MatrixX;
import correcorre.penguin.Penguin;
import correcorre.scenario.Block;

public class Chicken implements Weapon {

    private static MatrixX matrixX;
    Drawable d;
    Drawable pos1;
    Drawable pos2;
    Drawable empty;
    Rect r = new Rect();
    private boolean penguinON = false;
    private boolean enemyON = false;
    private String type;
    private String weaponType;


    private Rect rectP;
    private int orientation;
    private boolean rLeft;
    private boolean rRight;
    private int xBulletSpeed = 750;
    private int yBulletSpeed = 750;
    private int[] speedBullet;


    public Chicken(Main main, MatrixX ma, Block old) {

        matrixX = ma;
        this.r.top = old.getRect().top + matrixX.getSize()/10;//gallina
        this.r.left = old.getRect().left ;
        this.r.right = this.r.left + matrixX.getSize()*2;
        this.r.bottom = this.r.top + matrixX.getSize()*2 + matrixX.getSize()/10;//gallina
        this.type = old.getType();
        this.weaponType = "chicken";
        this.speedBullet = new int[] {xBulletSpeed,0};

        this.pos1 = VectorDrawableCompat.create(main.getResources(), R.drawable.w_metralletagallinaleft,null);
        this.pos2 = VectorDrawableCompat.create(main.getResources(), R.drawable.w_metralletagallinaright,null);
        this.empty = VectorDrawableCompat.create(main.getResources(), R.drawable.c_empty,null);
        this.d = VectorDrawableCompat.create(main.getResources(), R.drawable.w_metralletagallina,null);
    }

    public void setBulletSpeed(int percent) {

        if (percent < 35) percent = 35;
        if (percent > 85) percent = 100;

        if (percent < 65) this.orientation = 1;
        if (percent >= 65) this.orientation = 0;
        if (percent <= 50) {
            speedBullet[0] = (xBulletSpeed/100) * percent*2;
            speedBullet[1] = (yBulletSpeed);
        }
        if (percent > 50) {
            speedBullet[0] = (xBulletSpeed);
            speedBullet[1] = (yBulletSpeed/100) * (100-percent);
        }

    }

    public int getOrientation() { return this.orientation; }
    public int[] getBulletSpeed() { return this.speedBullet; }

    public void setSprite(String direction) {// THIS NEED A BIG FIX
        if (direction.equals("left")) {
            this.rLeft = false;
            this.rRight = true;
            this.d = pos1;
            setOrientation(this.orientation);
        }
        if (direction.equals("right")) {
            this.rRight = false;
            this.rLeft = true;
            this.d = pos2;
            setOrientation(this.orientation);
        }
        if (direction.equals("empty")) {
            this.d = empty;
        }
    }

    public void setDirection(String d) {

        if (d.equals("left")) {
            rLeft = true;
            rRight = false;
            setSprite("right");
        }
        if (d.equals("right")) {
            rLeft = false;
            rRight = true;
            setSprite("left");
        }
    }

    public void setOrientation(int o) {

        this.orientation = o;

        this.r.top = rectP.top + matrixX.getSize() / 4;
        this.r.bottom = rectP.bottom - matrixX.getSize() / 2 + matrixX.getSize() / 4;

        if (rLeft && orientation == 0) {
            this.r.left = rectP.left + matrixX.getSize() / 3;
            this.r.right = rectP.right + matrixX.getSize() / 3;
        }
        if (rLeft && orientation == 1) {
            this.r.left = rectP.left + matrixX.getSize() / 4;
            this.r.right = rectP.right + matrixX.getSize() / 4;
            this.r.top = rectP.top - (matrixX.getSize() / 6);
            this.r.bottom = rectP.bottom - (matrixX.getSize() / 6);
        }
        if (rRight && orientation == 0) {
            this.r.left = rectP.left - matrixX.getSize() / 3;
            this.r.right = rectP.right - matrixX.getSize() / 3;
        }
        if (rRight && orientation == 1) {
            this.r.left = rectP.left - matrixX.getSize() / 4;
            this.r.right = rectP.right - matrixX.getSize() / 4;
            this.r.top = rectP.top - (matrixX.getSize() / 6);
            this.r.bottom = rectP.bottom - (matrixX.getSize() / 6);
        }
    }

    public String getWeaponType() {
        return this.weaponType;
    }
    public String getType() {
        return this.type;
    }
    public boolean getEnemy() {
        return this.enemyON;
    }
    public boolean getPenguin() {
        return this.penguinON;
    }
    public void setEnemy(Enemy e) {
        this.enemyON = true;
    }

    public void setPenguin(Rect p, boolean rLeft, boolean rRight, int orientation) {

        rectP = p;
        this.orientation = orientation;
        if (rLeft) setSprite("left");
        if (rRight) setSprite("right");
        this.penguinON = true;

    }

    public Drawable getDrawable() {
        this.d.setBounds(this.r);
        return this.d;
    }

    public synchronized void moveX(long speed) {
        if (!this.penguinON) speed = -speed;
        this.r.left += speed;
        this.r.right += speed;

    }

    public synchronized void moveY(long speed) {
        if (!this.penguinON) speed = -speed;
        this.r.top += speed;
        this.r.bottom += speed;
    }

    public synchronized Rect getRect() {
        return this.r;
    }

    public synchronized void printWeapon(Canvas c) {//FIXINGGGGGGGGGGGGGG
        if (this.orientation == 1) {
            if (rRight) {
                c.rotate(35, this.r.left + ((this.r.right - this.r.left) / 2), this.r.top + ((this.r.bottom - this.r.top) / 2));
                this.getDrawable().draw(c);
                c.rotate(-35, this.r.left + ((this.r.right - this.r.left) / 2), this.r.top + ((this.r.bottom - this.r.top) / 2));
            }
            if (rLeft) {
                c.rotate(-35, this.r.left + ((this.r.right - this.r.left) / 2), this.r.top + ((this.r.bottom - this.r.top) / 2));
                this.getDrawable().draw(c);
                c.rotate(35, this.r.left + ((this.r.right - this.r.left) / 2), this.r.top + ((this.r.bottom - this.r.top) / 2));
            }
        } else {
            this.getDrawable().draw(c);
        }
    }
}
