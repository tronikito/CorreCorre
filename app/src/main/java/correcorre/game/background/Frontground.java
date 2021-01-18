package correcorre.game.background;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.R;

import static correcorre.ResourcesClass.*;

public class Frontground {

    public Rect r1;
    public Rect r2;
    public Drawable d1;
    public Drawable d2;

    public Frontground() {


        //Rect
        this.r1 = new Rect();
        this.r1.left = 0;
        this.r1.right = widthScreen *2;
        this.r1.top = 0;
        this.r1.bottom = heightScreen;

        this.r2 = new Rect();
        this.r2.left = this.r1.right;
        this.r2.right = this.r2.left + widthScreen *2;
        this.r2.top = 0;
        this.r2.bottom = heightScreen;

        //Drawable
        this.d1 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.b_frontground5, null);
        this.d2 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.b_frontground5, null);

        //Hillside hillside1 = new Hillside(0,sizeDot*3,widthScreen,heightScreen);
        //Hillside hillside2 = new Hillside(widthScreen,sizeDot*3,widthScreen,heightScreen);

        //frontground.add(hillside1);
        //frontground.add(hillside2);
    }

    public void moveX(int speed) {
        if (0 > this.r1.right) {
            this.r1.left = widthScreen;
            this.r1.right = this.r1.left + widthScreen*2;
        } else {
            this.r1.left = this.r1.left + speed;
            this.r1.right = this.r1.right + speed;
        }
        if (0 > this.r2.right) {
            this.r2.left = widthScreen;
            this.r2.right = this.r2.left + widthScreen*2;
        } else {
            this.r2.left = this.r2.left + speed;
            this.r2.right = this.r2.right + speed;
        }
    }

    public synchronized void moveFrontground(int[] speed) {
        this.moveX(-(speed[0]));
    }

    public synchronized void printFrontground(Canvas c) {
        d1.setBounds(r1);
        d1.draw(c);
        d2.setBounds(r2);
        d2.draw(c);
        //for (int x = 0; x < frontgroundList.size(); x++) {
            //BackgroundObject o = frontgroundList.get(x);
            //o.getDrawable().draw(c);
        //}
    }

}
