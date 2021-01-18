package correcorre.game.graficos;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import correcorre.R;
import static correcorre.ResourcesClass.*;

public class MyCanvas extends View {

    private PointF point1;
    private PointF point2;
    private Paint paint = new Paint();
    private Rect bLeft;
    private Rect bShoot;
    private Rect bRight;
    private Rect bBar1;
    private Rect bJump;
    private Rect rtouch1 = new Rect();
    private Rect rtouch2 = new Rect();
    private boolean shooting = false;
    boolean rtouch1bShoot = false;
    private boolean rtouch2bShoot = false;

    public MyCanvas() {
        super(mactivity);

        this.bJump = controls.bJump;
        this.bLeft = controls.bLeft;
        this.bShoot = controls.bShoot;
        this.bRight = controls.bRight;
        this.bBar1 = controls.bBar1;

        setBackgroundResource(R.drawable.background1);//BACKGROUND!!

        paint.setColor(Color.RED);
    }

    @Override
    protected synchronized void onDraw(Canvas c) {

            background.printBackground(c);

            matrixX.printBack(c);
            penguin.printPenguin(c);
            matrixX.printFront(c);

            frontground.printFrontground(c);
            controls.printControls(c);
            scoreboard.printScoreBoard(c);

        if (point1 != null) {
            c.drawCircle(point1.x, point1.y, 25, paint);
        }
        if (point2 != null) {
            c.drawCircle(point2.x, point2.y, 25, paint);
        }


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

            rtouch1bShoot = Rect.intersects(this.rtouch1, this.bShoot);
            rtouch2bShoot = Rect.intersects(this.rtouch2, this.bShoot);

            if (rtouch1bShoot || rtouch2bShoot) {
                if (shooting) {
                    shooting = false;
                    penguin.shoot(false);
                }
                else {
                    shooting = true;
                    penguin.shoot(true);
                }
            }
        }

        if (rtouch1.left == 0) point1 = null;
        if (rtouch2.left == 0) point2 = null;

        return true;
    }

    public void checkControls() {

        boolean rtouch1bJump = Rect.intersects(this.rtouch1, this.bJump);
        boolean rtouch2bJump = Rect.intersects(this.rtouch2, this.bJump);

        if (rtouch1bJump || rtouch2bJump) {
            if (!penguin.getJumping() && !penguin.getGravity()) {
                penguin.jump();
                penguin.setPressJump(true);
            }
        }

        //shooting Angle
        boolean rtouch1bBar1 = Rect.intersects(this.rtouch1, this.bBar1)  && !rtouch1bJump && !rtouch1bShoot;
        boolean rtouch2bBar1 = Rect.intersects(this.rtouch2, this.bBar1)  && !rtouch2bJump && !rtouch2bShoot;

        if (rtouch1bBar1 || rtouch2bBar1) {
            float position = 0;
            if (rtouch2bBar1) position = this.rtouch2.top - this.bBar1.top;
            if (rtouch1bBar1) position = this.rtouch1.top - this.bBar1.top; //prevalece 1

            float totalBarHeight = this.bBar1.bottom - this.bBar1.top;
            float percentBar = (float) Math.floor(position / (totalBarHeight / 100));
            penguin.setBulletSpeed((int) percentBar);
        }

        //walk direction
        boolean rtouch1bLeft = Rect.intersects(this.rtouch1, this.bLeft) && !rtouch1bJump && !rtouch1bShoot;
        boolean rtouch2bLeft = Rect.intersects(this.rtouch2, this.bLeft) && !rtouch2bJump && !rtouch2bShoot;

        boolean rtouch1bRight = Rect.intersects(this.rtouch1, this.bRight) && !rtouch1bJump && !rtouch1bShoot;
        boolean rtouch2bRight = Rect.intersects(this.rtouch2, this.bRight) && !rtouch2bJump && !rtouch2bShoot;

        if ((rtouch1bLeft || rtouch2bLeft) && !(rtouch1bRight || rtouch2bRight)) {
            penguin.setDirection("left");
        } else if ((rtouch1bRight || rtouch2bRight) && !(rtouch1bLeft || rtouch2bLeft)) {
            penguin.setDirection("right");
        }

        //CANCEL ACTION
        if ((!rtouch1bJump && !rtouch2bJump)) {
            penguin.setPressJump(false);
        }
    }
}