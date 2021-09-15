package correcorrerework.selectmap;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Pair;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.HashMap;

import correcorrerework.R;

import static correcorrerework.ResourcesClass.*;

public class MapMatrixX {

    private int mapListSize = 10;
    private Rect selectMapFrame = new Rect();
    private Drawable backgroundFrame;


    public MapMatrixX() {

        int selectMapLeft = widthScreen/8;
        int selectMapRight = widthScreen - widthScreen/8;
        int mapMargin = sizeDot;
        int mapSize = (selectMapLeft*2 + sizeDot*4) / 5;
        int selectMapTop = heightScreen /8;
        int selectMapBottom = heightScreen - (selectMapTop + mapSize*2 + mapMargin);

        selectMapFrame.top = selectMapTop;
        selectMapFrame.left = selectMapLeft;
        selectMapFrame.right = selectMapRight;
        selectMapFrame.bottom = selectMapBottom;

        backgroundFrame = VectorDrawableCompat.create(mactivity.getResources(), R.drawable.ic_007,null);

        mapList = new HashMap<Integer, Pair<Drawable,Rect>>();

        for (int x = 0; x < mapListSize; x++) {
            Rect newRect = new Rect();
            Drawable newDraw =  VectorDrawableCompat.create(mactivity.getResources(), R.drawable.ic_007,null);
            Pair newPair = new Pair(newDraw,newRect);
            mapList.put(x,newPair);
        }
    }
    public void newMapRect() {
        int leftPosition = widthScreen;
    }

    public void printSelectMapFrame(Canvas c) {
        backgroundFrame.setBounds(selectMapFrame);
        backgroundFrame.draw(c);
    }
}
