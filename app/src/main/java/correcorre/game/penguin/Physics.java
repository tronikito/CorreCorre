package correcorre.game.penguin;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.R;
import correcorre.game.weapons.FerretBullet;
import correcorre.game.weapons.ChickenBullet;
import correcorre.game.weapons.UnicornBullet;
import correcorre.game.weapons.Weapon;

import static correcorre.ResourcesClass.*;

public abstract class Physics {

    protected Rect r = new Rect();
    protected volatile Rect pLeft;
    protected volatile Rect pRight;
    protected volatile Rect pTop;
    protected volatile Rect pBottom;
    protected volatile Rect hitBox;
    protected Drawable d;
    protected Boolean pressJump = false;
    protected int counterPressed = 0;
    protected volatile Weapon weapon;
    protected Boolean rLeft = false;
    protected Boolean rRight = false;
    protected Boolean jumping = false;
    protected Boolean gravityAceleration = true;
    protected Boolean gravityXAceleration = true;
    public int paddingSpeed;
    protected int paddingSpeedXRight;
    protected int paddingSpeedXLeft;
    protected Boolean paddingSpeedApplication = false;
    protected Boolean paddingSpeedXRightApplication = false;
    protected Boolean paddingSpeedXLeftApplication = false;
    protected Boolean paddingSpeedApplicationTop = false;
    protected boolean foundX = false;
    private String weaponType;
    private boolean shooting;
    private int shootingCount = 0;
    private int shootingRandomCount = 10;
    private int orientation = 0;

    protected Boolean immunity = false;

    public Physics() {

        int x = widthScreen / 2 - ((sizeDot * 2) / 2);
        int y = (int) Math.round(heightScreen / 1.2 - ((sizeDot*2.5) + sizeDot * 1.5));
        int w = (sizeDot * 2);
        int h = (int) (sizeDot*2.5);

        //halfSizeDot = s/2;
        //there working with HalfSizeDot (not sure why)
        int halfSizeDot = sizeDot/2;

        this.r.left = x;
        this.r.right = this.r.left + w;
        this.r.top = y;
        this.r.bottom = this.r.top + h;

        this.hitBox = new Rect();
        this.hitBox.left = x + halfSizeDot;
        this.hitBox.right = this.hitBox.left + halfSizeDot*2;
        this.hitBox.top = y + halfSizeDot/2;
        this.hitBox.bottom = this.hitBox.top + halfSizeDot*4;

        this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.ic_007, null);

        pLeft = new Rect();
        pLeft.left = x-(halfSizeDot/2) + halfSizeDot;;
        pLeft.right = pLeft.left + 1;
        pLeft.top = y + halfSizeDot;
        pLeft.bottom = pLeft.top + 3*halfSizeDot;

        pRight = new Rect();
        pRight.left = x+(halfSizeDot/2) + 3*halfSizeDot;
        pRight.right = pRight.left + 1;
        pRight.top =  y + halfSizeDot;
        pRight.bottom = pRight.top + 3*halfSizeDot;

        pTop = new Rect();
        pTop.left = x + halfSizeDot;
        pTop.right = pTop.left + 2*halfSizeDot;
        pTop.top =  y;
        pTop.bottom = pTop.top + 1;

        pBottom = new Rect();
        pBottom.left = x + halfSizeDot;
        pBottom.right = pBottom.left + 2*halfSizeDot;
        pBottom.top =  y + 4*halfSizeDot + halfSizeDot;
        pBottom.bottom = pBottom.top + 1;

    }
    public void setBulletSpeed(int percent) {
        if (this.weapon != null) this.weapon.setBulletSpeed(percent);
    }
    /*public Rect getHitBox() {
        return this.hitBox;
    }*/

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
    /*public boolean paddingSpeedAplication() {
        return paddingSpeedAplication;
    }*/

    public synchronized void movePenguinPos(int[] speed) {

        if (mainGame.penguinX) {
            moveX(speed[0]);
            if (weapon != null) {
                weapon.moveX(speed[0]);
            }
        }
        if (mainGame.penguinY) {
            moveY(speed[1]);
            if (weapon != null) {
                weapon.moveY(speed[1]);
            }
        }
    }

    public void setWeapon(Weapon w) {
        this.weapon = w;
    }

    public Weapon getWeapon() {
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
    /*public void setOrientation(int orientation) {
        this.orientation = orientation;
        if (weapon != null) this.weapon.setOrientation(orientation);
    }
    public int getOrientation() {
        return this.orientation;
    }*/
    public void jump() {
        if (!jumping && !paddingSpeedApplication) {
            penguinSpeed[1] = -500;
            jumping = true;
        }
    }

    public synchronized Boolean getImmunity() { return this.immunity; }

    public void setDirection(String direction) {

        if (direction.equals("left")) {
            rLeft = false;
            rRight = true;
            if (!getImmunity()) {
                if (this.weapon != null) this.weapon.setDirection("right");
            }

        }
        if (direction.equals("right")) {
            rRight = false;
            rLeft = true;
            if (!getImmunity()) {
                if (this.weapon != null) this.weapon.setDirection("left");
            }
        }
    }

    public synchronized void calcAcceleration() {//aceleration penguin


        if (paddingSpeedApplication && !gravityAceleration) {
            int[] fixMatrix = new int[]{0, paddingSpeed};
            movePenguinPos(fixMatrix);
            matrixX.moveMatrix(fixMatrix);
            paddingSpeed = 0;
        }


        if (paddingSpeedXLeftApplication) {
            int[] fixMatrix = new int[]{paddingSpeedXLeft*-1, 0};
            movePenguinPos(fixMatrix);
            matrixX.moveMatrix(fixMatrix);
            paddingSpeedXLeft = 0;
            paddingSpeed = 0;
        }

        if (paddingSpeedXRightApplication) {
            int[] fixMatrix = new int[]{paddingSpeedXRight, 0};
            movePenguinPos(fixMatrix);
            matrixX.moveMatrix(fixMatrix);
            paddingSpeedXRight = 0;
            paddingSpeed = 0;
        }

        if (paddingSpeedApplicationTop) {
            int[] fixMatrix = new int[]{0, paddingSpeed * -1};
            movePenguinPos(fixMatrix);
            matrixX.moveMatrix(fixMatrix);
            paddingSpeed = 0;
        }

        if (gravityAceleration && !paddingSpeedApplication) {
            if (!this.pressJump && penguinSpeed[1] < 1000) {
                penguinSpeed[1] += 35;
            } else {
                counterPressed += 35;
                if (counterPressed >= 750 && penguinSpeed[1] < 1000) {
                    penguinSpeed[1] += 35;
                }
            }
        }

        if (rLeft && penguinSpeed[0] < 350 || rRight && penguinSpeed[0] > -350) {
            if (rLeft) {
                penguinSpeed[0] += 10;
            }
            if (rRight) {
                penguinSpeed[0] -= 10;
            }
        }
    }

    public synchronized void checkWeaponCollision() {//use PenguinSprite Rect

        if (weaponList != null) {
            for (int l = 0; l < weaponList.size(); l++) {
                if (!weaponList.get(l).getEnemy() && !weaponList.get(l).getPenguin()) {
                    if (Rect.intersects(weaponList.get(l).getRect(), this.r)) {
                        if (!weaponList.get(l).getType().equals(this.weaponType)) {
                            this.setWeapon(weaponList.get(l));

                            this.weapon.setPenguin(this.r, this.rLeft, this.rRight, this.orientation);
                            System.out.println(this.orientation);
                            if (rLeft) {
                                this.weapon.setSprite("right");
                            }
                            if (rRight) {
                                this.weapon.setSprite("left");
                            }
                            this.weaponType = weaponList.get(l).getWeaponType();
                            weaponList.remove(this.weapon);//delete from weapon list in matrixX
                            //for (int x = 0; x < weaponList.size(); x++) {
                                //if (weaponList.get(x).equals(this.weapon)) weaponList.remove(weaponList.get(x));
                            //}
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

                if (this.weapon.getWeaponType().equals("chicken")) {//heightScreenicken
                    ChickenBullet bullet = new ChickenBullet(this.r, this.rLeft, this.rRight, this.weapon.getOrientation(),this.weapon.getBulletSpeed());
                    bullet.setPenguin();
                    matrixX.generateBullet(bullet);
                    shootingRandomCount = (int) Math.floor(Math.random() * (12 - 7) + 15);//fireRate
                }
                if (this.weapon.getWeaponType().equals("ferret")) {//ferret
                    FerretBullet bullet = new FerretBullet(this.r, this.rLeft, this.rRight, this.weapon.getOrientation(),this.weapon.getBulletSpeed());
                    bullet.setPenguin();
                    matrixX.generateBullet(bullet);
                    shootingRandomCount = (int) Math.floor(Math.random() * (12 - 7) + 50);//fireRate
                }
                if (this.weapon.getWeaponType().equals("unicorn")) {//unicorn
                    UnicornBullet bullet = new UnicornBullet(this.r, this.rLeft, this.rRight, this.weapon.getOrientation(),this.weapon.getBulletSpeed());
                    bullet.setPenguin();
                    matrixX.generateBullet(bullet);
                    shootingRandomCount = 1;//fireRate
                }
                shootingCount = 0;

            }
        }
    }
    public synchronized void shoot(boolean shoot) {
        this.shooting = shoot;
    }

    public synchronized void checkPhysicCollision() {

        boolean foundX = false;
        boolean found = false;
        for (int xM = 0; xM < matrixList.size(); xM++) {
            for (int yM = 0; yM < matrixList.get(xM).size(); yM++) {

                if (matrixList.get(xM).get(yM).getSolid() == 1) {
                    if (Rect.intersects(pLeft, matrixList.get(xM).get(yM).getRect())) {

                        foundX = true;

                        if (pLeft.left - matrixList.get(xM).get(yM).getRect().right < -1) {
                            paddingSpeedXLeft = pLeft.left + 1 - matrixList.get(xM).get(yM).getRect().right;
                            penguinSpeed[0] = 0;
                            paddingSpeedXLeftApplication = true;

                        } else {
                            paddingSpeedXLeftApplication = false;
                        }
                    }
                    if (Rect.intersects(pRight, matrixList.get(xM).get(yM).getRect())) {

                        foundX = true;

                        if (matrixList.get(xM).get(yM).getRect().left - pRight.right < -1) {
                            paddingSpeedXRight = matrixList.get(xM).get(yM).getRect().left - pRight.right + 1;
                            penguinSpeed[0] = 0;
                            paddingSpeedXRightApplication = true;

                        } else {
                            paddingSpeedXRightApplication = false;
                        }
                    }
                    if (Rect.intersects(pTop, matrixList.get(xM).get(yM).getRect())) {

                        found = true;

                        if (pTop.top + 1 - matrixList.get(xM).get(yM).getRect().bottom < -1) {
                            paddingSpeed = pTop.top + 1 - matrixList.get(xM).get(yM).getRect().bottom;
                            penguinSpeed[1] = 0;
                            paddingSpeedApplicationTop = true;
                        }
                    }

                    if (Rect.intersects(pBottom, matrixList.get(xM).get(yM).getRect())) {

                        found = true;

                        gravityAceleration = false;

                        if (matrixList.get(xM).get(yM).getRect().top - pBottom.bottom < -1) {
                            paddingSpeed = matrixList.get(xM).get(yM).getRect().top - pBottom.bottom + 1;
                            penguinSpeed[1] = 0;
                            jumping = false;
                            counterPressed = 0;
                            paddingSpeedApplication = true;

                        } else {
                            paddingSpeedApplication = false;
                        }
                    }
                }
            }
        }

        if (!found) {
            gravityAceleration = true;
            paddingSpeedApplication = false;
            paddingSpeedApplicationTop = false;
        }
        if (!foundX) {
            paddingSpeedXLeftApplication = false;
            paddingSpeedXRightApplication = false;
        }
    }
    public synchronized void checkCollision() {

        checkWeaponCollision();
        checkPhysicCollision();

    }

    /*public void printPenguinGrid(Canvas c) {//debugger

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

    }*/
}
