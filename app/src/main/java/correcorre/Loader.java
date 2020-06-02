package correcorre;

import android.view.SurfaceView;

import org.json.JSONException;

import correcorre.graficos.LCanvas;
import correcorre.scenario.Scenario;

public class Loader extends SurfaceView implements Runnable {

    private static Main main;
    private static MatrixX matrixX = null;
    private static Controls controls = null;
    private static LCanvas canvas;
    private static Scenario scenario;
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
            controls = new Controls(canvas, main);
            controls.generateControls();
            scenario = new Scenario(main, "ScenarioNuevo.json");
            try {
                scenario.createScenario();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (scenario.scenario != null && scenario.start != null && canvas.width != -1 && canvas.height != -1) {

            matrixX = new MatrixX(canvas, main, scenario);
            matrixX.generateNewMatrix();

            if (matrixX != null &&
                    controls.load &&
                    scenario.scenario != null) {

                main.setControls(controls);
                main.setMatrixX(matrixX);
                main.setScenario(scenario);

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
