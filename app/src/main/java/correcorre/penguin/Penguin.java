package correcorre.penguin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;
import correcorre.background.BackgroundObject;

public class Penguin {

    private static Main main;
    private Context c;
    private int aceleration;
    private int marginLeft;
    private int marginRight;
    private Drawable pos1;
    private Drawable pos2;
    private Drawable pos3;
    private Drawable d;
    private boolean isMove;
    private Rect r = new Rect();
    private int width;
    private int height;
    private int speedCount = 0;
    private int actualPos;
    public Penguin(Main m, int x, int y, int w, int h) {//150,182

        this.c = m.getContext();
        this.main = m;
        this.width = w;
        this.height = h;

        this.r.left = x;
        this.r.right = this.r.left + w;
        this.r.top = y;
        this.r.bottom = this.r.top + h;
        pos1 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_penguin1,null);
        pos2 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_penguin2,null);
        pos3 = VectorDrawableCompat.create(c.getResources(), R.drawable.p_penguin3,null);
        this.d = pos1;
    }

    public Drawable getDrawable() {
        this.d.setBounds(r);
        return this.d;
    }

    public void moveX(int[] speed) {//recibe real fps

        speedCount += Math.abs(speed[0]);
        if (speedCount >= 30) {
            if (actualPos == 1) this.d = pos2;
            if (actualPos == 2) this.d = pos3;
            actualPos++;
            if (actualPos > 2) actualPos = 1;
            speedCount = 0;
        }
        //this.r.left = this.r.left + speed;
        //this.r.right = this.r.right + speed;
    }

    public void printPenguin(Canvas c) {
        this.getDrawable().draw(c);
    }

}
