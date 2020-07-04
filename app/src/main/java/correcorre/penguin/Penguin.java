package correcorre.penguin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;

public class Penguin extends Physics {

    private Context c;

    private Drawable pos1;
    private Drawable pos2;
    private Drawable lPos1;
    private Drawable lPos2;
    private Drawable lPos3;
    private Drawable lPos4;
    private Drawable lPos5;
    private Drawable rPos1;
    private Drawable rPos2;
    private Drawable rPos3;
    private Drawable rPos4;
    private Drawable rPos5;
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
        lPos4 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin4,null);
        lPos5 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_lpenguin5,null);
        rPos1 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin1,null);
        rPos2 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin2,null);
        rPos3 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin3,null);
        rPos4 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin4,null);
        rPos5 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_rpenguin5,null);
        this.d = pos1;
    }

    public Drawable getDrawable() {
        this.d.setBounds(this.r);
        return this.d;
    }

    public void movePenguinSprite(int[] speed) {//recibe real fps

        if (!rLeft && !rRight && !jumping && !gravityAceleration) {
            this.d = pos1;
        }
        if (rLeft && gravityAceleration) {
            if (this.speed[1] > 0) {
                this.d = lPos4;
            }
            if (this.speed[1] < 0) {
                this.d = lPos5;
            }
        }
        if (rRight && gravityAceleration) {
            if (this.speed[1] > 0) {
                this.d = rPos4;
            }
            if (this.speed[1] < 0) {
                this.d = rPos5;
            }
        }
        if (rLeft && paddingSpeedAplication) {
            this.d = pos1;
        }
        if (rRight && paddingSpeedAplication) {
            this.d = pos2;
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
                }
            }
            if (this.speed[0] < -150) {//DERRAPAR SPRITE
                this.d = lPos3;
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
                }
            }
            if (this.speed[0] > 150) {//DERRAPAR SPRITE
                this.d = rPos3;
            }
        }
    }

    public void printPenguin(Canvas c) {
        this.getDrawable().draw(c);
    }

}
