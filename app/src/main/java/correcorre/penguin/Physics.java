package correcorre.penguin;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.ArrayList;

import correcorre.Main;
import correcorre.R;
import correcorre.graficos.Controls;
import correcorre.graficos.MatrixX;
import correcorre.scenario.Block;

public abstract class Physics {

    protected static Main main;
    protected static MatrixX matrixX;
    final protected int gravity = 10;
    protected Rect r = new Rect();
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected int size;
    protected volatile ArrayList<Rect> pLeft;//physics Left
    protected volatile ArrayList<Rect> pRight;//physics Left
    protected volatile ArrayList<Rect> pTop;//physics Left
    protected volatile ArrayList<Rect> pBottom;//physics Left
    protected Drawable d;
    protected int[] speed = new int[] {0,0};
    Rect physicBlock;
    protected Boolean pressJump = false;
    protected int counterPressed = 0;

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

    public Physics(Main m, MatrixX ma, int xPos, int yPos, int w, int h, int s) {

        main = m;
        matrixX = ma;

        this.r.left = xPos;
        this.r.right = this.r.left + w;
        this.r.top = yPos;
        this.r.bottom = this.r.top + h;

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = w;
        this.height = h;
        this.size = s/2;
        this.d = VectorDrawableCompat.create(main.getContext().getResources(), R.drawable.ic_007, null);


        pLeft = new ArrayList<Rect>();
        int posX = 1;//this position is 0 column away from left;
        for (int y = 1; y < 4; y++) {//this is left column of physics

            physicBlock = new Rect();
            physicBlock.left = xPos-(this.size/2) + posX*this.size;;
            physicBlock.right = physicBlock.left + 1;
            physicBlock.top = yPos + y*this.size;
            physicBlock.bottom = physicBlock.top + this.size;
            pLeft.add(physicBlock);

        }

        pRight = new ArrayList<Rect>();
        posX = 4;//this position is 3 column away from left;
        for (int y = 1; y < 4; y++) {//this is right column of physics

            physicBlock = new Rect();
            physicBlock.left = xPos-(this.size/2) + posX*this.size;
            physicBlock.right = physicBlock.left + 1;
            physicBlock.top = yPos + y*this.size;
            physicBlock.bottom = physicBlock.top + this.size;
            pRight.add(physicBlock);

        }

        pTop = new ArrayList<Rect>();
        int posY = 0;//this position is 0 rows away from top;
        for (int x = 0; x < 2; x++) {//this is top row of physics

            physicBlock = new Rect();
            physicBlock.left = xPos + this.size  + x*this.size;
            physicBlock.right = physicBlock.left + this.size;
            physicBlock.top = yPos+ posY*this.size;
            physicBlock.bottom = physicBlock.top + 1;
            pTop.add(physicBlock);

        }

        pBottom = new ArrayList<Rect>();
        posY = 5;//this position is 4 rows away from top;
        for (int x = 0; x < 2; x++) {//this is bottom row of physics

            physicBlock = new Rect();
            physicBlock.left = xPos+this.size  + x*this.size;
            physicBlock.right = physicBlock.left + this.size;
            physicBlock.top = yPos + posY*(this.size)-this.size;
            physicBlock.bottom = physicBlock.top + this.size + 1;
            pBottom.add(physicBlock);

        }
    }
    public boolean getrLeft() {
        return rLeft;
    }
    public boolean getrRight() {
        return rRight;
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
        }
        if (main.penguinY) {
            moveY(speed[1]);
        }
    }

    public synchronized void moveX(int speed) {
        //this.xPos += speed;

        this.r.left += speed;
        this.r.right += speed;

        for (int x = 0; x < pBottom.size(); x++) {
            pBottom.get(x).left += speed;
            pBottom.get(x).right += speed;
        }
        for (int x = 0; x < pLeft.size(); x++) {
            pLeft.get(x).left += speed;
            pLeft.get(x).right += speed;
        }
        for (int x = 0; x < pRight.size(); x++) {
            pRight.get(x).left += speed;
            pRight.get(x).right += speed;
        }

        for (int x = 0; x < pTop.size(); x++) {
            pTop.get(x).left += speed;
            pTop.get(x).right += speed;
        }

    }
    public synchronized void moveY(int speed) {

        this.r.top += speed;
        this.r.bottom += speed;

        for (int x = 0; x < pBottom.size(); x++) {
            pBottom.get(x).top += speed;
            pBottom.get(x).bottom += speed;
        }
        for (int x = 0; x < pLeft.size(); x++) {
            pLeft.get(x).top += speed;
            pLeft.get(x).bottom += speed;
        }
        for (int x = 0; x < pRight.size(); x++) {
            pRight.get(x).top += speed;
            pRight.get(x).bottom += speed;
        }

        for (int x = 0; x < pTop.size(); x++) {
            pTop.get(x).top += speed;
            pTop.get(x).bottom += speed;
        }

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
        }
        if (rRight && !done) {
            rRight = false;
            rLeft = true;
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
            if (!this.pressJump) {
                speed[1] += 35;
            } else {
                counterPressed += 35;
                if (counterPressed >= 750) {
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

    public synchronized void checkLateralColission() {
        boolean foundX = false;
        for (int x = 1; x < pLeft.size(); x++) {//this is bottom row of physics
            for (int xM = 0; xM < matrixX.matrix.size(); xM++) {
                for (int yM = 0; yM < matrixX.matrix.get(xM).size(); yM++) {

                    if (matrixX.matrix.get(xM).get(yM).getSolid() == 1) {
                        if (Rect.intersects(pLeft.get(x),matrixX.matrix.get(xM).get(yM).getRect())) {

                            foundX = true;

                            if (pLeft.get(x).left - matrixX.matrix.get(xM).get(yM).getRect().right  < -1) {

                                paddingSpeedXLeft = pLeft.get(x).left+1 - matrixX.matrix.get(xM).get(yM).getRect().right;

                                speed[0] = 0;//delete gravity aceleration

                                paddingSpeedXLeftAplication = true;

                            } else {
                                paddingSpeedXLeftAplication = false;
                            }
                        }
                    }
                    if (matrixX.matrix.get(xM).get(yM).getSolid() == 1) {
                        if (Rect.intersects(pRight.get(x),matrixX.matrix.get(xM).get(yM).getRect())) {

                            foundX = true;

                            if (matrixX.matrix.get(xM).get(yM).getRect().left - pRight.get(x).right < -1){

                                paddingSpeedXRight = matrixX.matrix.get(xM).get(yM).getRect().left - pRight.get(x).right+1;

                                speed[0] = 0;

                                paddingSpeedXRightAplication = true;

                            } else {
                                paddingSpeedXRightAplication = false;
                            }
                        }
                    }
                }
            }
        }
        if (!foundX) {
            paddingSpeedXLeftAplication = false;
            paddingSpeedXRightAplication = false;
        }

    }
    public synchronized void checkBottomColission() {

        boolean found = false;

            for (int x = 0; x < pBottom.size(); x++) {//this is bottom row of physics
                for (int xM = 0; xM < matrixX.matrix.size(); xM++) {
                    for (int yM = 0; yM < matrixX.matrix.get(xM).size(); yM++) {

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


                        if (matrixX.matrix.get(xM).get(yM).getSolid() == 1) {
                            if (Rect.intersects(pTop.get(x),matrixX.matrix.get(xM).get(yM).getRect())) {
                                found = true;
                                if (pTop.get(x).top + 1 - matrixX.matrix.get(xM).get(yM).getRect().bottom < -1) {
                                    paddingSpeed = pTop.get(x).top + 1 - matrixX.matrix.get(xM).get(yM).getRect().bottom;
                                    speed[1] = 0;//delete gravity aceleration
                                    paddingSpeedAplicationTop = true;
                                }
                            }

                            if (Rect.intersects(pBottom.get(x),matrixX.matrix.get(xM).get(yM).getRect())) {

                                found = true;
                                gravityAceleration = false;

                                if (matrixX.matrix.get(xM).get(yM).getRect().top - pBottom.get(x).bottom < -1){


                                    paddingSpeed = matrixX.matrix.get(xM).get(yM).getRect().top - pBottom.get(x).bottom+1;
                                    speed[1] = 0;//delete gravity aceleration
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
            }
            if (!found) {
                gravityAceleration = true;
                paddingSpeedAplication = false;
                paddingSpeedAplicationTop = false;
            }
    }

    public void printPenguinGrid(Canvas c) {//debugger

        for (int y = 0; y < pLeft.size(); y++) {//this is left column of physics
            this.d.setBounds(pLeft.get(y));
            this.d.draw(c);
        }
        for (int y = 0; y < pRight.size(); y++) {//this is right column of physics
            this.d.setBounds(pRight.get(y));
            this.d.draw(c);
        }

        for (int x = 0; x < pTop.size(); x++) {//this is bottom row of physics
            this.d.setBounds(pTop.get(x));
            this.d.draw(c);
        }
        for (int x = 0; x < pBottom.size(); x++) {//this is bottom row of physics
            this.d.setBounds(pBottom.get(x));
            this.d.draw(c);
        }

    }
}
