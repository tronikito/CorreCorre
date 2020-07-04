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
    public Rect bLeft;
    public Rect bRight;
    public Rect bAll;
    private Drawable cAll;
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
        this.cAll = VectorDrawableCompat.create(main.getResources(), R.drawable.ic_007,null);
        this.cAll.setBounds(cRect);
        this.bAll = cRect;
        //izquierda
        cRect = new Rect();
        cRect.left = 100;
        cRect.right = 250; //width 150px;
        cRect.top = (int) (cHeight/1.5);
        cRect.bottom = (int) (cHeight/1.5+cHeight/6);//height 400px;
        this.cLeft = VectorDrawableCompat.create(main.getResources(),R.drawable.ic_007,null);
        this.cLeft.setBounds(cRect);
        this.bLeft = cRect;
        //derecha
        //cRect = new Rect();
        //cRect.left = 300;
        //cRect.right = 450;
        //cRect.top = (int) (cHeight/1.5);
        //cRect.bottom = (int) (cHeight/1.5+cHeight/6);
        //this.cRight = VectorDrawableCompat.create(main.getResources(),R.drawable.ic_007,null);
        //this.cRight.setBounds(cRect);
        //this.bRight = cRect;
        load = true;
    }

    public void printControls(Canvas canvas) {
        this.cAll.draw(canvas);
        this.cLeft.draw(canvas);
        //this.cRight.draw(canvas);
    }
}
