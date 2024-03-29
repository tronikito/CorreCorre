package correcorrerework;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Map;

import correcorrerework.game.MainGame;
import correcorrerework.game.background.Background;
import correcorrerework.game.background.BackgroundObject;
import correcorrerework.game.background.Frontground;
import correcorrerework.game.enemy.Enemy;
import correcorrerework.game.graficos.Controls;
import correcorrerework.game.graficos.MatrixX;
import correcorrerework.game.graficos.MyCanvas;
import correcorrerework.game.graficos.Scoreboard;
import correcorrerework.game.penguin.Penguin;
import correcorrerework.game.scenario.Block;
import correcorrerework.game.scenario.Scenario;
import correcorrerework.game.weapons.Bullet;
import correcorrerework.game.weapons.Explosion;
import correcorrerework.game.weapons.Weapon;
import correcorrerework.selectmap.MapCanvas;
import correcorrerework.selectmap.MapMatrixX;
import correcorrerework.selectmap.SelectMap;


public abstract class ResourcesClass {

    public static MainActivity mactivity = null;
    public static SelectMap selectMap = null;
    public static MainGame mainGame = null;
    public static Controls controls = null;
    public static MapMatrixX mapMatrixX;
    public static MatrixX matrixX = null;
    public static Scenario scenario = null;
    public static MapCanvas mapCanvas = null;
    public static MyCanvas myCanvas = null;
    public static Background background = null;
    public static Frontground frontground = null;
    public static Scoreboard scoreboard = null;
    public static volatile Penguin penguin = null;

    public static int fps = 0;

    //Threads variables
    public static Thread selectMapThread = null;
    public static Thread GameMainThread = null;
    public static boolean selectMapStopped = false;
    public static boolean gameStopped = false;

    //ScreenProperty
    public static int widthScreen = 0;
    public static int heightScreen = 0;
    public static int sizeDot = 0;
    public static int blocksWidth = 30;
    public static int blocksHeight = 0;

    //speeds
    public static int tempo = 0;
    public static int[] penguinSpeed = new int[]{0, 0};

    //ObjectsList
    public volatile static ArrayList<ArrayList<ArrayList>> matrixScenario = null;
    public volatile static ArrayList<ArrayList<Block>> matrixList = null;
    public volatile static ArrayList<Enemy> enemyList = null;
    public volatile static ArrayList<Weapon> weaponList = null;
    public volatile static ArrayList<Bullet> bulletList = null;
    public volatile static ArrayList<Explosion> explosionList = null;
    public volatile static ArrayList<BackgroundObject> backgroundList = null;
    //public static ArrayList<BackgroundObject> frontgroundList = new ArrayList<BackgroundObject>();
    public volatile static ArrayList<Drawable> controlsList = null;
    public volatile static Map<Integer, Pair<Drawable, Rect>> mapList = null;

    //colors


    public static void reset() {

        mactivity = null;
        mainGame = null;
        selectMap = null;
        controls = null;
        mapMatrixX = null;
        matrixX = null;
        scenario = null;
        mapCanvas = null;
        myCanvas = null;
        background = null;
        frontground = null;
        scoreboard = null;
        penguin = null;

        fps = 0;

        //Threads variables
        GameMainThread = null;
        gameStopped = false;

        //ScreenProperty
        widthScreen = 0;
        heightScreen = 0;
        sizeDot = 0;
        blocksWidth = 30; //30
        blocksHeight = 0;

        //speeds
        tempo = 0;
        penguinSpeed = new int[]{0, 0};

        //ObjectsList
        matrixScenario = null;
        matrixList = null;
        enemyList = null;
        weaponList = null;
        bulletList = null;
        explosionList = null;
        backgroundList = null;
        //public static ArrayList<BackgroundObject> frontgroundList = new ArrayList<BackgroundObject>();
        controlsList = null;
        mapList = null;
    }
}
