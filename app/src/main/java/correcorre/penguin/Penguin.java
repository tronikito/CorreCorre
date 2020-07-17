package correcorre.penguin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;
import correcorre.weapons.Metralleta;

public class Penguin extends Physics {

    private Context c;

    private Drawable pos1;
    private Drawable pos2;
    private Drawable lPos1;
    private Drawable lPos2;
    private Drawable lPos3;
    private Drawable lPos3noweapon;
    private Drawable lPos4;
    private Drawable lPos4weapon;
    private Drawable lPos5;
    private Drawable lPos5weapon;
    private Drawable rPos1;
    private Drawable rPos2;
    private Drawable rPos3;
    private Drawable rPos3noweapon;
    private Drawable rPos4;
    private Drawable rPos4weapon;
    private Drawable rPos5;
    private Drawable rPos5weapon;
    private Drawable d;

    private int speedCount = 0;
    private int actualPos;

    public Penguin(Main m, MatrixX ma, int x, int y, int w, int h, int s) {//150,182

        super(m,ma,x,y,w,h,s);

        this.c = m.getContext();
        this.main = m;

        pos1 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_penguin1,null);
        pos2 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_penguin2,null);
        lPos1 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin1,null);
        lPos2 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin2,null);
        lPos3 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin3,null);
        lPos3noweapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin3noweapon,null);
        lPos4 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin4,null);
        lPos4weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin4weapon,null);
        lPos5 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin5,null);
        lPos5weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin5weapon,null);
        rPos1 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin1,null);
        rPos2 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin2,null);
        rPos3 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin3,null);
        rPos3noweapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin3noweapon,null);
        rPos4 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin4,null);
        rPos4weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin4weapon,null);
        rPos5 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin5,null);
        rPos5weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin5weapon,null);
        this.d = pos1;

    }

    public Drawable getDrawable() {
        this.d.setBounds(this.r);
        return this.d;
    }

    public void movePenguinSprite(int[] speed) {//recibe real fps

        if (!rLeft && !rRight && !jumping && !gravityAceleration) {
            this.d = pos1;
            if (this.weapon != null) {
                this.weapon.setSprite("right");
            }

        }
        if (rLeft && gravityAceleration) {
            if (this.speed[1] > 0) {
                if (this.weapon != null) {
                    this.d = lPos4weapon;
                    this.weapon.setSprite("right");
                } else {
                    this.d = lPos4;
                }
            }
            if (this.speed[1] < 0) {

                if (this.weapon != null) {
                    this.d = lPos5weapon;
                    this.weapon.setSprite("right");
                } else {
                    this.d = lPos5;
                }
            }
        }
        if (rRight && gravityAceleration) {
            if (this.speed[1] > 0) {

                if (this.weapon != null) {
                    this.d = rPos4weapon;
                    this.weapon.setSprite("left");
                } else {
                    this.d = rPos4;
                }
            }
            if (this.speed[1] < 0) {

                if (this.weapon != null) {
                    this.d = rPos5weapon;
                    this.weapon.setSprite("left");
                } else {
                    this.d = rPos5;
                }
            }
        }
        if (rLeft && paddingSpeedAplication) {
            this.d = pos1;
            if (weapon != null) {
                this.weapon.setSprite("right");
            }
        }
        if (rRight && paddingSpeedAplication) {
            this.d = pos2;
            if (weapon != null) {
                this.weapon.setSprite("left");
            }
        }


        if (rLeft && !jumping && !gravityAceleration) {
            if (this.speed[0] >= -150) {
                speedCount += Math.abs(speed[0]);
                if (speedCount >= 30) {
                    if (actualPos == 1) this.d = lPos1;
                    if (actualPos == 2) this.d = lPos2;
                    actualPos++;
                    if (actualPos > 2) actualPos = 1;
                    speedCount = 0;
                } else if((this.d != lPos3 || this.d != lPos3noweapon) && this.d != lPos2) {
                    this.d = lPos1;
                }
            }
            if (this.speed[0] < -150) {//DERRAPAR SPRITE

                if (this.weapon != null) {
                    this.d = lPos3noweapon;
                } else {
                    this.d = lPos3;
                }
            }
        }

        if (rRight && !jumping && !gravityAceleration) {
            if (this.speed[0] <= 150) {
                speedCount += Math.abs(speed[0]);
                if (speedCount >= 30) {
                    if (actualPos == 1) this.d = rPos1;
                    if (actualPos == 2) this.d = rPos2;
                    actualPos++;
                    if (actualPos > 2) actualPos = 1;
                    speedCount = 0;
                } else if((this.d != rPos3 || this.d != rPos3noweapon) && this.d != rPos2) {
                    this.d = rPos1;
                }
            }
            if (this.speed[0] > 150) {//DERRAPAR SPRITE

                if (this.weapon != null) {
                    this.d = rPos3noweapon;
                } else {
                    this.d = rPos3;
                }
            }
        }
    }

    public void printPenguin(Canvas c) {
        this.getDrawable().draw(c);
    }

}
