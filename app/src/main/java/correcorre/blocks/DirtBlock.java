package correcorre.blocks;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.R;

public class DirtBlock extends Block {

    public DirtBlock(Context c) {
        super(c);

        int random = (int) Math.floor(Math.random() * Math.floor(3));
        if (random == 0) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
        } else if (random == 1) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
        } else if (random == 2) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_dirt2, null);
        }
    }
}
