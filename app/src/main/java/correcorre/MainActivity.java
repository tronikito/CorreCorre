package correcorre;

        import android.annotation.SuppressLint;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;

        import android.graphics.Color;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.RelativeLayout;

        import correcorre.graficos.LCanvas;


public class MainActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static final int UI_ANIMATION_DELAY = 300;

    private final Handler mHideHandler = new Handler();

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    //############################################################################

    Main main;
    Thread loader;
    LCanvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**
        //hideUI();
        //setContentView(R.layout.activity_main);
        //myCanvas = new MyCanvas(this);
        canvas = new LCanvas(this);
        //myCanvas.setBackgroundColor(Color.RED);//test
        canvas.setBackgroundColor(Color.RED);//test
        setContentView(canvas);
        //myCanvas.invalidate();
        //RelativeLayout main = findViewById(R.id.main);
        this.main = new Main(this.getApplicationContext());
        this.loader = new Thread(new Loader(main, this.loader, canvas, this), "Loader");
        this.loader.start();
*/

    }
    public void stopLoader() {
        try {
            this.loader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void setView(View o) {
        setContentView(o);
    }

    //############################################################################
    public void hideUI() {
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mVisible = false;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
        //hideUI();
        //setContentView(R.layout.activity_main);
        //myCanvas = new MyCanvas(this);
        canvas = new LCanvas(this);
        //myCanvas.setBackgroundColor(Color.RED);//test
        canvas.setBackgroundColor(Color.RED);//test
        setContentView(canvas);
        //myCanvas.invalidate();
        //RelativeLayout main = findViewById(R.id.main);
        this.main = new Main(this.getApplicationContext());
        this.loader = new Thread(new Loader(main, this.loader, canvas, this), "Loader");
        this.loader.start();
    }
    //FUNCIONA
    @Override
    protected void onUserLeaveHint()
    {
        // When user presses home page
        this.main.detener();
        super.onUserLeaveHint();
    }
    //DETIENE APLICACION AL MINIMIZAR
    private void hide() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        hideUI();
    }

    @SuppressLint("InlinedApi")
    private void show() {
        RelativeLayout main = findViewById(R.id.main);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;
        hideUI();
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
        hideUI();
    }
}