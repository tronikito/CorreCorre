package correcorre.background;

import android.content.Context;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.graficos.MatrixX;
import correcorre.R;

public class Hillside extends BackgroundObject {
    public Hillside(Context c, MatrixX m, int w, int h) {

        //Rect
        this.r.left = m.getWidth();
        this.r.right = this.r.left + w;
        this.r.top = (m.getHeight() - h);
        this.r.bottom = this.r.top + h;

        //Drawable
        int random = (int) Math.floor(Math.random() * Math.floor(2));

        if (random == 0) {
            this.d = VectorDrawableCompat.create(c.getResources(), R.drawable.c_grass, null);
        } else if (random == 1) {
            this.d = VectorDrawableCompat.create(c.getResources(), R.drawable.c_grass1, null);
        } else if (random == 2) {
            this.d = VectorDrawableCompat.create(c.getResources(), R.drawable.c_grass2, null);
        }
    }
}
