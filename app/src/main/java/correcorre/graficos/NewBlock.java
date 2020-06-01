package correcorre.graficos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class NewBlock {

    protected Drawable drawable;

    public NewBlock(Context c, int sprite) {
        this.drawable = VectorDrawableCompat.create(c.getResources(), sprite,null);
    }
    public Drawable getNewDrawable() {
        return this.drawable;
    }
}