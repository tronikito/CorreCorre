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
    public Drawable cRight;
    public Drawable cRight2;
    public Rect bLeft;
    public Rect bRight;
    public Rect bRight2;
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
        //izquierda
        cRect = new Rect();
        cRect.left = 100;
        cRect.right = 250; //width 150px;
        cRect.top = (int) (cHeight/1.5);
        cRect.bottom = (int) (cHeight/1.5+cHeight/6);//height 400px;
        this.cLeft = VectorDrawableCompat.create(main.getResources(),R.drawable.b_empty,null);
        this.cLeft.setBounds(cRect);
        this.bLeft = cRect;
        //derecha
        cRect = new Rect();
        cRect.left = 300;
        cRect.right = 450;
        cRect.top = (int) (cHeight/1.5);
        cRect.bottom = (int) (cHeight/1.5+cHeight/6);
        this.cRight = VectorDrawableCompat.create(main.getResources(),R.drawable.ic_007,null);
        this.cRight.setBounds(cRect);
        this.bRight = cRect;
        //derechaArriba
        cRect = new Rect();
        cRect.left = 300;
        cRect.right = 450;
        cRect.top = (int) (cHeight/1.5) - 200;
        cRect.bottom = (int) (cHeight/1.5+cHeight/6) - 200;
        this.cRight2 = VectorDrawableCompat.create(main.getResources(),R.drawable.ic_007,null);
        this.cRight2.setBounds(cRect);
        this.bRight2 = cRect;
        load = true;

    }

    public void printControls(Canvas canvas) {
        this.cJump.draw(canvas);
        this.cLeft.draw(canvas);
        this.cRight.draw(canvas);
        this.cRight2.draw(canvas);
    }
}
