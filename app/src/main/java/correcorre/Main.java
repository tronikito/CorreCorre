package correcorre;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceView;
import correcorre.background.Background;
import correcorre.graficos.Controls;
import correcorre.graficos.MatrixX;
import correcorre.graficos.MyCanvas;
import correcorre.graficos.Scoreboard;
import correcorre.penguin.Penguin;
import correcorre.scenario.Scenario;

public class Main extends SurfaceView implements Runnable {

    private int[] speed = new int[] {0,0};
    private static boolean working = false;
    private volatile boolean firstTime = true;
    private Thread start;
    public static int fps = 0;
    private static correcorre.graficos.MyCanvas myCanvas;
    private static Controls controls = null;
    private static MatrixX matrixX = null;
    private static Scenario scenario = null;
    private static Main main;
    private static Background background;
    private static Penguin penguin;
    private static Scoreboard scoreboard;
    public boolean penguinX;
    public boolean penguinY;
    public boolean outRangeTop;
    public boolean outRangeBottom;

    public Main(Context context) {
        super(context);
        main = this;
    }

    public synchronized void run() {

        final int ns_s = 1000000000;
        final byte fps_objective = 60;
        final double ns_fps = ns_s / fps_objective;
        long refFps = System.nanoTime();
        long refFpsCount = System.nanoTime();
        double timeTrans;
        double delta = 0;

        int actualFpsRefX = 1;
        int actualFpsRefY = 1;

        while (working) {

            final long initWhile = System.nanoTime();
            timeTrans = initWhile - refFps;
            refFps = initWhile;
            delta += timeTrans / ns_fps;

            int speedX = 0;
            int speedY = 0;

            while (delta >= 1) {

                /*
                penguin.checkColission();
                penguin.calcAceleration();
                matrixX.calcEnemyMove();
                matrixX.calcBulletMove(); //only colision
                matrixX.randomControlsEnemys();
                */


                if (penguin != null) main.setSpeed(penguin.getSpeed());//evitar desfase;

                double realFps = fps_objective-1/delta;

                int[] newSpeed = calcRealSpeed(this.speed, realFps, fps_objective, actualFpsRefX, actualFpsRefY);
                if (matrixX.enemyList != null) {
                    for (int x = 0; x < matrixX.enemyList.size(); x++) {
                        matrixX.enemyList.get(x).setActualSpeed(calcRealSpeed(matrixX.enemyList.get(x).getSpeed(), realFps, fps_objective, actualFpsRefX, actualFpsRefY));
                    }
                }
                if (matrixX.bulletList != null) {
                    for (int x = 0; x < matrixX.bulletList.size(); x++) {
                        matrixX.bulletList.get(x).setActualSpeed(calcRealSpeed(matrixX.bulletList.get(x).getSpeed(), realFps, fps_objective, actualFpsRefX, actualFpsRefY));
                    }
                }

                update(newSpeed,realFps);//every fps
                delta--;
            }
            if (System.nanoTime() - refFpsCount > ns_s) {//every 60fps

                actualFpsRefX = 1;
                actualFpsRefY = 1;
                fps = 0;
                refFpsCount = System.nanoTime();
            }
        }
    }
    private synchronized int[] calcRealSpeed(int [] speed, double realFps, byte fps_objective, int actualFpsRefX, int actualFpsRefY) {
        int speedX = 0;
        int speedY = 0;

        speedX = speed[0]/(fps_objective-1);
        int newSpeedXmod = Math.abs(this.speed[0]%fps_objective);
        float speedFpsRefX = calcSpeedFps(newSpeedXmod,realFps);
        if (speedFpsRefX * actualFpsRefX <= fps && speedFpsRefX >= 1) {
            if (speed[0] > 0) speedX += 1;
            if (this.speed[0] < 0) speedX -= 1;
            actualFpsRefX++;
        }

        speedY = speed[1]/(fps_objective-1);
        int newSpeedYmod = Math.abs(speed[1]%fps_objective);
        float speedFpsRefY = calcSpeedFps(newSpeedYmod,realFps);
        if (speedFpsRefY * actualFpsRefY <= fps && speedFpsRefY >= 1) {
            if (speed[1] > 0) speedY += 1;
            if (speed[1] < 0) speedY -= 1;
            actualFpsRefY++;
        }
        int[] newSpeed = {speedX,speedY};
        return newSpeed;
    }

    private synchronized float calcSpeedFps(int speed, double realFps) {
        float speedFpsRef = 0;
        if (speed < realFps) speedFpsRef = (int) Math.round(realFps/speed);
        if (speed >= realFps) speedFpsRef = (float) realFps/speed; // WHY????????????????????????????????????????????????????????????????????????
        return speedFpsRef;
    }

    private synchronized void update(int[] speed, double realFps) {//#################### RUN ACTIONS

        if (this.firstTime) {
            this.firstTime = false;
        } else {

            if (penguin.getPosX() < matrixX.getWidth() * 0.4 && penguin.getrLeft() || penguin.getrRight() && penguin.getPosX() > matrixX.getWidth() * 0.39) {
                penguinX = true;
            } else {
                penguinX = false;
            }

            penguinY = true;
            if (penguin.getPosY() < matrixX.getHeight() * 0.2) outRangeTop = true;
            else outRangeTop = false;
            if (penguin.getPosY() + matrixX.getSize() * 1.5 > matrixX.getHeight() * 0.8)
                outRangeBottom = true;
            else outRangeBottom = false;

            if (outRangeBottom && speed[1] > 0) {
                penguinY = false;
            }
            if (outRangeTop && speed[1] < 0) {
                penguinY = false;
            }

            penguin.checkColission();
            penguin.calcAceleration();
            penguin.movePenguinSprite(speed);
            penguin.movePenguinPos(speed);
            penguin.shooting();

            matrixX.calcBulletMove();
            matrixX.calcEnemyMove();
            matrixX.calcExplosionSprite();

            matrixX.moveMatrix(speed);

            background.moveBackground(speed);

        }
        myCanvas.invalidate();
        this.fps++;
    }

    public void iniciar(MainActivity mainActivity) {
        this.working = true;
        myCanvas = new MyCanvas(main, matrixX, controls, background, penguin, scoreboard);
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

    public void setScoreboard(Scoreboard s) { scoreboard = s; }
    public void setControls(Controls c) {
        controls = c;
    }
    public void setMatrixX(MatrixX m) {
        matrixX = m;
    }
    public void setScenario(Scenario s) { scenario = s; };
    public void setBackground(Background b) { background = b; }
    public void setPenguin(Penguin p) { penguin = p; }
    public Penguin getPenguin() {
        return penguin;
    }
    public void setSpeed(int[] s) {
        this.speed = s;
    }
    public int[] getSpeed() { return this.speed; }
}

/**
 * //Handler mainThread = new Handler(Looper.getMainLooper());
 * //mainThread.post(new FpsRunnable(this.fpsText,fps) {});
 */

