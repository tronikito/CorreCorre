package correcorre.weapons;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;
import correcorre.enemy.Enemy;
import correcorre.graficos.MatrixX;
import correcorre.scenario.Block;

public class Ferret implements Weapon {

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

    public Ferret(Main main, MatrixX ma, Block old) {
            matrixX = ma;
            //this.r.top = old.getRect().top;
            this.r.top = old.getRect().top + matrixX.getSize()/10;//gallina
            this.r.left = old.getRect().left ;
            this.r.right = this.r.left + matrixX.getSize()*2;
            this.r.bottom = this.r.top + matrixX.getSize()*2  + matrixX.getSize()/10;//gallina
            //this.r.bottom = this.r.top + matrixX.getSize()/2 +  matrixX.getSize()/3;
            this.type = "weapon";
            this.weaponType = "ferret";

        this.pos1 = VectorDrawableCompat.create(main.getResources(), R.drawable.w_ferretleft,null);
        this.pos2 = VectorDrawableCompat.create(main.getResources(), R.drawable.w_ferretright,null);
        this.empty = VectorDrawableCompat.create(main.getResources(), R.drawable.c_empty,null);
        this.d = VectorDrawableCompat.create(main.getResources(), R.drawable.w_ferret,null);
    }
    public void setSprite(String orientation) {
        if (orientation.equals("left")) {
            this.d = pos1;
        }
        if (orientation.equals("right")) {
            this.d = pos2;
        }
        if (orientation.equals("empty")) {
            this.d = empty;
        }
    }
    public String getType() {
        return this.type;
    }
    public String getWeaponType() {
        return this.weaponType;
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
    public void setPenguin(Rect pRect,boolean rLeft,boolean rRight) {

        //this.r.top = pRect.top+matrixX.getSize()+matrixX.getSize()/4;
        this.r.top = pRect.top+matrixX.getSize()/4;//gallina
        if (rLeft) {
            this.r.left = pRect.left+matrixX.getSize()/3;
            this.r.right = pRect.right+matrixX.getSize()/3;
        }
        if (rRight) {
            this.r.left = pRect.left-matrixX.getSize()/3;
            this.r.right = pRect.right-matrixX.getSize()/3;
        }
        this.r.bottom = pRect.bottom-matrixX.getSize()/2+matrixX.getSize()/4;//gallina
        //this.r.bottom = pRect.bottom-matrixX.getSize()/2;
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
    public synchronized void printWeapon(Canvas c) {
        this.getDrawable().draw(c);
    }
}
