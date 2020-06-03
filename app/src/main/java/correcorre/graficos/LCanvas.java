package correcorre.graficos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class LCanvas extends View {

    public int width = -1;
    public int height = -1;
    private Paint paint = new Paint();

    public LCanvas(Context c) {
        super(c);
    }

    @Override
    protected void onDraw(Canvas c) {
        this.width = getWidth();
        this.height = getHeight();
        c.drawCircle(getWidth() / 2, getHeight() / 2, 250, paint);
    }
}
