package correcorre.weapons;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;

public class FerretBullet implements Bullet {

    private Drawable pos1;
    private Drawable pos2;
    private Drawable pos3;
    private Drawable pos4;
    private Drawable pos5;
    private Drawable pos6;
    private Drawable pos7;
    private Drawable pos8;

    private int speedCount = 0;
    private int actualPos;

    private boolean penguin = false;
    protected Rect penguinHitBox;
    private boolean enemy = false;
    private Drawable d;
    private Rect r = new Rect();
    private int width;
    private int height;
    protected int[] speed = new int[] {0,0};
    protected int[] actualSpeed = new int[] {0,0};
    private static MatrixX matrixX;
    private int orientation;

    public FerretBullet(Main m, MatrixX ma, Rect pen,boolean rLeft, boolean rRight, int orientation) {

        matrixX = ma;

        this.width = ma.getSize();
        this.height = ma.getSize()/2;

        this.pos1 = VectorDrawableCompat.create(m.getResources(), R.drawable.b_caca1,null);
        this.pos2 = VectorDrawableCompat.create(m.getResources(), R.drawable.b_caca2,null);
        this.pos3 = VectorDrawableCompat.create(m.getResources(), R.drawable.b_caca3,null);
        this.pos4 = VectorDrawableCompat.create(m.getResources(), R.drawable.b_caca4,null);
        this.pos5 = VectorDrawableCompat.create(m.getResources(), R.drawable.b_caca5,null);
        this.pos6 = VectorDrawableCompat.create(m.getResources(), R.drawable.b_caca6,null);
        this.pos7 = VectorDrawableCompat.create(m.getResources(), R.drawable.b_caca7,null);
        this.pos8 = VectorDrawableCompat.create(m.getResources(), R.drawable.b_caca8,null);

        this.d = pos1;

        this.orientation = orientation;

        if (rRight) {
            this.r.right = pen.left;
            this.r.left = this.r.right - width;
            this.r.top = pen.top + ma.getSize() + ma.getSize()/3;
            this.r.bottom = this.r.top + this.height;
            this.speed[0] = 750;
            if (orientation == 2) {
                this.speed[1] = 250;
            }
        }
        if (rLeft) {
            this.r.left = pen.right;
            this.r.right = this.r.left + width;
            this.r.top = pen.top + ma.getSize() + ma.getSize()/3;
            this.r.bottom = this.r.top + this.height;
            this.speed[0] = -750;
            if (orientation == 2) {
                this.speed[1] = 250;
            }
        }
    }

    public Drawable getDrawable() {
        this.d.setBounds(this.r);
        return this.d;
    }
    public synchronized void moveX(long speed) {
        speed = -speed;
        this.r.left += speed;
        this.r.right += speed;

    }
    public synchronized void moveY(long speed) {
        speed = -speed;
        this.r.top += speed;
        this.r.bottom += speed;
    }

    public synchronized void printBullet(Canvas c) {
        this.getDrawable().draw(c);
    }

    public synchronized void checkColissionBullet() {// colission with scenario
        for (int xM = 0; xM < matrixX.matrix.size(); xM++) {
            for (int yM = 0; yM < matrixX.matrix.get(xM).size(); yM++) {
                if (matrixX.matrix.get(xM).get(yM).getSolid() == 1) {
                    if (Rect.intersects(this.r,matrixX.matrix.get(xM).get(yM).getRect())) {
                        matrixX.bulletList.remove(this);
                    }
                }
            }
        }//CHECK OUT OF SCREEN
        if (this.r.left < -matrixX.getSize()*2 || this.r.right > matrixX.getWidth() + matrixX.getSize()*2 ||
            this.r.top < - matrixX.getSize()*2 || this.r.bottom > matrixX.getHeight() + matrixX.getSize()*2) {
            System.out.println(-matrixX.getSize()*2);
            matrixX.bulletList.remove(this);
        }
    }
    public synchronized boolean checkColissionBulletEnemy() { //colission with enemys
        if (penguin) {
            if (matrixX.enemyList != null) {
                for (int x = 0; x < matrixX.enemyList.size(); x++) {
                    if (Rect.intersects(this.r,matrixX.enemyList.get(x).getRect())) {//getHitBox()
                        matrixX.generateExplosion(matrixX.enemyList.get(x).getRect());
                        matrixX.enemyList.remove(matrixX.enemyList.get(x));// SUSTITUIR POR MORIRRRRRRRRRRRRRRRRRRRRRRRR
                        matrixX.bulletList.remove(this);
                        return true;
                    }
                }
            }
        }
        //if (enemy) {
            //if (Rect.intersects(this.r,penguinHitBox)) {
                //System.out.println("tocado"); //("lo mismo que si es tocado"); //matrixX checkEnemyColission():
            //}
        //}
        return false;
    }
    public synchronized void setActualSpeed(int[] speed) {
        this.actualSpeed = speed;
    }
    public synchronized int[] getSpeed() {
        return this.speed;
    }
    public void moveBulletSprite() {//recibe real fps
            speedCount += Math.abs(actualSpeed[0]);
            if (speedCount >= 30) {
                if (actualPos == 1) this.d = pos1;
                if (actualPos == 2) this.d = pos2;
                if (actualPos == 3) this.d = pos3;
                if (actualPos == 4) this.d = pos4;
                if (actualPos == 5) this.d = pos5;
                if (actualPos == 6) this.d = pos6;
                if (actualPos == 7) this.d = pos7;
                if (actualPos == 8) this.d = pos8;
                actualPos++;
                if (actualPos > 8) actualPos = 1;
                speedCount = 0;
            }
    }
    public synchronized void moveBulletActualSpeed() {
        moveBulletPos(this.actualSpeed);
    }
    public synchronized void moveBulletPos(int[] speed) {
        moveX(speed[0]);
        moveY(speed[1]);
    }
    public synchronized void setPenguin() {
        this.penguin = true;
    }
    public synchronized boolean getPenguin() {
        return this.penguin;
    }
    public synchronized void setEnemy(Rect penguinHitBox) {
        this.enemy = true;
        this.penguinHitBox = penguinHitBox;
    }
    public synchronized boolean getEnemy() {
        return this.enemy;
    }
}
