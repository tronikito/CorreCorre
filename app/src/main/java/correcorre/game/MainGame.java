package correcorre.game;

import correcorre.game.graficos.MoveThings;

import static correcorre.ResourcesClass.*;

public class MainGame implements Runnable {

    public boolean penguinX;
    public boolean penguinY;
    public boolean outRangeTop;
    public boolean outRangeBottom;

    public MainGame() {
        mainGame = this;
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

        while (!gameStopped) {
            final long initWhile = System.nanoTime();
            timeTrans = initWhile - refFps;
            refFps = initWhile;
            delta += timeTrans / ns_fps;

            while (delta >= 1) {

                double realFps = fps_objective - 1 / delta;

                int[] newSpeed = calcRealSpeed(penguinSpeed, realFps, fps_objective, actualFpsRefX, actualFpsRefY);

                if (enemyList != null) {
                    for (int x = 0; x < enemyList.size(); x++) {
                        enemyList.get(x).setActualSpeed(calcRealSpeed(enemyList.get(x).getSpeed(), realFps, fps_objective, actualFpsRefX, actualFpsRefY));
                    }
                }
                if (bulletList != null) {
                    for (int x = 0; x < bulletList.size(); x++) {
                        bulletList.get(x).setActualSpeed(calcRealSpeed(bulletList.get(x).getSpeed(), realFps, fps_objective, actualFpsRefX, actualFpsRefY));
                    }
                }
                update(newSpeed, realFps);//every fps
                fps++;


                delta--;

                if (System.nanoTime() - refFpsCount > ns_s) {//every 60fps

                    actualFpsRefX = 1;
                    actualFpsRefY = 1;

                    //scoreboard.scoreFps(fps); //usar scoreboard fpsMonitor
                    fps = 0;
                    refFpsCount = System.nanoTime();
                }
            }
        }
    }

    private synchronized int[] calcRealSpeed(int[] speed, double realFps, byte fps_objective, int actualFpsRefX, int actualFpsRefY) {
        int speedX = 0;
        int speedY = 0;

        speedX = speed[0] / (fps_objective - 1);
        int newSpeedXmod = Math.abs(penguinSpeed[0] % fps_objective);
        float speedFpsRefX = calcSpeedFps(newSpeedXmod, realFps);
        if (speedFpsRefX * actualFpsRefX <= fps && speedFpsRefX >= 1) {
            if (speed[0] > 0) speedX += 1;
            if (penguinSpeed[0] < 0) speedX -= 1;
            actualFpsRefX++;
        }

        speedY = speed[1] / (fps_objective - 1);
        int newSpeedYmod = Math.abs(speed[1] % fps_objective);
        float speedFpsRefY = calcSpeedFps(newSpeedYmod, realFps);
        if (speedFpsRefY * actualFpsRefY <= fps && speedFpsRefY >= 1) {
            if (speed[1] > 0) speedY += 1;
            if (speed[1] < 0) speedY -= 1;
            actualFpsRefY++;
        }
        return new int[]{speedX, speedY};
    }

    private synchronized float calcSpeedFps(int speed, double realFps) {
        float speedFpsRef = 0;
        if (speed < realFps) speedFpsRef = (int) Math.round(realFps / speed);
        if (speed >= realFps)
            speedFpsRef = (float) realFps / speed;
        return speedFpsRef;
    }

    private synchronized void update(int[] speed, double realFps) {//#################### RUN ACTIONS

        penguinX = penguin.getPosX() < widthScreen * 0.4 && penguin.getrLeft() ||
                penguin.getrRight() && penguin.getPosX() > widthScreen * 0.39;

        penguinY = true;

        outRangeTop = penguin.getPosY() < heightScreen * 0.2;
        outRangeBottom = penguin.getPosY() + sizeDot * 1.5 > heightScreen * 0.8;

        if (outRangeBottom && speed[1] > 0) penguinY = false;
        if (outRangeTop && speed[1] < 0) penguinY = false;

        penguin.checkCollision();
        penguin.calcAcceleration();
        penguin.movePenguinSprite(speed);
        penguin.movePenguinPos(speed);
        penguin.shooting();

        MoveThings.calcMoves(); //enemys/bullets/sprites

        matrixX.moveMatrix(speed); //move matrixX and objects depends on it, like enemys/bullets
        //frontground.moveBackground(speed); //call from matrixX

        background.moveBackground(speed);

        myCanvas.checkControls();

        myCanvas.invalidate(); //print everything

    }
}

