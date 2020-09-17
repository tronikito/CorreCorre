package correcorre.scenario;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.R;

public class Block {

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
    private int position;
    private int solid;
    private String enemyType = null;
    private String weaponType = null;
    private String blockType = null;

    public Block(Context c, String t, int s, int p, String blockType) {
        this.c = c;
        this.type = t;
        this.sprite = s;
        this.position = p;
        if (blockType != null) {
            this.blockType = blockType;
        }
        this.finalSize = new Rect();
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

    public synchronized void setDrawable(Drawable d) {
        this.drawable = d;
    }

    public synchronized void moveX(long speed) {
        this.finalSize.left -= speed;
        this.finalSize.right -= speed;
    }
    public synchronized void moveY(long speed) {
        this.finalSize.top -= speed;
        this.finalSize.bottom -= speed;
    }

    public synchronized void animatedSprite(int tempo) {//every how many fps
        if (this.drawable == null) this.drawable = sprite1;
        if (sprite == 0) {
            if (tempo == 0) {//every 25
                if (blockType.equals("grass")) {
                    int random = (int) Math.floor(Math.random() * Math.round(5));
                    if (random == 0) this.drawable = sprite1;
                    if (random == 1) this.drawable = sprite2;
                    if (random == 2) this.drawable = sprite3;
                    if (random == 3) this.drawable = sprite4;
                    if (random == 4) this.drawable = sprite5;
                }
            }
            if (tempo%5 == 0) {//every 5
                if (blockType.equals("void")) {
                    if (tempo == 5) this.drawable = sprite2;
                    else if (tempo == 10) this.drawable = sprite3;
                    else if (tempo == 15) this.drawable = sprite4;
                    else if (tempo == 20) this.drawable = sprite5;
                    else if (tempo == 25) this.drawable = sprite1;
                    else {
                        this.drawable = sprite1;
                    }
                }
            }
        } else {
            this.drawable = sprite1;
        }
    }

    public void setSprite(int s) {
        this.sprite = s;

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
    //public String getBlockType() { return this.blockType; }
    public String getWeaponType() { return this.weaponType; }
    public String getEnemyType() {
        return this.enemyType;
    }

    public synchronized void setType(String type, String typeOf) {
        this.type = type;

        if (type.equals("enemy")) {
            this.enemyType = typeOf;
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);
        }
        if (type.equals("weapon")) {
            this.weaponType = typeOf;
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);
        }
        if (type.equals("block")) {
            this.blockType = typeOf;
            setBlockType(typeOf);
        }
    }

    public synchronized void setBlockType(String blockType) {
        this.blockType = blockType;

        if (blockType.equals("fence")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_fence, null);
        }
        if (blockType.equals("dirt")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
        }
        if (blockType.equals("dirtup")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirtup1, null);
        }
        if (blockType.equals("stoneup")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stoneup1, null);
        }
        if (blockType.equals("void")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone3, null);
            sprite4 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone4, null);
            sprite5 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone5, null);
        }
        if (blockType.equals("stone")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone3, null);
        }
        if (blockType.equals("grass")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass3, null);
            sprite4 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass5, null);
            sprite5 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_newgrass6, null);
        }
        if (blockType.equals("red")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_red, null);
        }
        if (blockType.equals("blue")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_blue, null);
        }
        if (blockType.equals("sapphire")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_sapphire1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_sapphire2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_sapphire3, null);;
        }
        if (blockType.equals("gold")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_gold1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_gold2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_gold3, null);
        }
        if (blockType.equals("redstone")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_redstone1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_redstone2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_redstone3, null);
        }
        if (blockType.equals("empty")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);
        }
        this.drawable = sprite1;
    }
}