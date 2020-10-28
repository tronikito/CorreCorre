package correcorre.scenario;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.R;

public class Block extends BlockSprites {

    public Context c;
    protected Rect finalSize;
    protected Drawable drawable;
    private Drawable blockSprite1;
    private Drawable blockSprite2;
    private Drawable blockSprite3;
    private Drawable blockSprite4;
    private Drawable blockSprite5;

    private String type;
    private int sprite;
    private int position;
    private int solid;
    private String enemyType = null;
    private String weaponType = null;
    private String blockType = null;

    public Block(Context c, String t, int s, int p, String blockType) {
        super(c);
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
        if (this.drawable != null) {
            this.drawable.setBounds(finalSize);
        }
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
        if (this.drawable == null) this.drawable = blockSprite1;
        if (sprite == 0) {
            if (tempo == 0) {//every 25
                if (blockType.equals("grass")) {
                    int random = (int) Math.floor(Math.random() * Math.round(5));
                    if (random == 0) this.drawable = blockSprite1;
                    if (random == 1) this.drawable = blockSprite2;
                    if (random == 2) this.drawable = blockSprite3;
                    if (random == 3) this.drawable = blockSprite4;
                    if (random == 4) this.drawable = blockSprite5;
                }
            }
            if (tempo % 5 == 0) {//every 5
                if (blockType.equals("void")) {
                    if (tempo == 5) this.drawable = blockSprite2;
                    else if (tempo == 10) this.drawable = blockSprite3;
                    else if (tempo == 15) this.drawable = blockSprite4;
                    else if (tempo == 20) this.drawable = blockSprite5;
                    else if (tempo == 25) this.drawable = blockSprite1;
                    else {
                        this.drawable = blockSprite1;
                    }
                }
            }
        } else {
            this.drawable = blockSprite1;
        }
    }

    public void setSprite(int s) {
        this.sprite = s;

        if (sprite == 1) if (blockSprite1 != null) this.drawable = blockSprite1;
        if (sprite == 2) if (blockSprite2 != null) this.drawable = blockSprite2;
        if (sprite == 3) if (blockSprite3 != null) this.drawable = blockSprite3;
        if (sprite == 4) if (blockSprite4 != null) this.drawable = blockSprite4;
        if (sprite == 5) if (blockSprite5 != null) this.drawable = blockSprite5;

        if (sprite > 5) this.drawable = blockSprite1;
        if (this.drawable == null) this.drawable = blockSprite1;
    }

    public String getType() {
        return this.type;
    }

    //public String getBlockType() { return this.blockType; }
    public String getWeaponType() {
        return this.weaponType;
    }

    public String getEnemyType() {
        return this.enemyType;
    }

    public synchronized void setType(String type, String typeOf) {
        this.type = type;

        if (type.equals("enemy")) {
            this.enemyType = typeOf;
            blockSprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);
        }
        if (type.equals("weapon")) {
            this.weaponType = typeOf;
            blockSprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);
        }
        if (type.equals("block")) {
            this.blockType = typeOf;
            setBlockType(typeOf);
        }
    }

    public synchronized void setBlockType(String blockType) {

        blockSprite1 = sprite41;
        blockSprite2 = null;
        blockSprite3 = null;
        blockSprite4 = null;
        blockSprite5 = null;

        if (blockType.equals("fence")) {
            blockSprite1 = sprite1;
        }
        else if (blockType.equals("dirt")) {
            if (sprite == 1) blockSprite1 = sprite42;
            if (sprite == 2) blockSprite1 = sprite43;
            if (sprite == 3) blockSprite1 = sprite44;
            if (sprite == 4) blockSprite1 = sprite45;
            if (sprite == 5) blockSprite1 = sprite46;
            if (sprite == 6) blockSprite1 = sprite47;
            if (sprite == 7) blockSprite1 = sprite48;
            if (sprite == 8) blockSprite1 = sprite49;
            if (sprite == 9) blockSprite1 = sprite50;
            if (sprite == 10) blockSprite1 = sprite51;
            if (sprite == 11) blockSprite1 = sprite52;
            if (sprite == 12) blockSprite1 = sprite53;
            if (sprite == 13) blockSprite1 = sprite54;
            if (sprite == 14) blockSprite1 = sprite55;
            if (sprite == 15) blockSprite1 = sprite56;
            if (sprite == 16) blockSprite1 = sprite57;
            if (sprite == 17) blockSprite1 = sprite58;
            if (sprite == 18) blockSprite1 = sprite59;
            if (sprite == 19) blockSprite1 = sprite64;
            if (sprite == 20) blockSprite1 = sprite65;

        }
        else if (blockType.equals("void")) {
            blockSprite1 = sprite5;
            blockSprite2 = sprite6;
            blockSprite3 = sprite7;
            blockSprite4 = sprite8;
            blockSprite5 = sprite9;
        }
        else if (blockType.equals("stone")) {
            if (sprite == 1) blockSprite1 = sprite10;
            if (sprite == 2) blockSprite1 = sprite11;
            if (sprite == 3) blockSprite1 = sprite12;
            if (sprite == 4) blockSprite1 = sprite13;
            if (sprite == 5) blockSprite1 = sprite14;
            if (sprite == 6) blockSprite1 = sprite15;
            if (sprite == 7) blockSprite1 = sprite16;
            if (sprite == 8) blockSprite1 = sprite17;
            if (sprite == 9) blockSprite1 = sprite18;
            if (sprite == 10) blockSprite1 = sprite19;
            if (sprite == 11) blockSprite1 = sprite20;
            if (sprite == 12) blockSprite1 = sprite21;
            if (sprite == 13) blockSprite1 = sprite22;
            if (sprite == 14) blockSprite1 = sprite23;
            if (sprite == 15) blockSprite1 = sprite24;
            if (sprite == 16) blockSprite1 = sprite60;
            if (sprite == 17) blockSprite1 = sprite61;
            if (sprite == 18) blockSprite1 = sprite62;
            if (sprite == 19) blockSprite1 = sprite63;
            if (sprite == 20) blockSprite1 = sprite66;
        }
        else if (blockType.equals("grass")) {
            blockSprite1 = sprite25;
            blockSprite2 = sprite26;
            blockSprite3 = sprite27;
            blockSprite4 = sprite28;
            blockSprite5 = sprite29;
        }
        else if (blockType.equals("red")) {
            blockSprite1 = sprite30;
        }
        else if (blockType.equals("blue")) {
            blockSprite1 = sprite31;
        }
        else if (blockType.equals("sapphire")) {
            if (sprite == 1) blockSprite1 = sprite32;
            if (sprite == 2) blockSprite1 = sprite33;
            if (sprite == 3) blockSprite1 = sprite34;
        }
        else if (blockType.equals("gold")) {
            if (sprite == 1) blockSprite1 = sprite35;
            if (sprite == 2) blockSprite1 = sprite36;
            if (sprite == 3) blockSprite1 = sprite37;
        }
        else if (blockType.equals("redstone")) {
            if (sprite == 1) blockSprite1 = sprite38;
            if (sprite == 2) blockSprite1 = sprite39;
            if (sprite == 3) blockSprite1 = sprite40;
        }
        else if (blockType.equals("empty")) {
            blockSprite1 = sprite41;
        }
        this.drawable = blockSprite1;
    }

/*
    public synchronized void setBlockType(String blockType) {
        this.blockType = blockType;

        if (blockType.equals("fence")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_fence, null);
        }
        if (blockType.equals("dirt")) {
            if (sprite == 1) sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt1, null);
            if (sprite == 2) sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
            if (sprite == 3) sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt3, null);
        }
        if (blockType.equals("void")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone1, null);
            sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone2, null);
            sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone3, null);
            sprite4 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone4, null);
            sprite5 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_voidstone5, null);
        }
        if (blockType.equals("stone")) {
            if (sprite == 1) sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone1, null);
            if (sprite == 2) sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone2, null);
            if (sprite == 3) sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone3, null);
            if (sprite == 4) sprite4 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone4, null);
            if (sprite == 5) sprite5 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone5, null);
            if (sprite == 6) sprite6 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone6, null);
            if (sprite == 7) sprite7 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone7, null);
            if (sprite == 8) sprite8 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone8, null);
            if (sprite == 9) sprite9 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone9, null);
            if (sprite == 10) sprite10 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone10, null);
            if (sprite == 11) sprite11 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone11, null);
            if (sprite == 12) sprite12 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone12, null);
            if (sprite == 13) sprite13 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone13, null);
            if (sprite == 14) sprite14 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_stone14, null);
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
            if (sprite == 1) sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_sapphire1, null);
            if (sprite == 2) sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_sapphire2, null);
            if (sprite == 3) sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_sapphire3, null);;
        }
        if (blockType.equals("gold")) {
            if (sprite == 1) sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_gold1, null);
            if (sprite == 2) sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_gold2, null);
            if (sprite == 3) sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_gold3, null);
        }
        if (blockType.equals("redstone")) {
            if (sprite == 1) sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_redstone1, null);
            if (sprite == 2) sprite2 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_redstone2, null);
            if (sprite == 3) sprite3 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_redstone3, null);
        }
        if (blockType.equals("empty")) {
            sprite1 = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty, null);
        }
        setSprite(sprite);
        //this.drawable = sprite1;
    }*/
}