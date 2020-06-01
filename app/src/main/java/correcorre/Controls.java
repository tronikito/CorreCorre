package correcorre;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
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
    private static LCanvas canvas;
    public volatile boolean load = false;

    public Controls(LCanvas c,Main m) {
        main = m;
        canvas = c;
        this.cWidth = c.getWidth();
        this.cHeight = c.getHeight();
    }
    public void generateControls() {

        Rect cRect = new Rect();
        cRect.left = 50;
        cRect.right = 500; //width 150px;
        cRect.top = cHeight/2-50;
        cRect.bottom = cHeight;//height 400px;
        this.bAll = cRect;
        this.cAll = VectorDrawableCompat.create(main.getResources(),R.drawable.ic_007,null);
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
        cRect = new Rect();
        cRect.left = 300;
        cRect.right = 450;
        cRect.top = (int) (cHeight/1.5);
        cRect.bottom = (int) (cHeight/1.5+cHeight/6);
        this.cRight = VectorDrawableCompat.create(main.getResources(),R.drawable.ic_007,null);
        this.cRight.setBounds(cRect);
        this.bRight = cRect;
        load = true;
    }

    public void printControls(Canvas canvas) {
        this.cAll.draw(canvas);
        this.cLeft.draw(canvas);
        this.cRight.draw(canvas);
    }
}
