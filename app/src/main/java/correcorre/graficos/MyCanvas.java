package correcorre.graficos;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;

import correcorre.Main;
import correcorre.R;
import correcorre.background.Background;
import correcorre.penguin.Penguin;

public class MyCanvas extends View {

    private PointF point1;
    private PointF point2;
    private Paint paint = new Paint();
    private static MatrixX matrixX;
    private static Controls controls;
    private static Background background;
    @SuppressLint("StaticFieldLeak")
    private volatile static Penguin penguin;
    private static Scoreboard scoreboard;
    public int width = -1;
    public int height = -1;
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

    public MyCanvas(Main m, MatrixX ma, Controls con, Background b, Penguin p, Scoreboard s) {
        super(m.getContext());

        matrixX = ma;
        controls = con;
        background = b;
        penguin = p;
        scoreboard = s;

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

        matrixX.printThings(c);

        controls.printControls(c);
        scoreboard.printScoreBoard(c);

        if (point1 != null) {
            c.drawCircle(point1.x, point1.y, 25, paint);
        }
        if (point2 != null) {
            c.drawCircle(point2.x, point2.y, 25, paint);
        }
    }

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
/** SVG *****************************************************************************************

        public void vectorial(Canvas canvas,int xSprite,int ySprite, int wSprite,int hSprite,int sprite) {
        int width = xSprite + wSprite;
        int height = ySprite + hSprite;
        Drawable drawable = VectorDrawableCompat.create(getResources(), sprite,null);
        Rect finalSize = new Rect(xSprite,ySprite, width,height);// TAMAÑO FINAL (POSICION ABSOLUTA)
        drawable.setBounds(finalSize);
        drawable.draw(canvas);
    }*/

/** PNG *****************************************************************************************

    public void drawPenguin(Bitmap penguin, Canvas canvas) {//cambiar sprite
        int f = 5;//factor size
        int cW = canvas.getWidth();
        int cH = canvas.getHeight();

        // TAMAÑO FINAL (POSICION ABSOLUTA)
        Rect finalSize = new Rect(cW/2-(penguin.getHeight()/2 * f), cH-(penguin.getHeight()*f),
        canvas.getWidth()/2 + (penguin.getWidth()/2 * f),canvas.getHeight());

        canvas.drawBitmap(this.penguin.get(penguinPosition), null, finalSize, null);
        this.drawPenguin = false;
    }
    public void spritePenguin(ArrayList penguin) {//entrar sprites
        this.drawPenguin = true;
        this.penguin = penguin;
        //System.out.println(penguin);
    }*/

/** PNG *****************************************************************************************

    public void importar(Canvas canvas) {//importar PNG
        AssetManager manager = getContext().getAssets();
        Bitmap bitmap = null;
        // read a Bitmap from Assets
        InputStream open = null;
        try {
            open = manager.open("mountai1.png");
            bitmap = BitmapFactory.decodeStream(open);
            // Assign the bitmap to an ImageView in this layout
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //COGER sprite, X Y, ANCHO DESDE X, LARGO DESDE Y;
        Bitmap sprite = Bitmap.createBitmap(bitmap,750,500,150,50);

        //COLOCAR sprite X,Y, ANCHO,LARGO
        Rect finalSize = new Rect(50, 50, 1800,100);// TAMAÑO FINAL (POSICION ABSOLUTA)
        canvas.drawBitmap(sprite, null, finalSize, null);

    }*******************************************************************************************/