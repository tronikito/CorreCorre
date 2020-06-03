package correcorre.blocks;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.R;

public class EmptyBlock extends Block {

    public EmptyBlock(Context c) {
        super(c);
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_empty,null);
    }
}
