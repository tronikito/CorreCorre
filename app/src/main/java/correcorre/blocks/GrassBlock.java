package correcorre.blocks;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;

public class GrassBlock extends Block {

    int frame = 0;
    private static Main main;
    public GrassBlock(Context c, Main main) {
        super(c);
        generateDrawable();
        //this.drawable = VectorDrawableCompat.create(c.getResources(), R.drawable.c_grass1, null);
    }

    private void generateDrawable() {
        if (main.fps < 30) {
            this.drawable = VectorDrawableCompat.create(this.context.getResources(), R.drawable.c_grass1, null);
        } else if (main.fps >= 30) {
            this.drawable = VectorDrawableCompat.create(this.context.getResources(), R.drawable.c_grass2, null);
        } else if (main.fps == 99) {
            this.drawable = VectorDrawableCompat.create(this.context.getResources(), R.drawable.c_grass3, null);
        }
    }
    @Override
    public synchronized Drawable getDrawable() {
        generateDrawable();
        this.drawable.setBounds(finalSize);
        return drawable;
    }
}