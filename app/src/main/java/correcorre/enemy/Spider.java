package correcorre.enemy;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;

public class Spider extends SpiderPhysics {
    private Drawable d;

    public Spider( Main m, MatrixX ma, int x, int y, int w, int h) {
        super(m,ma,x,y,w,h);
        this.d = VectorDrawableCompat.create(m.getResources(), R.drawable.e_spider1left,null);
    }

    private Drawable getDrawable() {
        this.d.setBounds(this.r);
        return this.d;
    }

    public void printEnemy(Canvas c) {
        this.getDrawable().draw(c);
    }
}
