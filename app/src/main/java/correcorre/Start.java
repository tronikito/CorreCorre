package correcorre;

import android.content.Context;
import android.view.SurfaceView;

import correcorre.graficos.MyCanvas;

public class Start extends SurfaceView implements Runnable {

    public float speed;
    private static boolean working = false;
    private volatile boolean firstTime = true;
    private Thread start;
    private static int fps = 0;
    private MyCanvas MyCanvas;
    private Controls controls;
    private MatrixX matrixX;

    public Start(Context context, MyCanvas MyCanvas) {
        super(context);
        this.speed = 1f;
        this.MyCanvas = MyCanvas;
        //this.controls = new Controls(context);
        //this.MyCanvas.setStart(main);
    }

    public void run() {

        final int ns_s = 1000000000;
        final byte fps_objective = 20;
        final double ns_fps = ns_s / fps_objective;
        long refFps = System.nanoTime();
        long refFpsCount = System.nanoTime();
        double timeTrans;
        double delta = 0;

        while (working) {//##########################################
            final long initWhile = System.nanoTime();
            timeTrans = initWhile - refFps;
            refFps = initWhile;
            delta += timeTrans / ns_fps;

            while (delta >= 1) {
                update();
                delta--;
            }
            if (System.nanoTime() - refFpsCount > ns_s) {
                System.out.println(this.fps);
                fps = 0;
                refFpsCount = System.nanoTime();
            }
        }
    }

    private void update() {
        if (this.firstTime) {
            this.firstTime = false;
            this.MyCanvas.invalidate();
            this.controls.generateControls();
        }
        if (this.matrixX != null) {
            //this.matrixX.moveMatrix(this.speed);
        }
        this.MyCanvas.invalidate();
        this.fps++;
    }

    public void setMatrixX(MatrixX m) {
        this.matrixX = m;
    }

    public synchronized void iniciar() {
        this.working = true;
        this.start = new Thread(this, "Graficos");
        this.start.start();
    }

    private synchronized void detener() {
        this.working = false;
        try {
            this.start.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


