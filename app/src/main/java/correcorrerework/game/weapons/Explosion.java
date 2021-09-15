package correcorrerework.game.weapons;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;


import correcorrerework.R;

import static correcorrerework.ResourcesClass.*;


public class Explosion {

    private int speedCount = 0;
    private int totalCount = 0;
    private int actualPos = 0;
    private Drawable pos1;
    private Drawable pos2;
    private Drawable pos3;
    private Drawable pos4;
    private Drawable d;
    private Rect r;

    public Explosion(Rect enemy) {
        this.r = new Rect();
        this.r.top = enemy.top;
        this.r.bottom = enemy.bottom;
        this.r.left = enemy.left;
        this.r.right = enemy.right;

        this.pos1 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.explosion1,null);
        this.pos2 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.explosion2,null);
        this.pos3 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.explosion3,null);
        this.pos4 = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.explosion4,null);
        this.d = pos4;
    }

    public void printExplosion(Canvas c) {
        this.getDrawable().draw(c);
    }
    public Drawable getDrawable() {
        this.d.setBounds(this.r);
        return this.d;
    }
    public synchronized void moveX(long speed) {
        speed = -speed;
        this.r.left += speed;
        this.r.right += speed;

    }
    public synchronized void moveY(long speed) {
        speed = -speed;
        this.r.top += speed;
        this.r.bottom += speed;
    }

    public void moveExplosionSprite() {//recibe real fps
        speedCount += 1;
        if (speedCount >= 5) {
            if (actualPos == 1) this.d = pos1;
            if (actualPos == 2) this.d = pos2;
            if (actualPos == 3) this.d = pos3;
            if (actualPos == 4) this.d = pos4;
            actualPos++;
            if (actualPos > 4) actualPos = 1;
            totalCount += speedCount;
            speedCount = 0;
        }
        if (totalCount >= 25) {
            for (int x = 0; x < explosionList.size(); x++) {
                if (explosionList.get(x).equals(this)) {
                    explosionList.remove(this);
                }
            }
        }
    }
}
