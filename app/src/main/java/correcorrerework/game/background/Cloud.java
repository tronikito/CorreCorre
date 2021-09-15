package correcorrerework.game.background;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorrerework.R;
import static correcorrerework.ResourcesClass.*;

public class Cloud extends BackgroundObject {

    private int width;
    private int height;
    private int pos;

    public Cloud(int w, int h, int pos) {

        this.width = w;
        this.height = h;
        this.pos = pos;

        //Rect
        int randomH = (int) Math.floor(Math.random() * Math.floor(heightScreen / 3));
        this.r.left = widthScreen / 6 * pos;
        this.r.right = widthScreen / 6 * pos + w;
        this.r.top = randomH;
        this.r.bottom = randomH + h;

        //Drawable
        if (pos == 1) {
            this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.b_cloud9, null);
        } else if (pos == 2) {
            this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.b_cloud7, null);
        } else if (pos == 3) {
            this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.b_cloud9, null);
        } else if (pos == 4) {
            this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.b_cloud10, null);
        }
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

