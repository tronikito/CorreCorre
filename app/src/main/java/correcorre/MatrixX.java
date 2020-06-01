package correcorre;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import correcorre.graficos.*;
import correcorre.scenario.Scenario;

public class MatrixX {

    //throw new java.lang.RuntimeException("error block"); <<usar para casting blocks
    // eliminar newblock y añadirlo a extension blocks

    public volatile ArrayList<ArrayList<Block>> matrix = null;
    public volatile ArrayList<ArrayList<String>> matrixScenario = null;
    private MyCanvas canvas;

    final private int cW;
    final private int cH;
    private int blocksWidth = 35;
    final private int size;
    final private int offsetnW;
    final private int offsetX;
    final private int offsetX2;
    private int blocksHeight;
    final private int offsetnH;
    final private int offsetY;
    final private int offsetY2;
    private int relativeOffsetX = 0;
    private int relativeOffsetY = 0;
    public boolean working = false;
    int foundL = -1;
    int foundT = -1;
    int foundR = -1;
    int foundB = -1;

    private static Main main;
    private static Scenario scenario;
    private int[] scenarioStart;
    private Drawable renderBlock;

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

    // GENERAR MATRIXX INICIAL ##################################################################

    public ArrayList<ArrayList<Block>> generateNewMatrix() {
        this.matrix = new ArrayList();
        ArrayList column;

        for (int x = 0; x < blocksWidth; x++) {
            column = new ArrayList();
            for (int y = 0; y < blocksHeight; y++) {
                Block newB = generateFromScenario(x+relativeOffsetX,y+relativeOffsetY);
                Rect newR = new Rect();

                //Posicionar rectangulo
                newR.left = (x * size) - offsetX2;
                newR.right = (x * size) - offsetX;
                newR.top = (y * size) - offsetY2;
                newR.bottom = (y * size) - offsetY;

                newB.setRect(newR);

                column.add(newB);
            }
            matrix.add(column);
        }
        return matrix;
    }

    private Block generateFromScenario(int x, int y) {
        Block newB = null;
        //Check ArraySize init
        if (x < this.matrixScenario.size() && x >= 0) {
            if (y < this.matrixScenario.get(x).size() && y >= 0) {
        //Check ArraySize end
                String type = this.matrixScenario.get(x).get(y);
                newB = createNewTypeBlock(type);
            }
        }
        if (newB == null) newB = new EmptyBlock(main.getContext());
        return newB;
    }
    private Block createNewTypeBlock(String type) {
        Block newB = null;
        if (type.equals("dirt")) {
            newB = new RedBlock(main.getContext());
        }
        return newB;
    }

    // ##########################################################################################

    // MOVER MATRIXX ############################################################################

    public synchronized void moveMatrix(int[] speed) {
        working = true;
        for (int x = 0; x < this.matrix.size(); x++) {
            for (int y = 0; y < this.matrix.get(x).size(); y++) {
                Block b = this.matrix.get(x).get(y);
                b.moveX(speed[0]);
                b.moveY(speed[1]);
                //checkOffset();
            }
        }
        working = false;
    }

    public synchronized void checkOffset() {

        Block old;
        Block b;
        Rect r;

        foundL = -1;
        foundT = -1;
        foundR = -1;
        foundB = -1;

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

        //int paddingL = this.matrix.get(foundL).get(foundT).getRect().left + offsetX2;
        //int paddingT = this.matrix.get(foundL).get(foundT).getRect().top + offsetY2;
        //int paddingB = ((this.cH + offsetY2) - this.matrix.get(foundR).get(foundB).getRect().bottom);
        //int paddingR = ((this.cW + offsetX) - this.matrix.get(foundR).get(foundB).getRect().right);

        if (left) this.relativeOffsetX += 1;
        if (top) this.relativeOffsetY += 1;
        if (right) this.relativeOffsetX -= 1;
        if (bottom) this.relativeOffsetY -= 1;

        if (left) {

            int calcY = foundT;
            int contador = 0;
            while (contador < blocksHeight) {
            //for (int y = foundT; y == foundT-1; y++) {
                old = this.matrix.get(foundL).get(calcY);

                int padding = old.getRect().left + this.offsetX2;
                //int calcY = calcRelativePosition(old.getRect().top, 0, paddingT);
                b = generateFromScenario(relativeOffsetX+blocksWidth-1, relativeOffsetY+contador);
                b.setRect(newRect(old,padding,0));
                this.matrix.get(foundL).set(calcY, b);
                calcY++;
                contador++;
                if (calcY == blocksHeight) calcY = 0;
                //System.out.println(calcY);
            }
        }

        if (top) {
            int calcX = foundL;
            if (right || left) calcX = foundL+1;
            //if (calcX == blocksWidth) calcX = 0;

            int contador = 0;
            while (contador < blocksWidth) {

            //for (int x = foundL; x == foundL-1; x++) {
                old = this.matrix.get(calcX).get(foundT);
                int padding = (old.getRect().top + offsetY2);
                //int calcX = calcRelativePosition(old.getRect().left,1,paddingL);
                b = generateFromScenario(relativeOffsetX+contador,relativeOffsetY+blocksHeight-1);//
                b.setRect(newRect(old,padding,1));
                this.matrix.get(calcX).set(foundT,b);
                calcX++;
                contador++;
                if (calcX == blocksWidth) calcX = 0;
            };
        }

        if (right) {
            int calcY = foundT;
            int contador = 0;
            //for (int y = foundT; y == foundT-1; y++) {
            while (contador < blocksHeight) {
                old = this.matrix.get(foundR).get(calcY);
                int padding = ((this.cW + offsetX) - old.getRect().right);
                //int calcY = calcRelativePosition(old.getRect().top,2,paddingB);
                b = generateFromScenario(relativeOffsetX,relativeOffsetY+contador);
                b.setRect(newRect(old,padding,2));
                this.matrix.get(foundR).set(calcY,b);
                calcY++;
                contador++;
                if (calcY == blocksHeight) calcY = 0;
            }
        }

        if (bottom) {
            int calcX = 0;
            if (right || left) calcX = foundL+1;
            //if (calcX == blocksWidth) calcX = 0;
            //if (left) calcX = foundL+1;
            int contador = 0;
            //for (int x = foundL; x == foundL-1; x++) {
            while (contador < blocksWidth) {

                old = this.matrix.get(calcX).get(foundB);
                int padding = ((this.cH + offsetY2) - old.getRect().bottom);
                //int calcX = calcRelativePosition(old.getRect().left,3,paddingR);
                b = generateFromScenario(relativeOffsetX+contador,relativeOffsetY);
                b.setRect(newRect(old,padding,3));
                this.matrix.get(calcX).set(foundB,b);
                calcX++;
                contador++;
                if (calcX == blocksWidth) calcX = 0;
            }
        }
        if (this.canvas != null) main.setSpeed(this.canvas.getSpeed());
    }

    private Rect newRect(Block old, int padding, int pos) {
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
            for (int x = 0; x < this.matrix.size(); x++) {
                for (int y = 0; y < this.matrix.get(x).size(); y++) {
                    //for (int y = this.matrix.get(i).size()-1; y >= 0; y--) {
                    Block b = this.matrix.get(x).get(y);
                    b.getDrawable().draw(canvas);

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
    public void setCanvas(MyCanvas c) {
        this.canvas = c;
    }
}
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################
// ##############################################################################################

    /**
    public synchronized void sdfsdfmoveMatrix(float speed) {//SINCRO CON PRINTMATRIX

        boolean firstCheckTop = true;
        boolean firstCheckBottom = true;
        for (int x = 0; x < this.matrix.size(); x++) {
            boolean firstCheckLeft = true;
            boolean firstCheckRight = true;

            for (int y = 0; y < this.matrix.get(x).size(); y++) {

                b = this.matrix.get(x).get(y);

                //horizontal
                if (b.getRect().left < 0 - (size + (size / 2))) {
                    if (firstCheckLeft) {
                        firstCheckLeft = false;
                        offsetX += 1;
                        //offsetY += 1;
                    }
                    b.setDrawable(renderBlock);
                    b.moveX(-this.cW);
                    b.setDrawable(generateFromScenario(howManyW - 1, howManyH -1).getDrawable());
                }
                if (b.getRect().right > this.cW - (size + (size / 2))) {
                    if (firstCheckRight) {
                        firstCheckRight = false;
                        offsetX -= 1;
                        //offsetY -= 1;
                    }
                    b.moveX(+this.cW);
                    //b.setDrawable(generateFromScenario(0,y).getDrawable());//funciona sin vertical
                    b.setDrawable(generateFromScenario(0, y).getDrawable());
                }
                if  (b.getRect().top < 0 - (size + (size / 2))) {
                    if (firstCheckTop) {
                        firstCheckTop = false;
                        offsetY += 1;
                    }
                    b.moveY(-this.cH);
                    b.setDrawable(generateFromScenario(howManyW -1, howManyH - 1).getDrawable());
                }
                //vertial

                if (b.getRect().bottom > this.cH - (size - (size / 2))) {
                    if (firstCheckBottom) {
                        firstCheckBottom = false;
                        offsetY -= 1;
                    }
                    b.moveY(+this.cH);
                    b.setDrawable(generateFromScenario(x, 0).getDrawable());
                }


                System.out.println(offsetX + " " + offsetY);
                b.moveX(speed);
                b.moveY(speed);
            }
        }
    }
    public synchronized boolean csdfsdfheckScenario(int x, int y) {
        if (x < this.matrixScenario.size() && x >= 0) {
            if (y < this.matrixScenario.get(x).size() && y >= 0) {
                return true;
            }
        }
        return false;
    }
**/
/* calcular por posicion absoluta
    public int calcRelativePosition(int pos,int layer,int padding) {

        if (layer == 0) {
            return (pos+offsetY2+padding)/this.size;
        }
        if (layer == 1) {
            return (pos+offsetX2+padding)/this.size;
        }
        if (layer == 2) {
            return (pos+offsetY2-padding)/this.size;
        }
        if (layer == 3) {
            return (pos+offsetX2-padding)/this.size;
        }
        System.out.println("fail");
        return 0;
    }
*/