package correcorre.background;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.ArrayList;

import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;
import correcorre.penguin.Penguin;

public class Frontground {

    private ArrayList<BackgroundObject> frontground = new ArrayList<BackgroundObject>();
    private volatile static Penguin penguin;
    private static Main main;
    private static MatrixX matrixX;
    Rect r1;
    Rect r2;
    Drawable d1;
    Drawable d2;

    public Frontground(Context c, Main m, MatrixX ma) {


        //Rect
        this.r1 = new Rect();
        this.r1.left = 0;
        this.r1.right = ma.getWidth() * 2;
        this.r1.top = 0;
        this.r1.bottom = ma.getHeight();

        this.r2 = new Rect();
        this.r2.left = this.r1.right;
        this.r2.right = this.r2.left + ma.getWidth() * 2;
        this.r2.top = 0;
        this.r2.bottom = ma.getHeight();

        //Drawable
        this.d1 = VectorDrawableCompat.create(c.getResources(), R.drawable.b_frontground5, null);
        this.d2 = VectorDrawableCompat.create(c.getResources(), R.drawable.b_frontground5, null);

        main = m;
        matrixX = ma;
        //Hillside hillside1 = new Hillside(c,ma,0,ma.getSize()*3,ma.getWidth(),ma.getHeight());
        //Hillside hillside2 = new Hillside(c,ma,ma.getWidth(),ma.getSize()*3,ma.getWidth(),ma.getHeight());

        //frontground.add(hillside1);
        //frontground.add(hillside2);
    }

    public void moveX(int speed) {
        if (this.r1.left < 0 - matrixX.getWidth() * 2) {
            this.r1.left = matrixX.getWidth();
            this.r1.right = this.r1.left + matrixX.getWidth();
        } else {
            this.r1.left = this.r1.left + speed;
            this.r1.right = this.r1.right + speed;
        }
        if (this.r2.left < 0 - matrixX.getWidth() * 2) {
            this.r2.left = matrixX.getWidth();
            this.r2.right = this.r2.left + matrixX.getWidth();
        } else {
            this.r2.left = this.r2.left + speed;
            this.r2.right = this.r2.right + speed;
        }
    }

    public synchronized void moveBackground(int[] speed) {

        this.moveX(-(speed[0]));
    }

    public void setPenguin(Penguin p) {
        penguin = p;
    }

    public void printBackground(Canvas c) {
        this.d1.setBounds(this.r1);
        this.d1.draw(c);
        this.d2.setBounds(this.r2);
        this.d2.draw(c);
        for (int x = 0; x < this.frontground.size(); x++) {
            //BackgroundObject o = this.frontground.get(x);
            //o.getDrawable().draw(c);

        }

    }
}
