
package correcorrerework.game.background;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorrerework.R;
import correcorrerework.game.background.BackgroundObject;

import static correcorrerework.ResourcesClass.*;

public class Hillside extends BackgroundObject {

    private int width;
    private int height;

    public Hillside(int x, int y, int w, int h) {

        this.width = w;
        this.height = h;

        //Rect
        this.r.left = x;
        this.r.right = this.r.left + w;
        this.r.top = y;
        this.r.bottom = this.r.top + h;

        //Drawable
        this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.b_hillbils3, null);
    }
    
    @Override
    public void moveX(int speed) {
        if (this.r.left < 0 - this.width) {
            this.r.left = widthScreen;
            this.r.right = this.r.left + this.width;
        } else {
            this.r.left = this.r.left+speed;
            this.r.right = this.r.right+speed;
        }
    }
}
