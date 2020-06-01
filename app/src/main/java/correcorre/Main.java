package correcorre;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceView;

import correcorre.graficos.MyCanvas;
import correcorre.scenario.Scenario;

public class Main extends SurfaceView implements Runnable {

    private volatile int[] speed = new int[] {9,9};
    private static boolean working = false;
    private volatile boolean firstTime = true;
    private Thread start;
    private static int fps = 0;
    private static correcorre.graficos.MyCanvas myCanvas;
    private static Controls controls = null;
    private static MatrixX matrixX = null;
    private static Scenario scenario = null;
    private static Main main;

    public Main(Context context) {
        super(context);
        main = this;
    }

    public void run() {

        final int ns_s = 1000000000;
        final byte fps_objective = 60;
        final double ns_fps = ns_s / fps_objective;
        long refFps = System.nanoTime();
        long refFpsCount = System.nanoTime();
        double timeTrans;
        double delta = 0;

        while (working) {
            final long initWhile = System.nanoTime();
            timeTrans = initWhile - refFps;
            refFps = initWhile;
            delta += timeTrans / ns_fps;

            while (delta >= 1) {
                update();//every fps
                delta--;
            }
            if (System.nanoTime() - refFpsCount > ns_s) {//every 60fps
                //System.out.println(this.fps);
                fps = 0;
                refFpsCount = System.nanoTime();
            }
        }
    }

    private void update() {//#################### RUN ACTIONS
        if (this.firstTime) {
            this.firstTime = false;
        } else {
            matrixX.moveMatrix(this.speed);
            matrixX.checkOffset();
        }
        myCanvas.invalidate();
        this.fps++;
    }

    public void iniciar(MainActivity mainActivity) {
        this.working = true;
        myCanvas = new MyCanvas(main.getContext(), main, matrixX, controls);
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new setView(main, mainActivity, myCanvas) {
        });
        this.start = new Thread(this, "Graficos");
        this.start.start();
    }

    public synchronized void detener() {
        this.working = false;
        try {
            this.start.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /** GETTERS Y SETTERS */

    public void setControls(Controls c) {
        controls = c;
    }
    public void setMatrixX(MatrixX m) {
        matrixX = m;
    }
    public void setScenario(Scenario s) { scenario = s; };
    public void setSpeed(int[] s) {
        this.speed = s;
    }
    public int[] getSpeed() { return this.speed; }
}

/**
 * //Handler mainThread = new Handler(Looper.getMainLooper());
 * //mainThread.post(new FpsRunnable(this.fpsText,fps) {});
 */

