package correcorre.selectmap;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.ArrayList;

import correcorre.R;

import static correcorre.ResourcesClass.*;

public class MapCanvas extends View {

    private PointF point1;
    private PointF point2;
    private Paint paint = new Paint();
    private Rect rtouch1 = new Rect();
    private Rect rtouch2 = new Rect();
    boolean rtouch1bShoot = false;
    private boolean rtouch2bShoot = false;
    public Rect map1 = new Rect();
    public Drawable map = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.stagebutton1, null);
    private ArrayList<Rect> stageButtons = new ArrayList<Rect>();

    public MapCanvas() {
        super(mactivity);
        //setBackgroundResource(R.drawable.background1);//BACKGROUND!!
        paint.setColor(Color.RED);
        int stageButtonWidth = widthScreen / 7;
        int widthScreenBorder = stageButtonWidth;


        for (int u = 0; u < 2; u++) {

            for (int i = 0; i < 5; i++) {

                Rect newRect = new Rect();

                newRect.left = stageButtonWidth + (stageButtonWidth * i);
                newRect.right = (stageButtonWidth * 2) + (stageButtonWidth * i);
                newRect.top = ((heightScreen/2) - stageButtonWidth) + (stageButtonWidth * u);
                newRect.bottom = newRect.top + (stageButtonWidth);

                stageButtons.add(newRect);
            }

        }
        /*
        map1.left = 100;// widthScreen / 3;
        map1.right = 400; //widthScreen - widthScreen / 3;
        map1.top = 200; //heightScreen / 3;
        map1.bottom = 500; //heightScreen - heightScreen / 3;
        */
    }

    @Override
    protected synchronized void onDraw(Canvas c) {

        //setBackgroundResource(R.color.black_overlay);//BACKGROUND!!
        //c.drawCircle(widthScreen / 2, heightScreen / 2, 25, paint);

        for (int x = 0; x < stageButtons.size(); x++) {
            map.setBounds(stageButtons.get(x));


            if (point1 != null) {
                c.drawCircle(point1.x, point1.y, 25, paint);
            }
            if (point2 != null) {
                c.drawCircle(point2.x, point2.y, 25, paint);
            }

            map.draw(c);
        }

        //mapMatrixX.printSelectMapFrame(c);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public synchronized boolean onTouchEvent(@NonNull MotionEvent event) {

        this.rtouch1 = new Rect();
        this.rtouch2 = new Rect();

        if (event.getAction() != MotionEvent.ACTION_UP &&
                event.getAction() != MotionEvent.ACTION_CANCEL) {

            float x1 = event.getX(0);
            float y1 = event.getY(0);
            float x2 = 0;
            float y2 = 0;

            this.rtouch1.left = Math.round(x1);
            this.rtouch1.right = rtouch1.left;
            this.rtouch1.top = Math.round(y1);
            this.rtouch1.bottom = rtouch1.top;

            if (event.getPointerCount() > 1) {
                x2 = event.getX(1);
                y2 = event.getY(1);
                this.rtouch2.left = Math.round(x2);
                this.rtouch2.right = rtouch2.left;
                this.rtouch2.top = Math.round(y2);
                this.rtouch2.bottom = rtouch2.top;
            }

            point1 = new PointF(x1, y1);
            if (event.getPointerCount() > 1) {
                point2 = new PointF(x2, y2);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            for (int x = 0; x < stageButtons.size(); x++) {
                rtouch1bShoot = Rect.intersects(this.rtouch1, stageButtons.get(x));
                rtouch2bShoot = Rect.intersects(this.rtouch2, stageButtons.get(x));

                if (rtouch1bShoot || rtouch2bShoot) {
                    mactivity.startGame();
                }
            }

        }

        if (rtouch1.left == 0) point1 = null;
        if (rtouch2.left == 0) point2 = null;

        return true;
    }
}