package correcorrerework.game.weapons;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import static correcorrerework.ResourcesClass.*;



public class ChickenBullet extends BulletSprites implements Bullet{

    private int speedCount = 0;
    private int actualPos;

    private boolean penguin = false;
    protected Rect penguinHitBox;
    private boolean enemy = false;
    private Drawable d;
    private Rect r = new Rect();
    protected int[] speed = new int[] {0,0};
    protected int[] actualSpeed = new int[] {0,0};
    private int orientation;
    private boolean rRight;
    private boolean rLeft;
    private boolean firstTime = true;

    private Drawable pos1;
    private Drawable pos2;
    private Drawable pos3;
    private Drawable pos4;
    private Drawable pos5;
    private Drawable pos6;
    private Drawable pos7;
    private Drawable pos8;

    public ChickenBullet(Rect pen,boolean rLeft, boolean rRight, int orientation, int[] speed) {

        pos1 = bulletSprite1;
        pos2 = bulletSprite2;
        pos3 = bulletSprite3;
        pos4 = bulletSprite4;
        pos5 = bulletSprite5;
        pos6 = bulletSprite6;
        pos7 = bulletSprite7;
        pos8 = bulletSprite8;

        int width = sizeDot;
        int height = sizeDot / 2;


        int random = (int) Math.round(Math.random()*7+1);
        if (random == 1) {
            this.d = pos1;
            actualPos = 1;
        }
        if (random == 2) {
            this.d = pos2;
            actualPos = 2;
        }
        if (random == 3) {
            this.d = pos3;
            actualPos = 3;
        }
        if (random == 4) {
            this.d = pos4;
            actualPos = 4;
        }
        if (random == 5) {
            this.d = pos5;
            actualPos = 5;
        }
        if (random == 6) {
            this.d = pos6;
            actualPos = 6;
        }
        if (random == 7) {
            this.d = pos7;
            actualPos = 7;
        }
        if (random == 8) {
            this.d = pos8;
            actualPos = 8;
        }
        //this.d = bulletSprite1;

        this.rLeft = rLeft;
        this.rRight = rRight;
        this.orientation = orientation;

        if (rRight) {
            this.r.right = pen.left;
            this.r.left = this.r.right - width;
            this.r.top = pen.top + sizeDot + sizeDot/3;
            this.r.bottom = this.r.top + height;
            this.speed[0] = speed[0];
            //if (orientation == 1) {
                this.speed[1] = speed[1];
            //}
        }
        if (rLeft) {
            this.r.left = pen.right;
            this.r.right = this.r.left + width;
            this.r.top = pen.top + sizeDot + sizeDot/3;
            this.r.bottom = this.r.top + height;
            this.speed[0] = speed[0]*-1;
            //if (orientation == 1) {
                this.speed[1] = speed[1];
            //}
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

        if (orientation == 1) {

            if (firstTime) {
                this.r.top = this.r.top - sizeDot/2 - sizeDot/4;
                this.r.bottom = this.r.bottom - sizeDot/2 - sizeDot/4;
                firstTime = false;
            }

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

    public synchronized boolean checkColissionBullet() {// BALAS heightScreenOCAN CON SOLIDOSSSSSSSSSS
        for (int xM = 0; xM < matrixList.size(); xM++) {
            for (int yM = 0; yM < matrixList.get(xM).size(); yM++) {
                if (matrixList.get(xM).get(yM).getSolid() == 1) {
                    if (Rect.intersects(this.r,matrixList.get(xM).get(yM).getRect())) {
                        bulletList.remove(this);
                        return true;
                    }
                }
            }
        }//check OUT OF SCREEN
        if (this.r.left < -sizeDot*2 || this.r.right > widthScreen + sizeDot*2 ||
                this.r.top < - sizeDot*2 || this.r.bottom > heightScreen + sizeDot*2) {
            bulletList.remove(this);
            return true;
        }
        return false;
    }
    public synchronized boolean checkColissionBulletEnemy() {
        if (penguin) {
            if (enemyList != null) {
                for (int x = 0; x < enemyList.size(); x++) {
                    if (Rect.intersects(this.r,enemyList.get(x).getHitBox())) {
                        Rect rExplosion = new Rect();
                        rExplosion.top = this.r.top -25;
                        rExplosion.bottom = this.r.bottom + 25;
                        rExplosion.left = this.r.left - 25;
                        rExplosion.right = this.r.right + 25;
                        matrixX.generateExplosion(rExplosion);//enemyList.get(x).getRect()
                        enemyList.remove(enemyList.get(x));// SUSTITUIR POR MORIRRRRRRRRRRRRRRRRRRRRRRRR
                        bulletList.remove(this);
                        return true;
                    }
                }
            }
        }/*
        if (enemy) {
            if (Rect.intersects(this.r,penguinHitBox)) {
                System.out.println("tocado"); //("lo mismo que si es tocado"); //matrixX checkEnemyColission():
            }
        }*/
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
