package correcorre.graficos;

import android.graphics.Canvas;
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

    private PointF point;
    private Paint paint = new Paint();
    private static MatrixX matrixX;
    private static Controls controls;
    private static Background background;
    private static Penguin penguin;
    public int width = -1;
    public int height = -1;
    private Rect bLeft;
    private Rect bRight;
    private Rect bJump;
    private Rect rtouch = new Rect();


    public MyCanvas(Main m,MatrixX ma, Controls con, Background b, Penguin p) {
        super(m.getContext());

        matrixX = ma;
        controls = con;
        background = b;
        penguin = p;
        this.bJump = controls.bAll;
        this.bLeft = controls.bLeft;

        setBackgroundResource(R.drawable.background1);//BACKGROUND!!
    }

    @Override
    protected synchronized void onDraw(Canvas c) {


        background.printBackground(c);
        matrixX.printMatrixBack(c);
        penguin.printPenguin(c);
        matrixX.printMatrixFront(c);
        controls.printControls(c);
        penguin.printPenguinGrid(c);

        matrixX.getEnemys(c);


        if (point != null) {
            c.drawCircle(point.x, point.y, 100, paint);
        }
    }


    @Override
    public synchronized boolean onTouchEvent(@NonNull MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        this.rtouch.left = Math.round(x);
        this.rtouch.right = rtouch.left + 50;
        this.rtouch.top = Math.round(y);
        this.rtouch.bottom = rtouch.top + 50;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (Rect.intersects(this.rtouch,this.bJump)) {
                        if (!penguin.getJumping() && !penguin.getGravity()) {
                            penguin.jump();
                            penguin.setPressJump(true);
                        }
                    }
                    if (Rect.intersects(this.rtouch,this.bLeft)) {
                        penguin.setDirection();
                    }
                    point = new PointF(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    penguin.setPressJump(false);
                    if (point != null) {
                        point.set(x, y);
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    penguin.setPressJump(false);
                case MotionEvent.ACTION_CANCEL:
                    penguin.setPressJump(false);
                    point = null;
                    invalidate();
                    break;
            }
        return true;
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

    /*  public void importarDrawable(Canvas canvas) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mountain1,options);
        Rect cuadrado = new Rect(501,50,bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,null,cuadrado,null);
    }*/

