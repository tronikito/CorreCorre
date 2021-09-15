package correcorrerework.game.graficos;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.ArrayList;

import correcorrerework.R;

import static correcorrerework.ResourcesClass.*;

public class Controls {

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
    public volatile boolean load = false;


    public Controls() {

        controlsList = new ArrayList<Drawable>();
        //jump
        Rect cRect = new Rect();
        cRect.left = widthScreen-500;
        cRect.right = widthScreen-50; //width 150px;
        cRect.top = heightScreen /2+50;
        cRect.bottom = heightScreen-50;//height 400px;
        this.cJump = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.b_empty,null);
        assert this.cJump != null;
        this.cJump.setBounds(cRect);
        this.bJump = cRect;
        //left
        cRect = new Rect();
        cRect.left = 0;
        cRect.right = widthScreen/2; //width 150px;
        cRect.top = 0;
        cRect.bottom = heightScreen;//height 400px;
        this.cLeft = VectorDrawableCompat.create(mactivity.getResources(),R.drawable.c_empty,null);
        assert this.cLeft != null;
        this.cLeft.setBounds(cRect);
        this.bLeft = cRect;
        //shoot
        cRect = new Rect();
        cRect.left = 300;
        cRect.right = 450;
        cRect.top = (int) (heightScreen/1.5);
        cRect.bottom = (int) (heightScreen/1.5+heightScreen/6);
        this.cShoot = VectorDrawableCompat.create(mactivity.getResources(),R.drawable.ic_007,null);
        assert this.cShoot != null;
        this.cShoot.setBounds(cRect);
        this.bShoot = cRect;
        //right
        cRect = new Rect();
        cRect.left = widthScreen/2;
        cRect.right = widthScreen;
        cRect.top = 0;
        cRect.bottom = heightScreen;
        this.cRight = VectorDrawableCompat.create(mactivity.getResources(),R.drawable.c_empty,null);
        assert this.cRight != null;
        this.cRight.setBounds(cRect);
        this.bRight = cRect;
        //shootDirection
        cRect = new Rect();
        cRect.left = 0;
        cRect.right = widthScreen;
        cRect.top = 0;
        cRect.bottom = heightScreen;
        this.cBar1 = VectorDrawableCompat.create(mactivity.getResources(),R.drawable.c_empty,null);
        assert this.cBar1 != null;
        this.cBar1.setBounds(cRect);
        this.bBar1 = cRect;
        load = true;

        controlsList.add(cJump);
        controlsList.add(cLeft);
        controlsList.add(cShoot);
        controlsList.add(cRight);
        controlsList.add(cBar1);
    }

    public synchronized void printControls(Canvas c) {
        for (int x = 0; x < controlsList.size(); x++) {
            controlsList.get(x).draw(c);
        }
    }
}
