package correcorre.background;

import android.content.Context;
import java.util.ArrayList;
import correcorre.graficos.MatrixX;

public class Background {

    private ArrayList<BackgroundObject> background = new ArrayList<BackgroundObject>();

    public Background(Context c, MatrixX m) {

        Mountain mountain1 = new Mountain(c,m,m.getWidth(),m.getHeight());
    }
    public synchronized void moveBackground(int[] speed) {
        for (int x = 0; x < this.background.size(); x++) {
                BackgroundObject o = this.background.get(x);
                o.moveX(speed[0]);
                o.moveY(speed[1]);
        }
    }
}
