package correcorre.background;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import correcorre.graficos.MatrixX;

public class Background {

    private ArrayList<BackgroundObject> background = new ArrayList<BackgroundObject>();

    public Background(Context c, MatrixX m) {

        Mountain mountain1 = new Mountain(c,m,0,0,m.getWidth(),m.getHeight());
        Cloud cloud1 = new Cloud(c,m,300,200,1);
        Cloud cloud2 = new Cloud(c,m,300,200,2);
        Cloud cloud3 = new Cloud(c,m,300,200,3);
        Cloud cloud4 = new Cloud(c,m,500,200,4);
        //Cloud cloud5 = new Cloud(c,m,300,200,5);
        //Cloud cloud6 = new Cloud(c,m,300,200,6);
        //Cloud cloud7 = new Cloud(c,m,200,200,7);
        //Cloud cloud8 = new Cloud(c,m,200,200,8);
        Hillside hillside1 = new Hillside(c,m,0,0,m.getWidth(),m.getHeight());
        Hillside hillside2 = new Hillside(c,m,m.getWidth(),0,m.getWidth(),m.getHeight());

        //order is relevant
        background.add(cloud1);
        background.add(cloud2);
        background.add(cloud3);
        background.add(cloud4);
        //background.add(cloud5);
        //background.add(cloud6);
        //background.add(cloud7);
        //background.add(cloud8);
        background.add(mountain1);
        background.add(hillside1);
        background.add(hillside2);
    }
    public synchronized void moveBackground(int[] speed) {
        for (int x = 0; x < this.background.size(); x++) {
                BackgroundObject o = this.background.get(x);
                if (o instanceof Cloud) {
                   o.moveX(-1);
                }
                if (o instanceof Mountain) {
                    if (Math.abs(speed[0]) > 10) o.moveX(speed[0]/10);
                    o.moveX(-2);
                }
                if (o instanceof Hillside) {
                    o.moveX(-3);
                }
        }
    }
    public void printBackground(Canvas c) {
        for (int x = 0; x < this.background.size(); x++) {
            BackgroundObject o = this.background.get(x);
            o.getDrawable().draw(c);
        }

    }
}
