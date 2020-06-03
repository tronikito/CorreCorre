package correcorre.blocks;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.R;

public class GrassBlock extends Block {

    public GrassBlock(Context c) {
        super(c);

        int random = (int) Math.floor(Math.random() * Math.floor(2));
        if (random == 0) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_grass, null);
        } else if (random == 1) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_grass1, null);
        } else if (random == 2) {
            this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_grass2, null);
        }
    }
}
