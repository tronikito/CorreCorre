package correcorre.graficos;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

//JUST A TYPE INFORMATION BLOCK

public class ScenarioBlock extends Block {

    private Drawable drawable;
    private String type;

    public ScenarioBlock(Context c,String type) {
        super(c);
        //this.drawable = VectorDrawableCompat.create(c.getResources(), sprite,null);
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public Rect getRect() {
        throw new java.lang.RuntimeException("Cannot get Rect of ScenarioBlock");
    }
    public Drawable getDrawable() {
        throw new java.lang.RuntimeException("Cannot get Drawable of ScenarioBlock");
    }
    public void setDrawable(Drawable d) {
        throw new java.lang.RuntimeException("Cannot change ScenarioBlock");
    }
    public void setRect(Rect r) {
        throw new java.lang.RuntimeException("Cannot change ScenarioBlock");
    }
    public void moveX(float speed) {
        throw new java.lang.RuntimeException("Cannot move ScenarioBlock");
    }
    public void moveY(float speed) {
        throw new java.lang.RuntimeException("Cannot move ScenarioBlock");
    }
    public void setEmpty() {
        throw new java.lang.RuntimeException("Cannot change ScenarioBlock");
    }

}