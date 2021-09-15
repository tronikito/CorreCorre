package correcorrerework.game.background;

import static correcorrerework.ResourcesClass.*;

public class Tree extends BackgroundObject {
    public Tree(int w, int h) {

        //Rect
        this.r.left = widthScreen;
        this.r.right = this.r.left + w;
        this.r.top = (heightScreen - h);
        this.r.bottom = this.r.top + h;

        //Drawable
        int random = (int) Math.floor(Math.random() * Math.floor(2));

        if (random == 0) {
           // this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.c_grass3, null);
        } else if (random == 1) {
           // this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.c_grass1, null);
        } else if (random == 2) {
            //this.d = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.c_grass2, null);
        }
    }
}
