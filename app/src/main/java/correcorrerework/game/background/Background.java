package correcorrerework.game.background;

import android.graphics.Canvas;

import java.util.ArrayList;

import static correcorrerework.ResourcesClass.*;

public class Background {

    public Background() {

        backgroundList = new ArrayList<BackgroundObject>();

        Mountain mountain1 = new Mountain(0,0,widthScreen,heightScreen);
        Cloud cloud1 = new Cloud(300,200,1);
        Cloud cloud2 = new Cloud(300,200,2);
        Cloud cloud3 = new Cloud(300,200,3);
        Cloud cloud4 = new Cloud(500,200,4);

        Hillside hillside1 = new Hillside(0,0,widthScreen,heightScreen);
        Hillside hillside2 = new Hillside(widthScreen,0,widthScreen,heightScreen);

        //order is relevant
        backgroundList.add(cloud1);
        backgroundList.add(cloud2);
        backgroundList.add(cloud3);
        backgroundList.add(cloud4);

        backgroundList.add(mountain1);
        backgroundList.add(hillside1);
        backgroundList.add(hillside2);
    }

    public synchronized void moveBackground(int[] speed) {

        if (!mainGame.penguinX) {
            for (int x = 0; x < backgroundList.size(); x++) {
                BackgroundObject o = backgroundList.get(x);
                int resultSpeed = 0;
                if (penguinSpeed[0] > 150) resultSpeed = -1;
                if (penguinSpeed[0] < -150) resultSpeed = 1;
                if (penguinSpeed[0] <= 150 && penguinSpeed[0] >= -150) resultSpeed = 0;

                if (o instanceof Cloud) {
                    o.moveX(1 * resultSpeed);
                }
                if (o instanceof Mountain) {
                    if (Math.abs(speed[0]) > 10) o.moveX(speed[0]/10);
                    o.moveX(2 * resultSpeed);
                }
                if (o instanceof Hillside) {
                    o.moveX(3 * resultSpeed);
                }
            }
        }

    }

    public synchronized void printBackground(Canvas c) {
        for (int x = 0; x < backgroundList.size(); x++) {
            backgroundList.get(x).getDrawable().draw(c);
        }

    }
}
