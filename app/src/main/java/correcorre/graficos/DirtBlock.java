package correcorre.graficos;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.R;

public class DirtBlock extends Block {

    protected Rect finalSize;
    private Drawable drawable;

    public DirtBlock(Context c) {
        super(c);
        //this.finalSize = new Rect(x, y, x + width, y + height);// TAMAÑO FINAL (POSICION ABSOLUTA)
        //this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);
        int random = (int) Math.floor(Math.random() * Math.floor(2));
        if (random == 0) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt, null);
        } else if (random == 1) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt1, null);
        } else if (random == 2) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
        }
    }

    public Rect getRect() {
        return this.finalSize;
    }
    public Drawable getDrawable() {
        this.drawable.setBounds(finalSize);
        return drawable;
    }
    public void setDrawable(Drawable d) {
        this.drawable = d;
    }
    public void setRect(Rect r) {
        this.finalSize = r;
    }
    public void moveX(float speed) {
        this.finalSize.left -= speed;
        this.finalSize.right -= speed;
    }
    public void moveY(float speed) {
        this.finalSize.top -= speed;
        this.finalSize.bottom -= speed;
    }
    /*public void isEmpty(Context c) {
        this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_azul, null);
    }*/

}
