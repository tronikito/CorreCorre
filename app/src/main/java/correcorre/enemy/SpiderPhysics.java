package correcorre.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;

public class SpiderPhysics {

    protected static Main main;
    protected static MatrixX matrixX;
    protected Rect r;
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected int[] speed = new int[] {0,0};
    protected int[] actualSpeed = new int[] {0,0};
    protected volatile Rect pLeft;
    protected volatile Rect pRight;
    protected volatile Rect pTop;
    protected volatile Rect pBottom;
    protected volatile Rect hitBox;
    protected Drawable dra;
    protected Boolean pressJump = false;
    protected int counterPressed = 0;
    protected Rect physicBlock;
    protected Boolean rLeft = true;
    protected Boolean rRight = false;
    protected Boolean jumping = false;
    protected Boolean gravityAceleration = true;
    protected Boolean gravityXAceleration = true;
    protected int size;
    public int paddingSpeed;
    protected int paddingSpeedXRight;
    protected int paddingSpeedXLeft;
    protected Boolean paddingSpeedAplication = false;
    protected Boolean paddingSpeedXRightAplication = false;
    protected Boolean paddingSpeedXLeftAplication = false;
    protected Boolean paddingSpeedAplicationTop = false;
    protected int randomPress = 0;

    public SpiderPhysics (Main m, MatrixX ma, int xPos, int yPos, int w, int h) {
        main = m;
        matrixX = ma;

        this.r = new Rect();
        this.xPos = xPos;
        this.yPos = yPos;
        this.r.left = yPos;
        this.r.right = this.r.left + w;
        this.r.top = xPos;
        this.r.bottom = this.r.top + h;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = w;
        this.height = h;
        this.size = matrixX.getSize();
        this.dra = VectorDrawableCompat.create(main.getContext().getResources(), R.drawable.ic_007, null);

        this.hitBox = new Rect();
        this.hitBox.left = yPos + matrixX.getSize();
        this.hitBox.right = this.hitBox.left + 1;
        this.hitBox.top = xPos;
        this.hitBox.bottom = this.hitBox.top + matrixX.getSize();

        pLeft = new Rect();
        pLeft.left = this.r.left;;
        pLeft.right = pLeft.left + 1;
        pLeft.top = this.r.top + this.size/3;
        pLeft.bottom = pLeft.top  + this.size/3;

        pRight = new Rect();
        pRight.left = this.r.left + 2*this.size;
        pRight.right = pRight.left + 1;
        pRight.top = this.r.top + this.size/3;
        pRight.bottom = pRight.top + this.size/3;

        pTop = new Rect();
        pTop.left = this.r.left + this.size/2;
        pTop.right = pTop.left + this.size;
        pTop.top = this.r.top;
        pTop.bottom = pTop.top + 1;

        pBottom = new Rect();
        pBottom.left = this.r.left + this.size/2;
        pBottom.right = pBottom.left + this.size;
        pBottom.top = this.r.top + this.size;
        pBottom.bottom = pBottom.top + 1;

    }

    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }

    public void moveX(long speed) {
        speed = -speed;
        this.r.left += speed;
        this.r.right += speed;

            pBottom.left += speed;
            pBottom.right += speed;

            pLeft.left += speed;
            pLeft.right += speed;

            pRight.left += speed;
            pRight.right += speed;

            pTop.left += speed;
            pTop.right += speed;

            hitBox.left += speed;
            hitBox.right += speed;

    }

    public void moveY(long speed) {
        speed = -speed;
        this.r.top += speed;
        this.r.bottom += speed;

            pBottom.top += speed;
            pBottom.bottom += speed;

            pLeft.top += speed;
            pLeft.bottom += speed;

            pRight.top += speed;
            pRight.bottom += speed;

            pTop.top += speed;
            pTop.bottom += speed;

            hitBox.top += speed;
            hitBox.bottom += speed;

    }

    public Rect getHitBox() {
        return this.hitBox;
    }
    public Rect getRect() {
        return this.r;
    }
    public synchronized void moveEnemyPos(int[] speed) {
            moveX(speed[0]);
            moveY(speed[1]);
    }

    public synchronized void moveEnemyActualSpeed() {
        moveEnemyPos(this.actualSpeed);
    }
    public synchronized int[] getSpeed() {
        return this.speed;
    }
    public synchronized void setActualSpeed(int[] speed) {
        this.actualSpeed = speed;
    }
    public synchronized int[] getActualSpeed() {
        return this.actualSpeed;
    }
    public synchronized void calcAcelerationEnemy() {//aceleration penguin


        if (paddingSpeedAplication && !gravityAceleration) {
            int[] fixMatrix = new int[]{0, paddingSpeed * -1};
            moveEnemyPos(fixMatrix);
            paddingSpeed = 0;
        }


        if (paddingSpeedXLeftAplication) {
            int[] fixMatrix = new int[]{paddingSpeedXLeft, 0};
            moveEnemyPos(fixMatrix);
            paddingSpeedXLeft = 0;
            paddingSpeed = 0;
        }

        if (paddingSpeedXRightAplication) {
            int[] fixMatrix = new int[]{paddingSpeedXRight*-1, 0};
            moveEnemyPos(fixMatrix);
            paddingSpeedXRight = 0;
            paddingSpeed = 0;
        }

        if (paddingSpeedAplicationTop) {
            int[] fixMatrix = new int[]{0, paddingSpeed};
            moveEnemyPos(fixMatrix);
            paddingSpeed = 0;
        }

        if (gravityAceleration && !paddingSpeedAplication) {
            if (!this.pressJump && speed[1] < 1000) {
                speed[1] -= 35;
            } else {
                counterPressed += 750;
                if (counterPressed >= 750 && speed[1] < 1000) {
                    speed[1] -= 35;
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

    public void randomControls() {
        float random = Math.round(Math.random());
        if (random == 1) {
            this.randomPress += 35;
            if (this.randomPress >= 2500) {
                jump();
                setPressJump(true);
                this.randomPress = 0;
            }
        }
    }
    public void setDirection() {
        boolean found = false;
        if (rLeft) {
            rLeft = false;
            rRight = true;
            found = true;
        }
        if (rRight && !found) {
            rLeft = true;
            rRight = false;
        }
    }

    public void setPressJump(boolean j) {
        this.pressJump = j;
    }

    public void jump() {
        if (!jumping && !paddingSpeedAplication) {
            this.speed[1] = +850;
            jumping = true;
        }
    }

    public synchronized void checkColissionEnemy() {

        boolean foundX = false;
        boolean found = false;

            for (int xM = 0; xM < matrixX.matrix.size(); xM++) {
                for (int yM = 0; yM < matrixX.matrix.get(xM).size(); yM++) {

                    if (matrixX.matrix.get(xM).get(yM).getSolid() == 1) {
                        if (Rect.intersects(pLeft,matrixX.matrix.get(xM).get(yM).getRect())) {

                            foundX = true;

                            if (pLeft.left - matrixX.matrix.get(xM).get(yM).getRect().right  < -1) {

                                paddingSpeedXLeft = pLeft.left+1 - matrixX.matrix.get(xM).get(yM).getRect().right;
                                speed[0] = 0;
                                paddingSpeedXLeftAplication = true;
                                rLeft = false;
                                rRight = true;

                            } else {
                                paddingSpeedXLeftAplication = false;
                            }
                        }
                        if (Rect.intersects(pRight,matrixX.matrix.get(xM).get(yM).getRect())) {

                            foundX = true;

                            if (matrixX.matrix.get(xM).get(yM).getRect().left - pRight.right < -1){

                                paddingSpeedXRight = matrixX.matrix.get(xM).get(yM).getRect().left - pRight.right+1;
                                speed[0] = 0;
                                paddingSpeedXRightAplication = true;
                                rLeft = true;
                                rRight = false;

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

        if (this.r.left < 0) {
            rRight = true;
            rLeft = false;
            if (speed[0] > 0) {
                speed[0] = 0;
            }
        }
        if (this.r.right > matrixX.getWidth()) {
            rRight = false;
            rLeft = true;
            if (speed[0] < 0) {
                speed[0] = 0;
            }
        }
    }
    public synchronized void checkColissionPenguin(Rect pen) {
        if (Rect.intersects(this.hitBox,pen)) {
            //main.detener();
        }
    }

    public synchronized void printEnemyGrid(Canvas c) {//debugger

            this.dra.setBounds(pLeft);
            this.dra.draw(c);

            this.dra.setBounds(pRight);
            this.dra.draw(c);

            this.dra.setBounds(pTop);
            this.dra.draw(c);

            this.dra.setBounds(pBottom);
            this.dra.draw(c);

            this.dra.setBounds(hitBox);
            this.dra.draw(c);

    }
}