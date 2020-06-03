package correcorre.blocks;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public abstract class Block {

    public Context context;
    protected Rect finalSize;
    protected Drawable drawable;

    public Block(Context c) {
        this.context = c;
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