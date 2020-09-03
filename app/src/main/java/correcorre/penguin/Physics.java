package correcorre.penguin;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;
import correcorre.weapons.Bullet;
import correcorre.weapons.FerretBullet;
import correcorre.weapons.MetralletaBullet;
import correcorre.weapons.Weapon;

public abstract class Physics {

    protected static Main main;
    protected static MatrixX matrixX;
    protected Rect r = new Rect();
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected int size;
    protected volatile Rect pLeft;
    protected volatile Rect pRight;
    protected volatile Rect pTop;
    protected volatile Rect pBottom;
    protected volatile Rect hitBox;
    protected Drawable d;
    protected int[] speed = new int[] {0,0};
    protected Boolean pressJump = false;
    protected int counterPressed = 0;
    protected Weapon weapon;
    protected Boolean rLeft = false;
    protected Boolean rRight = false;
    protected Boolean jumping = false;
    protected Boolean gravityAceleration = true;
    protected Boolean gravityXAceleration = true;
    public int paddingSpeed;
    protected int paddingSpeedXRight;
    protected int paddingSpeedXLeft;
    protected Boolean paddingSpeedAplication = false;
    protected Boolean paddingSpeedXRightAplication = false;
    protected Boolean paddingSpeedXLeftAplication = false;
    protected Boolean paddingSpeedAplicationTop = false;
    protected boolean foundX = false;
    private String typeWeapon;
    private boolean shooting;
    private int shootingCount = 0;
    private int shootingRandomCount = 10;

    public Physics(Main m, MatrixX ma, int xPos, int yPos, int w, int h, int s) {

        main = m;
        matrixX = ma;

        this.size = s/2;

        this.r.left = xPos;
        this.r.right = this.r.left + w;
        this.r.top = yPos;
        this.r.bottom = this.r.top + h;

        this.hitBox = new Rect();
        this.hitBox.left = xPos + this.size;
        this.hitBox.right = this.hitBox.left + this.size*2;
        this.hitBox.top = yPos + this.size/2;
        this.hitBox.bottom = this.hitBox.top + this.size*4;

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = w;
        this.height = h;

        this.d = VectorDrawableCompat.create(main.getContext().getResources(), R.drawable.ic_007, null);

        pLeft = new Rect();
        pLeft.left = xPos-(this.size/2) + this.size;;
        pLeft.right = pLeft.left + 1;
        pLeft.top = yPos + this.size;
        pLeft.bottom = pLeft.top + 3*this.size;

        pRight = new Rect();
        pRight.left = xPos+(this.size/2) + 3*this.size;
        pRight.right = pRight.left + 1;
        pRight.top =  yPos + this.size;
        pRight.bottom = pRight.top + 3*this.size;

        pTop = new Rect();
        pTop.left = xPos + this.size;
        pTop.right = pTop.left + 2*this.size;
        pTop.top =  yPos;
        pTop.bottom = pTop.top + 1;

        pBottom = new Rect();
        pBottom.left = xPos + this.size;
        pBottom.right = pBottom.left + 2*this.size;
        pBottom.top =  yPos + 4*this.size + this.size;
        pBottom.bottom = pBottom.top + 1;

    }

    public Rect getHitBox() {
        return this.hitBox;
    }

    public boolean getrLeft() {
        return rLeft;
    }
    public boolean getrRight() {
        return rRight;
    }

    public Rect getRect() {
        return this.r;
    }
    public int getPosX() {
        return this.r.left;
    }//probando xPos

    public int getPosY() {
        return this.r.top;
    }

    public boolean getJumping() {
        return this.jumping;
    }
    public boolean getGravity() {
        return this.gravityAceleration;
    }
    public boolean paddingSpeedAplication() {
        return paddingSpeedAplication;
    }

    public synchronized void movePenguinPos(int[] speed) {

        if (main.penguinX) {
            moveX(speed[0]);
            if (weapon != null) {
                weapon.moveX(speed[0]);
            }
        }
        if (main.penguinY) {
            moveY(speed[1]);
            if (weapon != null) {
                weapon.moveY(speed[1]);
            }
        }
    }

    public void setWeapon(Weapon w) {
        this.weapon = w;
    }

    public synchronized Weapon getWeapon() {
        return this.weapon;
    }

    public synchronized void moveX(int speed) {

        this.r.left += speed;
        this.r.right += speed;

        pTop.left += speed;
        pTop.right += speed;
        pLeft.left += speed;
        pLeft.right += speed;
        pRight.left += speed;
        pRight.right += speed;
        pBottom.left += speed;
        pBottom.right += speed;

        hitBox.left += speed;
        hitBox.right += speed;

    }
    public synchronized void moveY(int speed) {

        this.r.top += speed;
        this.r.bottom += speed;

        pTop.top += speed;
        pTop.bottom += speed;
        pLeft.top += speed;
        pLeft.bottom += speed;
        pRight.top += speed;
        pRight.bottom += speed;
        pBottom.top += speed;
        pBottom.bottom += speed;

        hitBox.top += speed;
        hitBox.bottom += speed;

    }

    public void setPressJump(boolean j) {
        this.pressJump = j;
    }

    public void jump() {
        if (!jumping && !paddingSpeedAplication) {
            this.speed[1] = -500;
            jumping = true;
        }
    }

    public void setDirection() {
        boolean done = false;
        if (rLeft) {
            rLeft = false;
            rRight = true;
            done = true;
            if (this.weapon != null) {
                this.weapon.getRect().left = this.weapon.getRect().left - (matrixX.getSize()/3)*2;
                this.weapon.getRect().right = this.weapon.getRect().right - (matrixX.getSize()/3)*2;
                this.weapon.setSprite("left");
            }
        }

        if (rRight && !done) {
            rRight = false;
            rLeft = true;
            if (this.weapon != null) {
                this.weapon.getRect().left = this.weapon.getRect().left + (matrixX.getSize() / 3) * 2;
                this.weapon.getRect().right = this.weapon.getRect().right + (matrixX.getSize() / 3) * 2;
                this.weapon.setSprite("right");
            }
        }
        if (!rLeft && !rRight) {
            rLeft = true;
        }
    }

    public int[] getSpeed() {
        return this.speed;
    }

    public synchronized void calcAceleration() {//aceleration penguin


        if (paddingSpeedAplication && !gravityAceleration) {
            int[] fixMatrix = new int[]{0, paddingSpeed};
            movePenguinPos(fixMatrix);
            matrixX.moveMatrix(fixMatrix);
            paddingSpeed = 0;
        }


        if (paddingSpeedXLeftAplication) {
            int[] fixMatrix = new int[]{paddingSpeedXLeft*-1, 0};
            movePenguinPos(fixMatrix);
            matrixX.moveMatrix(fixMatrix);
            paddingSpeedXLeft = 0;
            paddingSpeed = 0;
        }

        if (paddingSpeedXRightAplication) {
            int[] fixMatrix = new int[]{paddingSpeedXRight, 0};
            movePenguinPos(fixMatrix);
            matrixX.moveMatrix(fixMatrix);
            paddingSpeedXRight = 0;
            paddingSpeed = 0;
        }

        if (paddingSpeedAplicationTop) {
            int[] fixMatrix = new int[]{0, paddingSpeed * -1};
            movePenguinPos(fixMatrix);
            matrixX.moveMatrix(fixMatrix);
            paddingSpeed = 0;
        }

        if (gravityAceleration && !paddingSpeedAplication) {
            if (!this.pressJump && speed[1] < 1000) {
                speed[1] += 35;
            } else {
                counterPressed += 35;
                if (counterPressed >= 750 && speed[1] < 1000) {
                    speed[1] += 35;
                }
            }
        }

        if (rLeft && speed[0] < 350 || rRight && speed[0] > -350) {
            if (rLeft) {
                speed[0] += 10;
            }
            if (rRight) {
                speed[0] -= 10;
            }
        }
    }

    public synchronized void checkWeaponColission() {//use PenguinSprite Rect

        if (matrixX.weaponList != null) {
            for (int l = 0; l < matrixX.weaponList.size(); l++) {
                if (!matrixX.weaponList.get(l).getEnemy() && !matrixX.weaponList.get(l).getPenguin()) {
                    if (Rect.intersects(matrixX.weaponList.get(l).getRect(), this.r)) {
                        if (!matrixX.weaponList.get(l).getType().equals(this.typeWeapon)) {
                            this.setWeapon(matrixX.weaponList.get(l));
                            this.weapon.setPenguin(this.r, this.rLeft, this.rRight);
                            if (rLeft) {
                                this.weapon.setSprite("right");
                            }
                            if (rRight) {
                                this.weapon.setSprite("left");
                            }
                            this.typeWeapon = matrixX.weaponList.get(l).getType();
                        }
                    }
                }

            }
        }
    }

    public synchronized void shooting() {
        shootingCount++;

        if (shooting && shootingCount > shootingRandomCount) {
            if (this.weapon != null) {
                //System.out.println(this.weapon.getType());
                if (this.weapon.getWeaponType() == 1) {//chicken
                    MetralletaBullet bullet = new MetralletaBullet(main,matrixX,this.r, this.rLeft, this.rRight);
                    bullet.setPenguin();
                    matrixX.generateBullet(bullet);
                }
                if (this.weapon.getWeaponType() == 2) {//ferret
                    FerretBullet bullet = new FerretBullet(main,matrixX,this.r, this.rLeft, this.rRight);
                    bullet.setPenguin();
                    matrixX.generateBullet(bullet);
                }
                shootingCount = 0;
                shootingRandomCount = (int) Math.floor(Math.random() * (12 - 7) + 7);//fireRate
            }
        }
    }
    public synchronized void shoot(boolean shoot) {
        this.shooting = shoot;
    }

    public synchronized void checkPhysicColission() {

        boolean foundX = false;
        boolean found = false;
        for (int xM = 0; xM < matrixX.matrix.size(); xM++) {
            for (int yM = 0; yM < matrixX.matrix.get(xM).size(); yM++) {

                if (matrixX.matrix.get(xM).get(yM).getSolid() == 1) {
                    if (Rect.intersects(pLeft, matrixX.matrix.get(xM).get(yM).getRect())) {

                        foundX = true;

                        if (pLeft.left - matrixX.matrix.get(xM).get(yM).getRect().right < -1) {
                            paddingSpeedXLeft = pLeft.left + 1 - matrixX.matrix.get(xM).get(yM).getRect().right;
                            speed[0] = 0;
                            paddingSpeedXLeftAplication = true;

                        } else {
                            paddingSpeedXLeftAplication = false;
                        }
                    }
                    if (Rect.intersects(pRight, matrixX.matrix.get(xM).get(yM).getRect())) {

                        foundX = true;

                        if (matrixX.matrix.get(xM).get(yM).getRect().left - pRight.right < -1) {
                            paddingSpeedXRight = matrixX.matrix.get(xM).get(yM).getRect().left - pRight.right + 1;
                            speed[0] = 0;
                            paddingSpeedXRightAplication = true;

                        } else {
                            paddingSpeedXRightAplication = false;
                        }
                    }
                    if (Rect.intersects(pTop, matrixX.matrix.get(xM).get(yM).getRect())) {

                        found = true;

                        if (pTop.top + 1 - matrixX.matrix.get(xM).get(yM).getRect().bottom < -1) {
                            paddingSpeed = pTop.top + 1 - matrixX.matrix.get(xM).get(yM).getRect().bottom;
                            speed[1] = 0;
                            paddingSpeedAplicationTop = true;
                        }
                    }

                    if (Rect.intersects(pBottom, matrixX.matrix.get(xM).get(yM).getRect())) {

                        found = true;

                        gravityAceleration = false;

                        if (matrixX.matrix.get(xM).get(yM).getRect().top - pBottom.bottom < -1) {
                            paddingSpeed = matrixX.matrix.get(xM).get(yM).getRect().top - pBottom.bottom + 1;
                            speed[1] = 0;
                            jumping = false;
                            counterPressed = 0;
                            paddingSpeedAplication = true;

                        } else {
                            paddingSpeedAplication = false;
                        }
                    }
                }
            }
        }

        if (!found) {
            gravityAceleration = true;
            paddingSpeedAplication = false;
            paddingSpeedAplicationTop = false;
        }
        if (!foundX) {
            paddingSpeedXLeftAplication = false;
            paddingSpeedXRightAplication = false;
        }
    }
    public synchronized void checkColission() {

        //##############################################################################
        checkWeaponColission();
        checkPhysicColission();
        //##############################################################################

    }

    //############################################################### GRASS Move
    /*
      if (Rect.intersects(pBottom.get(x),matrixX.matrix.get(xM).get(yM).getRect())) {
        if (matrixX.matrix.get(xM).get(yM).getType().equals("grass")) {

            if (x > 0 && rLeft || x < 4 && rRight) {//animation start
                matrixX.matrix.get(xM).get(yM).setSprite(0);
            }
            if (x == 0 && rLeft || x == 4 && rRight) {//animation stop
                matrixX.matrix.get(xM).get(yM).setSprite(1);
            }
        }
    }
    */
    //###############################################################

    public void printPenguinGrid(Canvas c) {//debugger

            this.d.setBounds(pLeft);
            this.d.draw(c);

            this.d.setBounds(pRight);
            this.d.draw(c);

            this.d.setBounds(pTop);
            this.d.draw(c);

            this.d.setBounds(pBottom);
            this.d.draw(c);

            this.d.setBounds(hitBox);
            this.d.draw(c);

    }
}
