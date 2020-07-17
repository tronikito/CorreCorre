package correcorre.enemy;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;
import correcorre.graficos.MatrixX;

public class Plant extends PlantPhysics implements Enemy {
    private Drawable d;
    private Drawable pos1;
    private Drawable pos2;
    private Drawable pos3;
    private Drawable pos4;
    private int actualPos = 0;

    public Plant(Main m, MatrixX ma, int x, int y, int w, int h) {
        super(m,ma,x,y,w,h);
        this.d = VectorDrawableCompat.create(m.getResources(), R.drawable.e_plant1left,null);
        pos1 = VectorDrawableCompat.create(m.getResources(), R.drawable.e_plant1left,null);
        pos2 = VectorDrawableCompat.create(m.getResources(), R.drawable.e_plant1left,null);
        pos3 = VectorDrawableCompat.create(m.getResources(), R.drawable.e_plant1left,null);
        pos4 = VectorDrawableCompat.create(m.getResources(), R.drawable.e_plant1left,null);
    }

    private Drawable getDrawable() {
        this.d.setBounds(this.r);
        return this.d;
    }

    public void moveSprite() {//recibe real fps
        actualPos++;
        if (rLeft) {
            if (actualPos == 5) this.d = pos1;
            if (actualPos == 10) {
                this.d = pos2;
                actualPos = 0;
            }
        }
        if (rRight) {
            if (actualPos == 5) this.d = pos3;
            if (actualPos == 10) {
                this.d = pos4;
                actualPos = 0;
            }
        }
    }
    public void printEnemy(Canvas c) {
        this.getDrawable().draw(c);
    }

}
