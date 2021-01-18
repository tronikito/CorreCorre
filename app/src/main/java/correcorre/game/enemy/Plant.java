package correcorre.game.enemy;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.R;

import static correcorre.ResourcesClass.mactivity;

public class Plant extends PlantPhysics implements Enemy {
    private Drawable d;
    private Drawable pos1;
    private Drawable pos2;
    private Drawable pos3;
    private Drawable pos4;
    private int actualPos = 0;

    public Plant(int x, int y, int w, int h) {
        super(x,y,w,h);
        this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.e_plant1left,null);
        pos1 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.e_plant1left,null);
        pos2 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.e_plant1left,null);
        pos3 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.e_plant1left,null);
        pos4 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.e_plant1left,null);
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
