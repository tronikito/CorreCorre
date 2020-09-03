package correcorre.penguin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;

public class Penguin extends Physics {

    private Context c;

    private Drawable lPos0;
    private Drawable lPos0weapon;
    private Drawable rPos0;
    private Drawable rPos0weapon;
    private Drawable lPos1;
    private Drawable lPos1weapon;
    private Drawable lPos2;
    private Drawable lPos2weapon;
    private Drawable lPos3;
    //private Drawable lPos3weapon; // Desestimada Derrape Weapon
    private Drawable lPos4;
    private Drawable lPos4weapon;
    private Drawable lPos5;
    private Drawable lPos5weapon;
    private Drawable rPos1;
    private Drawable rPos1weapon;
    private Drawable rPos2;
    private Drawable rPos2weapon;
    private Drawable rPos3;
    //private Drawable rPos3weapon; // Desestimada Derrape Weapon
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

        lPos0 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin0,null);
        lPos0weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin0weapon,null);
        rPos0 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin0,null);
        rPos0weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin0weapon,null);
        lPos1 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin1,null);
        lPos1weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin1weapon,null);
        lPos2 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin2,null);
        lPos2weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin2weapon,null);
        lPos3 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin3,null);
        //lPos3weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin3weapon,null);
        lPos4 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin4,null);
        lPos4weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin4weapon,null);
        lPos5 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin5,null);
        lPos5weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin5weapon,null);
        rPos1 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin1,null);
        rPos1weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin1weapon,null);
        rPos2 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin2,null);
        rPos2weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin2weapon,null);
        rPos3 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin3,null);
        //rPos3weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin3weapon,null);
        rPos4 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin4,null);
        rPos4weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin4weapon,null);
        rPos5 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin5,null);
        rPos5weapon = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin5weapon,null);
        this.d = lPos0;

    }

    public Drawable getDrawable() {
        this.d.setBounds(this.r);
        return this.d;
    }

    public void movePenguinSprite(int[] speed) {//recibe real fps

        if (!rLeft && !rRight && !jumping && !gravityAceleration) {

            if (this.weapon != null) {
                this.d = lPos0weapon;
                this.weapon.setSprite("right");
            }
            else {
                this.d = lPos0;
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

            if (weapon != null) {
                this.weapon.setSprite("right");
                this.d = lPos0weapon;
            } else {
                this.d = lPos0;
            }
        }
        if (rRight && paddingSpeedAplication) {

            if (weapon != null) {
                this.weapon.setSprite("left");
                this.d = rPos0weapon;
            } else {
                this.d = rPos0;
            }
        }


        if (rLeft && !jumping && !gravityAceleration) {
            if (this.speed[0] >= -150) {
                speedCount += Math.abs(speed[0]);
                if (speedCount >= 30) {
                    if (this.weapon == null) {
                        if (actualPos == 0) this.d = lPos0;
                        if (actualPos == 1) this.d = lPos1;
                        if (actualPos == 2) this.d = lPos2;
                    } else {
                        if (actualPos == 0) this.d = lPos0weapon;
                        if (actualPos == 1) this.d = lPos1weapon;
                        if (actualPos == 2) this.d = lPos2weapon;
                    }
                    actualPos++;
                    if (actualPos > 2) actualPos = 0;
                    speedCount = 0;
                } else if(this.d != lPos3 && this.d != lPos2 && this.d != lPos0 && this.d != lPos1 && this.weapon == null) {
                    this.d = lPos0;
                } else if (this.d != lPos0weapon && this.d != lPos1weapon && this.d != lPos2weapon && this.weapon != null) {
                    this.d = lPos0weapon;
                }
            }
            if (this.speed[0] < -150) {//DERRAPAR SPRITE
                if (this.weapon != null) {
                    speedCount += Math.abs(speed[0]);
                    if (speedCount >= 30) {
                        if (actualPos == 0) this.d = lPos0weapon;
                        if (actualPos == 1) this.d = lPos1weapon;
                        if (actualPos == 2) this.d = lPos2weapon;
                        actualPos++;
                        if (actualPos > 2) actualPos = 0;
                        speedCount = 0;
                    }
                } else {
                    this.d = lPos3;
                }
            }
        }

        if (rRight && !jumping && !gravityAceleration) {
            if (this.speed[0] <= 150) {
                speedCount += Math.abs(speed[0]);
                if (speedCount >= 30) {
                    if (this.weapon == null) {
                        if (actualPos == 0) this.d = rPos0;
                        if (actualPos == 1) this.d = rPos1;
                        if (actualPos == 2) this.d = rPos2;
                    } else {
                        if (actualPos == 0) this.d = rPos0weapon;
                        if (actualPos == 1) this.d = rPos1weapon;
                        if (actualPos == 2) this.d = rPos2weapon;
                    }
                    actualPos++;
                    if (actualPos > 2) actualPos = 0;
                    speedCount = 0;
                } else if(this.d != rPos3 && this.d != rPos2 && this.d != rPos0 && this.d != rPos1 && this.weapon == null) {
                    this.d = rPos0;
                } else if (this.d != rPos0weapon && this.d != rPos1weapon && this.d != rPos2weapon && this.weapon != null) {
                 this.d = rPos0weapon;
                }
            }
            if (this.speed[0] > 150) {//DERRAPAR SPRITE

                if (this.weapon != null) {
                    speedCount += Math.abs(speed[0]);
                    if (speedCount >= 30) {
                        if (actualPos == 0) this.d = rPos0weapon;
                        if (actualPos == 1) this.d = rPos1weapon;
                        if (actualPos == 2) this.d = rPos2weapon;
                        actualPos++;
                        if (actualPos > 2) actualPos = 0;
                        speedCount = 0;
                    } //else if(this.d != rPos1weapon && this.d != rPos2weapon) {
                       // this.d = rPos0weapon;
                    //}
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
