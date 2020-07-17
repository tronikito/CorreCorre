package correcorre.scenario;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;

public class Block {

    private static Main main;
    public Context c;
    protected Rect finalSize;
    protected Drawable drawable;
    private Drawable sprite1;
    private Drawable sprite2;
    private Drawable sprite3;
    private Drawable sprite4;
    private Drawable sprite5;
    private String type;
    private int sprite;
    private int tempo = 180;
    private int toTempo = 0;
    private int position;
    private int solid;
    private int enemyType = 0;
    private int weaponType = 0;

    public Block(Context c, String t, int s, int p) {
        this.c = c;
        this.type = t;
        this.sprite = s;
        this.position = p;
        this.finalSize = new Rect();
    }
    public int getWeaponType() {
        return this.weaponType;
    }
    public void setWeaponType(int e) {
        this.weaponType = e;
    }
    public int getEnemyType() {
        return this.enemyType;
    }
    public void setEnemyType(int e) {
       this.enemyType = e;
    }
    public int getSolid() {
        return this.solid;
    }

    public void setSolid(int s) {
        this.solid = s;
    }

    public void setPosition(int p) {
        this.position = p;
    }
    public int getPosition() {
        return this.position;
    }
    public int getSprite() {
        return this.sprite;
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

    public void animatedSprite() {//every how many fps
        if (this.drawable == null) this.drawable = sprite1;
        this.toTempo++;
        if (type.equals("grass")) {

            //secuencial
            //if (toTempo < 60) this.drawable = sprite1;
            //if (toTempo >= 60 && toTempo < 120) if (sprite2 != null) this.drawable = sprite2;
            //if (toTempo >= 120) if (sprite3 != null) this.drawable = sprite3;

            //random
            if (toTempo == 1) {
                int random = (int) Math.floor(Math.random() * Math.round(5));
                if (random == 0) this.drawable = sprite1;
                if (random == 1) this.drawable = sprite2;
                if (random == 2) this.drawable = sprite3;
                if (random == 3) this.drawable = sprite4;
                if (random == 4) this.drawable = sprite5;
                toTempo = 160;
            }
        }
        else {
            this.drawable = sprite1;
        }
        if (toTempo >= tempo) toTempo = 0;
    }

    public void setSprite(int s) {
        this.sprite = s;
        if (sprite == 0) animatedSprite();
        if (sprite == 1) this.drawable = sprite1;
        if (sprite == 2) if (sprite2 != null) this.drawable = sprite2;
        if (sprite == 3) if (sprite3 != null) this.drawable = sprite3;
        if (sprite == 4) if (sprite4 != null) this.drawable = sprite4;
        if (sprite == 5) if (sprite5 != null) this.drawable = sprite5;
        if (this.drawable == null) this.drawable = sprite1;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type, int sprite) {
        this.type = type;
        this.sprite = sprite;
        if (type.equals("fence")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_fence, null);
        }
        if (type.equals("dirt")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
        }
        if (type.equals("grass")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass3, null);
            sprite4 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass5, null);
            sprite5 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass6, null);
        }
        if (type.equals("red")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_red, null);;
            sprite2 = null;
            sprite3 = null;
        }
        if (type.equals("blue")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_blue, null);
            sprite2 = null;
            sprite3 = null;
            this.drawable = sprite1;
        }
        if (type.equals("empty")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);;
            sprite2 = null;
            sprite3 = null;
            this.drawable = sprite1;
        }
        if (type.equals("enemy")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);;
            sprite2 = null;
            sprite3 = null;
            this.drawable = sprite1;
        }
        if (type.equals("weapon")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);;
            sprite2 = null;
            sprite3 = null;
            this.drawable = sprite1;
        }

        setSprite(sprite);
    }
}