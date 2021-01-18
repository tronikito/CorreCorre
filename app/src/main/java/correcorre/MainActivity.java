package correcorre;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import correcorre.game.MainGame;
import correcorre.selectmap.MapCanvas;
import correcorre.selectmap.MapMatrixX;
import correcorre.selectmap.SelectMap;
import correcorre.game.background.Background;
import correcorre.game.background.Frontground;
import correcorre.game.graficos.Controls;
import correcorre.game.graficos.MatrixX;
import correcorre.game.graficos.MyCanvas;
import correcorre.game.graficos.Scoreboard;
import correcorre.game.penguin.Penguin;
import correcorre.game.scenario.Scenario;
import correcorre.R;

import static correcorre.ResourcesClass.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reset for preview game reset
        reset();

        gameStopped = true;//stop looper runnable
        selectMapStopped = true;//stop looper runnable

        //start calling things
        mactivity = this;
        HideBar.toggleHideyBar(mactivity);

        ImageView logo = findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectMap();
            }
        });
    }

    private void startSelectMap() {

        LinearLayout mainLayout = findViewById(R.id.mainLayout); //take size screen
        widthScreen = mainLayout.getMeasuredWidth();
        heightScreen = mainLayout.getMeasuredHeight();

        selectMapStopped = false;

        mapMatrixX = new MapMatrixX();
        mapCanvas = new MapCanvas();
        startSelectMapThread();
    }

    public void startGame() {

        gameStopped = false;
        scenario = new Scenario("ScenarioGenerado.json");

        if (matrixScenario != null) {

            controls = new Controls();
            myCanvas = new MyCanvas();
            background = new Background();
            frontground = new Frontground();
            scoreboard = new Scoreboard();
            matrixX = new MatrixX();
            penguin = new Penguin();

        }
        startMainGameThread();
        stopSelectMapThread();
    }

    private void startMainGameThread() {

        GameMainThread = new Thread(new MainGame(), "GameMain");
        GameMainThread.start();
        looperSetView(0);
    }

    private void startSelectMapThread() {

        selectMapThread = new Thread(new SelectMap(), "MapCanvas");
        selectMapThread.start();
        looperSetView(2);

    }

    public void stopSelectMapThread() {
        try {
            selectMapStopped = true;
            selectMapThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
/*
    public void startGameNo() {

        gameStopped = false;

        scenario = new Scenario("ScenarioGenerado.json");

        do {
            if (matrixScenario != null) {
                controls = new Controls();
                myCanvas = new MyCanvas();
                background = new Background();
                frontground = new Frontground();
                scoreboard = new Scoreboard();
                matrixX = new MatrixX();
                penguin = new Penguin();
            }
        } while (matrixScenario != null);

        startMainGameThread();
    }

    private void startMainGameThread() {
        gameStopped = false;
        GameMainThread = new Thread(new MainGame(), "GameMain");
        GameMainThread.start();
        looperSetView(0);
        stopSelectMapThread();
    }

    private void startSelectMap() {
        LinearLayout mainLayout = findViewById(R.id.mainLayout); //take size screen
        widthScreen = mainLayout.getMeasuredWidth();
        heightScreen = mainLayout.getMeasuredHeight();
        mapCanvas = new MapCanvas();
        startSelectMapThread();
    }

    private void startSelectMapThread() {
        selectMapStopped = false;
        selectMapThread = new Thread(new SelectMap(), "MapCanvas");
        selectMapThread.start();
        looperSetView(2);
    }

    public void stopSelectMapThread() {
        try {
        selectMapStopped = true;
        selectMapThread.join();
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
    }*/

    private void looperSetView(int option) {
        Handler mThread = new Handler(Looper.getMainLooper());
        mThread.post(new SetView(option) {
        });
    }


}
