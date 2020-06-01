package correcorre.graficos;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public abstract class Block {

    public Context context;
    public int offsetX = 0;
    public int offsetY = 0;

    public Block(Context c) {
        this.context = c;
    }

    public abstract Rect getRect();
    public abstract Drawable getDrawable();
    public abstract void setDrawable(Drawable d);
    public abstract void setRect(Rect r);
    public abstract void moveX(float speed);
    public abstract void moveY(float speed);
    public int getOffsetX() { return this.offsetX; }
    public int getOffsetY() { return this.offsetY; }
    public void setOffsetX(int o) { this.offsetX = o; }
    public void setOffsetY(int o) { this.offsetY = o; }

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