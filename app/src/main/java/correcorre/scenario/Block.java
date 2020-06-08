package correcorre.scenario;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;

public class Block {

    private static Main main;
    public Context context;
    protected Rect finalSize;
    protected Drawable drawable;
    private Drawable sprite1;
    private Drawable sprite2;
    private Drawable sprite3;
    private String type;
    private int sprite;

    public Block(Context c, Main m, String t, int s) {
        this.context = c;
        main = m;
        this.type = t;
        this.sprite = s;
        this.finalSize = new Rect();
    }

    public Rect getRect() {
        return this.finalSize;
    }

    public Drawable getDrawable() {
        this.drawable.setBounds(finalSize);
        return drawable;
    }

    public void setDrawable(Drawable d) {
        this.drawable = d;
    }

    public void moveX(long speed) {
        this.finalSize.left -= speed;
        this.finalSize.right -= speed;
    }
    public void moveY(long speed) {
        this.finalSize.top -= speed;
        this.finalSize.bottom -= speed;
    }

    //private void setSprite() {
        //if (main.fps < 20) this.drawable = sprite1;
        //else if (main.fps >= 20 && main.fps < 40) if (sprite2 != null) this.drawable = sprite2;
        //else if (main.fps >= 40) if (sprite3 != null) this.drawable = sprite3;
    //}

    public String getType() {
        return this.type;
    }
    public void setType(String type, int sprite) {
        this.type = type;
        this.sprite = sprite;
        if (type.equals("dirt")) {
            sprite1 = VectorDrawableCompat.create(context.getResources(), R.drawable.c_dirt2, null);
            sprite2 = VectorDrawableCompat.create(context.getResources(), R.drawable.c_dirt2, null);
            sprite3 = VectorDrawableCompat.create(context.getResources(), R.drawable.c_dirt2, null);
        }
        if (type.equals("grass")) {

            sprite1 = VectorDrawableCompat.create(context.getResources(), R.drawable.c_newgrass2, null);
            sprite2 = VectorDrawableCompat.create(context.getResources(), R.drawable.c_newgrass3, null);
            sprite3 = null;
        }
        if (type.equals("red")) {
            sprite1 = VectorDrawableCompat.create(context.getResources(), R.drawable.c_red, null);;
            sprite2 = null;
            sprite3 = null;
        }
        if (type.equals("blue")) {
            sprite1 = VectorDrawableCompat.create(context.getResources(), R.drawable.c_blue, null);
            sprite2 = null;
            sprite3 = null;
            this.drawable = sprite1;
        }
        if (type.equals("empty")) {
            sprite1 = VectorDrawableCompat.create(context.getResources(), R.drawable.c_empty, null);;
            sprite2 = null;
            sprite3 = null;
            this.drawable = sprite1;
        }
        if (sprite == 1) this.drawable = sprite1;
        if (sprite == 2) this.drawable = sprite2;
        if (sprite == 3) this.drawable = sprite3;
    }
}