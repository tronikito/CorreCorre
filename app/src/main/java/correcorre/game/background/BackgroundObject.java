package correcorre.game.background;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public abstract class BackgroundObject {

    Drawable d;
    Rect r = new Rect();

    public void moveX(int speed) {
        this.r.left = this.r.left+speed;
        this.r.right = this.r.right+speed;
    }
    public void moveY(int speed) {
        this.r.top = this.r.top+speed;
        this.r.bottom = this.r.bottom+speed;
    }
    public Drawable getDrawable() {
        this.d.setBounds(r);
        return this.d;
    }
    public Rect getRect() {
        return this.r;
    }
}
