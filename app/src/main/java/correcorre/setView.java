package correcorre;

import android.view.SurfaceView;

import correcorre.graficos.LCanvas;
import correcorre.graficos.MyCanvas;

public class setView implements Runnable {

    private MainActivity ma;
    private Main m;
    private MyCanvas c;

    public setView(Main m, MainActivity ma, MyCanvas c) {
        this.m = m;
        this.ma = ma;
        this.c = c;
    }

    public void run() {
        ma.setView(this.c);
    }
}