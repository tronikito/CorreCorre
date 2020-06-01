package correcorre.graficos;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class MatrixBlock  {//DEPRECATED -------------------------

    protected Rect finalSize;
    private Drawable drawable;

    public MatrixBlock(int x, int y, int width, int height) {
        this.finalSize = new Rect(x,y, x + width,y + height);// TAMAÑO FINAL (POSICION ABSOLUTA)
    }

    public void setDrawable(Drawable d) {
        this.drawable = d;
    }
    public Drawable getDrawable() {//demand by printMatrix(); Last stance, setBounds
        this.drawable.setBounds(finalSize);
        return this.drawable;
    }

    public void moveX(float speed) {
        this.finalSize.left -= speed;
        this.finalSize.right -= speed;
    }
    public Rect getRect() {
        return this.finalSize;
    }
}