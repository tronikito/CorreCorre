package correcorre;

import android.annotation.SuppressLint;
import android.view.SurfaceView;

import org.json.JSONException;

import correcorre.background.Background;
import correcorre.graficos.Controls;
import correcorre.graficos.LCanvas;
import correcorre.graficos.MatrixX;
import correcorre.graficos.Scoreboard;
import correcorre.penguin.Penguin;
import correcorre.scenario.Scenario;
import correcorre.weapons.Chicken;

public class Loader extends SurfaceView implements Runnable {

    private static Main main;
    private static MatrixX matrixX = null;
    private static Controls controls = null;
    private static LCanvas canvas;
    private static Scenario scenario;
    private static Background background;
    @SuppressLint("StaticFieldLeak")
    private volatile static Penguin penguin;
    private static Scoreboard scoreboard;
    private boolean loading = false;
    private MainActivity mainActivity;
    private Thread thread;
    private boolean working = true;


    public Loader(Main m, Thread t, LCanvas l, MainActivity ma) {
        super(m.getContext());
        main = m;
        this.thread = t;
        canvas = l;
        mainActivity = ma;
    }

    public void run() {

        final int ns_s = 1000000000;
        final byte fps_objective = 5;
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
                canvas.invalidate();
                delta--;
            }
            if (System.nanoTime() - refFpsCount > ns_s) {
                refFpsCount = System.nanoTime();
                //cada milisegundo here
                update();
            }
        }
    }

    private void update() {
        if (!loading) {

            controls = new Controls(main,canvas);
            controls.generateControls();
            scenario = new Scenario(main, "ScenarioGenerado.json");
            try {
                scenario.createScenario();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (scenario.scenario != null && scenario.start != null && canvas.width != -1 && canvas.height != -1) {

            matrixX = new MatrixX(this.getContext(),canvas, main, scenario);
            matrixX.generateNewMatrix();
            background = new Background(getContext(),main,matrixX);

            if (matrixX.generateMatrix &&
                    controls.load &&
                    scenario.scenario != null) {

                int pWidth = matrixX.getSize()*2;
                int pHeight = (int) Math.round(matrixX.getSize()*2.5);
                penguin = new Penguin(main,matrixX,matrixX.getWidth()/2-(pWidth/2),(int) Math.round(matrixX.getHeight()/1.2-(pHeight+matrixX.getSize()*1.5)),pWidth, pHeight,matrixX.getSize());
                scoreboard = new Scoreboard(main,matrixX);

                background.setPenguin(penguin);
                matrixX.setPenguin(penguin);
                matrixX.setScoreboard(scoreboard);

                main.setControls(controls);
                main.setMatrixX(matrixX);
                main.setScenario(scenario);
                main.setBackground(background);
                main.setPenguin(penguin);
                main.setScoreboard(scoreboard);

                this.working = false;
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                main.iniciar(mainActivity);
                this.mainActivity.stopLoader();
            }
        }
    }
}
