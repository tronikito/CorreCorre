package correcorre.blocks;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.R;

public class RedBlock extends Block {

    public RedBlock(Context c) {
        super(c);
        this.drawable = VectorDrawableCompat.create(this.context.getResources(), R.drawable.c_rojo, null);
    }
}
