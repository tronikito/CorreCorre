package correcorre.enemy;

import android.graphics.Rect;

import correcorre.Main;
import correcorre.graficos.MatrixX;

public class SpiderPhysics {

    protected static Main main;
    protected static MatrixX matrixX;
    protected Rect r;
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;

    public SpiderPhysics (Main m, MatrixX ma, int xPos, int yPos, int w, int h) {
        main = m;
        matrixX = ma;

        this.r = new Rect();
        this.r.left = xPos;
        this.r.right = this.r.left + w;
        this.r.top = yPos;
        this.r.bottom = this.r.top + h;
        System.out.println(this.r.left);
        System.out.println(this.r.right);
        System.out.println(this.r.top);
        System.out.println(this.r.bottom);
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = w;
        this.height = h;
    }
    public void moveX(long speed) {
        this.r.left -= speed;
        this.r.right -= speed;
    }
    public void moveY(long speed) {
        this.r.top -= speed;
        this.r.bottom -= speed;
    }
}
