package correcorre.background;

import android.content.Context;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.graficos.MatrixX;
import correcorre.R;

public class Hillside extends BackgroundObject {

    private static MatrixX m;
    private int width;
    private int height;

    public Hillside(Context c, MatrixX m, int x, int y, int w, int h) {

        this.m = m;
        this.width = w;
        this.height = h;

        //Rect
        this.r.left = x;
        this.r.right = this.r.left + w;
        this.r.top = y;
        this.r.bottom = this.r.top + h;

        //Drawable
        this.d = VectorDrawableCompat.create(c.getResources(), R.drawable.b_hillbils3, null);
    }
    
    @Override
    public void moveX(int speed) {
        if (this.r.left < 0 - this.width) {
            this.r.left = m.getWidth();
            this.r.right = this.r.left + this.width;
        } else {
            this.r.left = this.r.left+speed;
            this.r.right = this.r.right+speed;
        }
    }
}
