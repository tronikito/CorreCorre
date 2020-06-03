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


















/*package correcorre.graficos;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Block extends NewBlock {

    protected Rect finalSize;

    public Block(Context c, int x, int y, int width, int height, int sprite) {
        super(c,sprite);
        this.finalSize = new Rect(x,y, x + width,y + height);// TAMAÑO FINAL (POSICION ABSOLUTA)
    }
    public Drawable drawable() {
        this.drawable.setBounds(finalSize);
        return drawable;
    }

    public void moveX(float speed) {
        this.finalSize.left -= speed;
        this.finalSize.right -= speed;
    }
    public Rect getRect() {
        return this.finalSize;
    }
}*/