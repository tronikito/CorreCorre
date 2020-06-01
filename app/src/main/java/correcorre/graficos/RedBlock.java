package correcorre.graficos;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.R;

public class RedBlock extends Block {

    protected Rect finalSize;
    private Drawable drawable;

    public RedBlock(Context c) {
        super(c);
        this.drawable = VectorDrawableCompat.create(this.context.getResources(), R.drawable.c_rojo, null);
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

}

