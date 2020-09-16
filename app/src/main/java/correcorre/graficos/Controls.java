package correcorre.graficos;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;
import correcorre.graficos.LCanvas;

public class Controls {

    private int cWidth;
    private int cHeight;
    public Drawable cLeft;
    public Drawable cShoot;
    public Drawable cRight;
    public Drawable cBar1;
    public Rect bLeft;
    public Rect bShoot;
    public Rect bRight;
    public Rect bBar1;
    public Rect bJump;
    private Drawable cJump;
    private static Main main;
    public volatile boolean load = false;

    public Controls(Main m,LCanvas c) {
        main = m;
        this.cWidth = c.getWidth();
        this.cHeight = c.getHeight();
    }
    public void generateControls() {

        //jump
        Rect cRect = new Rect();
        cRect.left = cWidth-500;
        cRect.right = cWidth-50; //width 150px;
        cRect.top = (int) cHeight/2+50;
        cRect.bottom = cHeight-50;//height 400px;
        this.cJump = VectorDrawableCompat.create(main.getResources(), R.drawable.b_empty,null);
        this.cJump.setBounds(cRect);
        this.bJump = cRect;
        //left
        cRect = new Rect();
        cRect.left = 0;
        cRect.right = cWidth/2; //width 150px;
        cRect.top = 0;
        cRect.bottom = cHeight;//height 400px;
        this.cLeft = VectorDrawableCompat.create(main.getResources(),R.drawable.c_empty,null);
        this.cLeft.setBounds(cRect);
        this.bLeft = cRect;
        //shoot
        cRect = new Rect();
        cRect.left = 300;
        cRect.right = 450;
        cRect.top = (int) (cHeight/1.5);
        cRect.bottom = (int) (cHeight/1.5+cHeight/6);
        this.cShoot = VectorDrawableCompat.create(main.getResources(),R.drawable.ic_007,null);
        this.cShoot.setBounds(cRect);
        this.bShoot = cRect;
        //right
        cRect = new Rect();
        cRect.left = cWidth/2;
        cRect.right = cWidth;
        cRect.top = 0;
        cRect.bottom = cHeight;
        this.cRight = VectorDrawableCompat.create(main.getResources(),R.drawable.c_empty,null);
        this.cRight.setBounds(cRect);
        this.bRight = cRect;
        //shootDirection
        cRect = new Rect();
        cRect.left = 0;
        cRect.right = cWidth;
        cRect.top = 0;
        cRect.bottom = cHeight;
        this.cBar1 = VectorDrawableCompat.create(main.getResources(),R.drawable.c_empty,null);
        this.cBar1.setBounds(cRect);
        this.bBar1 = cRect;
        load = true;

    }

    public void printControls(Canvas canvas) {
        this.cJump.draw(canvas);
        this.cLeft.draw(canvas);
        this.cShoot.draw(canvas);
        this.cRight.draw(canvas);
        this.cBar1.draw(canvas);
    }
}
