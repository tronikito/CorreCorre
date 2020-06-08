package correcorre.graficos;

import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;
import correcorre.Main;
import correcorre.blocks.Block;
import correcorre.blocks.BlueBlock;
import correcorre.blocks.DirtBlock;
import correcorre.blocks.EmptyBlock;
import correcorre.blocks.GrassBlock;
import correcorre.blocks.RedBlock;
import correcorre.scenario.Scenario;

public class MatrixX {

    //throw new java.lang.RuntimeException("error block"); <<usar para casting blocks

    public volatile ArrayList<ArrayList<Block>> matrix = null;
    public volatile ArrayList<ArrayList<String>> matrixScenario = null;
    private MyCanvas canvas;

    final private int cW;
    final private int cH;
    private int blocksWidth = 30;
    final private int size;
    final private int offsetnW;
    final private int offsetX;
    final private int offsetX2;
    private int blocksHeight;
    final private int offsetnH;
    final private int offsetY;
    final private int offsetY2;
    private int relativeOffsetX = 0;
    private int relativeOffsetY = 1;
    public boolean working = false;
    private static Main main;
    private static Scenario scenario;
    private int[] scenarioStart;//debe ser el offset

    public MatrixX(LCanvas c, Main m,Scenario s) {

        main = m;
        scenario = s;
        this.matrixScenario = scenario.scenario;
        this.scenarioStart = scenario.start;

        this.cW = c.width;
        this.cH = c.height;

        //Calc new Width, size(blocks) and num blocksWidth matrix

        double size = Math.ceil(cW/this.blocksWidth);
        if (size %2 != 0) size++;
        this.size = (int) size;
        int nW = blocksWidth*this.size;
        this.offsetnW = (nW-cW)/2;//where start block at spawn
        this.offsetX = this.offsetnW + this.size;//where to spawn block
        this.blocksWidth += 2;//make margin for hidden move of blocks
        this.offsetX2 = this.offsetnW + this.size*2; //new offset to check out of map block.

        //Calc new Height, and num blocksHeight matrix;

        double blocksHeight = Math.ceil(cH/this.size);
        if (blocksHeight %2 != 0) blocksHeight++;
        this.blocksHeight = (int) blocksHeight;
        int nH = this.blocksHeight*this.size;
        this.offsetnH = (nH-cH)/2;//where start block at spawn
        this.offsetY = this.offsetnH+this.size;//where to spawn block
        this.blocksHeight += 2;//make margin for hidden move of blocks
        this.offsetY2 = this.offsetnH + this.size*2;//new offset to check out of map block.

    }

    public int getWidth() {
        return this.cW;
    }
    public int getHeight() {
        return this.cH;
    }

    // GENERAR MATRIXX INICIAL ##################################################################

    public synchronized void generateNewMatrix() {

        this.matrix = new ArrayList<ArrayList<Block>>();
        ArrayList<Block> column;

        for (int x = 0; x < blocksWidth; x++) {
            column = new ArrayList<>();
            for (int y = 0; y < blocksHeight; y++) {
                Block newB = generateFromScenario(x+relativeOffsetX,y+relativeOffsetY);
                Rect newR = new Rect();

                newR.left = (x * size) - offsetX2;
                newR.right = (x * size) - offsetX;
                newR.top = (y * size) - offsetY2;
                newR.bottom = (y * size) - offsetY;

                newB.setRect(newR);

                column.add(newB);
            }
            matrix.add(column);
        }
    }

    private synchronized Block generateFromScenario(int x, int y) {
        Block newB = null;
        if (x < this.matrixScenario.size() && x >= 0) {
            if (y < this.matrixScenario.get(x).size() && y >= 0) {
                String type = this.matrixScenario.get(x).get(y);
                newB = createNewTypeBlock(type);
            }
        }
        if (newB == null) newB = new EmptyBlock(main.getContext());
        return newB;
    }

    private synchronized Block createNewTypeBlock(String type) {
        Block newB = null;
        if (type.equals("dirt")) {
            newB = new DirtBlock(main.getContext());
        }
        if (type.equals("grass")) {
            newB = new GrassBlock(main.getContext());
        }
        if (type.equals("red")) {
            newB = new RedBlock(main.getContext());
        }
        if (type.equals("blue")) {
            newB = new BlueBlock(main.getContext());
        }
        if (type.equals("empty")) {
            newB = new EmptyBlock(main.getContext());
        }
        return newB;
    }

    // ##########################################################################################

    // MOVE MATRIXX ############################################################################

    public synchronized void moveMatrix(int[] speed) {

        for (int x = 0; x < this.matrix.size(); x++) {
            for (int y = 0; y < this.matrix.get(x).size(); y++) {
                Block b = this.matrix.get(x).get(y);
                b.moveX(speed[0]);
                b.moveY(speed[1]);
            }
        }
        checkOffset();
    }

    public synchronized void checkOffset() {

        Block b;

        int foundL = -1;
        int foundT = -1;
        int foundR = -1;
        int foundB = -1;

        boolean left = false;
        boolean top = false;
        boolean right = false;
        boolean bottom = false;

        int menor;
        int mayor;

        menor = this.cW + this.offsetX2*10;
        for (int x = 0; x < this.matrix.size(); x++) {
            b = this.matrix.get(x).get(0);
            if (b.getRect().left < menor) {
                foundL = x;
                menor = b.getRect().left;
            }
            if (b.getRect().left < 0 - this.offsetX2) {
                left = true;
            }
        }
        menor = this.cH + this.offsetY2*10;
        for (int y = 0; y < this.matrix.get(0).size(); y++) {
            b = this.matrix.get(0).get(y);
            if (b.getRect().top < menor) {
                foundT = y;
                menor = b.getRect().top;
            }
            if(b.getRect().top < (0 - this.offsetY2)) {
                top = true;
            }
        }
        mayor = -this.offsetX2*10;
        for (int x = 0; x < this.matrix.size(); x++) {
            b = this.matrix.get(x).get(0);
            if (b.getRect().right > mayor) {
                foundR = x;
                mayor = b.getRect().right;
            }
            if (b.getRect().right > this.cW + this.offsetX2) {
                right = true;
            }
        }
        mayor = -this.offsetY2*10;
        for (int y = 0; y < this.matrix.get(0).size(); y++) {
            b = this.matrix.get(0).get(y);
            if (b.getRect().bottom > mayor) {
                foundB = y;
                mayor = b.getRect().bottom;
            }
            if(b.getRect().bottom > this.cH + this.offsetY2) {
                bottom = true;
            }
        }

        if (left) this.relativeOffsetX += 1;
        if (top) this.relativeOffsetY += 1;
        if (right) this.relativeOffsetX -= 1;
        if (bottom) this.relativeOffsetY -= 1;

        if (left) {
            int calcY = foundT;
            for (int y = 0; y < blocksHeight; y++) {
                b = this.matrix.get(foundL).get(calcY);
                int padding = b.getRect().left + this.offsetX2;
                b.setRect(newRect(b,padding,0));
                calcY++;
                if (calcY == blocksHeight) calcY = 0;
            }
            foundL += 1;
            if (foundL == blocksWidth) foundL = 0;
        }

        if (right) {
            int calcY = foundT;
            for (int y = 0; y < blocksHeight; y++) {
                b = this.matrix.get(foundR).get(calcY);
                int padding = ((this.cW + offsetX) - b.getRect().right);
                b.setRect(newRect(b,padding,2));
                calcY++;
                if (calcY == blocksHeight) calcY = 0;
            }
            foundL -= 1;
            if (foundL == -1) foundL = blocksWidth-1;
        }

        if (top) {
            int calcX = foundL;
            for(int x = 0; x < blocksWidth; x++) {
                b = this.matrix.get(calcX).get(foundT);
                int padding = (b.getRect().top + offsetY2);
                b.setRect(newRect(b,padding,1));
                calcX++;
                if (calcX >= blocksWidth) calcX = 0;
            }
            foundT += 1;
            if (foundT == blocksHeight) foundT = 0;
        }

        if (bottom && main.getSpeed()[1] < 0) {
            int calcX = foundL;

            for(int x = 0; x < blocksWidth; x++) {
                b = this.matrix.get(calcX).get(foundB);
                int padding = ((this.cH + offsetY2) - b.getRect().bottom);
                b.setRect(newRect(b,padding,3));
                calcX++;
                if (calcX >= blocksWidth) calcX = 0;
            }
            foundT -= 1;
            if (foundT == -1) foundT = blocksHeight-1;
        }

        recalculateDrawable(foundL,foundT);
    }

    public synchronized void recalculateDrawable(int foundL,int foundT) {

        Rect r;
        int calcX = foundL;
        int calcY = foundT;

        for (int x = 0; x < blocksWidth; x++) {
            for(int y = 0; y < blocksHeight; y++) {
                r = this.matrix.get(calcX).get(calcY).getRect();
                this.matrix.get(calcX).set(calcY,generateFromScenario(x+relativeOffsetX,y+relativeOffsetY));
                this.matrix.get(calcX).get(calcY).setRect(r);
                calcY++;
                if (calcY == blocksHeight) calcY = 0;
            }
            calcX++;
            if (calcX == blocksWidth) calcX = 0;
        }

        if (this.canvas != null) main.setSpeed(this.canvas.getSpeed());//evitar desfase;
        //despues de mover/recalcular offset/recalcularDrawables;
        //se cambia la velocidad para evitar desfases;
    }

    private synchronized Rect newRect(Block old, int padding, int pos) {

        Rect r = new Rect();
        if (pos == 0) {
            r.left = (this.cW + offsetnW) + padding;
            r.right = (this.cW + offsetX) + padding;
            r.top = old.getRect().top;
            r.bottom = old.getRect().bottom;
            return r;
        }
        if (pos == 1) {
            r.left = old.getRect().left;
            r.right = old.getRect().right;
            r.top =  (this.cH + offsetnH) + padding;
            r.bottom = (this.cH + offsetY) + padding;
            return r;
        }
        if (pos == 2) {
            r.left = -offsetX2 - padding;
            r.right = -offsetX - padding;
            r.top = old.getRect().top;
            r.bottom = old.getRect().bottom;
            return r;
        }
        if (pos == 3) {
            r.left = old.getRect().left;
            r.right = old.getRect().right;
            r.top =  -offsetY - padding;
            r.bottom = -offsetnH - padding;
            return r;
        }
        return r;
    }

    // IMPRIMIR MATRIXX #########################################################################

    public synchronized void printMatrix(Canvas canvas) {//SINCRO CON MOVEMATRIX
        if (this.matrix != null) {
            for (int x = 0; x < blocksWidth; x++) {
                for (int y = 0; y < blocksHeight; y++) {
                    Block b = this.matrix.get(x).get(y);
                    if (b instanceof GrassBlock) b.getDrawable(main.fps).draw(canvas);//Drawable animado
                    else b.getDrawable().draw(canvas);

                    /*
                    //Prueba texto casillas DEBUGG
                    //text variables
                    Rect r;
                    r = b.getRect();
                    Paint tempTextPaint = new Paint();
                    String text = x + " " + y;
                    float textWidth;
                    //text constructor
                    tempTextPaint.setColor(Color.YELLOW);
                    tempTextPaint.setAntiAlias(true);
                    tempTextPaint.setStyle(Paint.Style.FILL);
                    tempTextPaint.setTextSize(24);
                    // text drawable
                    textWidth = tempTextPaint.measureText(text);
                    canvas.drawText(text, 0 + r.right -(textWidth), 50 + r.top-(24), tempTextPaint);
*/
                }
            }
        }
    }
}