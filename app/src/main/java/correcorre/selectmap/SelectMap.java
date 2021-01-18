package correcorre.selectmap;

import static correcorre.ResourcesClass.*;

public class SelectMap implements Runnable {

    public SelectMap() {
        selectMap = this;
    }

    public synchronized void run() {

        final int ns_s = 1000000000;
        final byte fps_objective = 60;
        final double ns_fps = ns_s / fps_objective;
        long refFps = System.nanoTime();
        long refFpsCount = System.nanoTime();
        double timeTrans;
        double delta = 0;

        while (!selectMapStopped) {

            final long initWhile = System.nanoTime();
            timeTrans = initWhile - refFps;
            refFps = initWhile;
            delta += timeTrans / ns_fps;

            while (delta >= 1) {
                update();//every fps
                fps++;
                delta--;
            }
            if (System.nanoTime() - refFpsCount > ns_s) {//every 60fps
                fps = 0;
                refFpsCount = System.nanoTime();
            }
        }
    }

    private synchronized void update() {//#################### RUN ACTIONS
        mapCanvas.invalidate(); //print everything

    }
}

