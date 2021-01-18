package correcorre;

import correcorre.R;

import static correcorre.ResourcesClass.*;

public class SetView implements Runnable {
    private int option;

    public SetView(int option) {
        this.option = option;
    }

    public void run() {
        /*
        if (option == 0) {
            mactivity.setView(0);
        }
        if (option == 1) {
            mactivity.setView(1);
        }*/
        if (option == 0) mactivity.setContentView(myCanvas);
        if (option == 1) mactivity.setContentView(R.layout.activity_main);
        if (option == 2) mactivity.setContentView(mapCanvas);
    }
}