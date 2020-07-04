package correcorre.graficos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;
import correcorre.Main;
import correcorre.enemy.Spider;
import correcorre.penguin.Penguin;
import correcorre.scenario.Block;
import correcorre.scenario.Scenario;

public class MatrixX {

    //throw new java.lang.RuntimeException("error block"); <<usar para casting blocks

    public volatile ArrayList<ArrayList<Block>> matrix = null;
    public volatile ArrayList<ArrayList<ArrayList>> matrixScenario = null;
    public volatile ArrayList<Spider> enemyList = null;

    public boolean generateMatrix = false;
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
    private int relativeOffsetX = -5;
    private int relativeOffsetY = -5;//-5
    private static Main main;
    private static Scenario scenario;
    private static MyCanvas myCanvas;
    private static Penguin penguin;
    private Context context;
    private int[] scenarioStart;//debe ser el offset

    public MatrixX(Context context, LCanvas c, Main m, Scenario s) {

        this.context = context;
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

    public void setPenguin(Penguin p) { penguin = p; }
    public int getSize() { return this.size; }
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
                Block newB = new Block(this.context,"empty",1,1);
                generateFromScenario(newB,x+relativeOffsetX,y+relativeOffsetY);

                Rect r = newB.getRect();
                r.left = (x * size) - offsetX2;
                r.right = (x * size) - offsetX;
                r.top = (y * size) - offsetY2;
                r.bottom = (y * size) - offsetY;
                column.add(newB);
            }
            matrix.add(column);
        }
        generateMatrix = true;
    }

    private synchronized void generateFromScenario(Block old, int x, int y) {
        boolean newType = false;
        if (x < this.matrixScenario.size() && x >= 0) {
            if (y < this.matrixScenario.get(x).size() && y >= 0) {

                String type = (String) this.matrixScenario.get(x).get(y).get(0);
                int position = (int) this.matrixScenario.get(x).get(y).get(1);
                int enemyType = (int) this.matrixScenario.get(x).get(y).get(2);
                int solid = (int) this.matrixScenario.get(x).get(y).get(3);
                old.setType(type,1);
                old.setPosition(position);
                old.setEnemyType(enemyType);
                old.setSolid(solid);
                if (type.equals("enemy") && enemyType != 0) {
                    this.matrixScenario.get(x).get(y).set(2, 0);//reset to not more spawns.
                   generateEnemy(old);
                   old.setEnemyType(0);//reset from matrixX for not double spawn.
                }
                newType = true;
            }
        }
        if (!newType) old.setType("empty",1);
    }

    private synchronized void generateEnemy(Block old) {

        if (this.enemyList == null) {
            this.enemyList = new ArrayList<Spider>();
        }

        if (old.getEnemyType() == 1) {//spider
            //Spider spider = new Spider(main, this, old.getRect().top + (this.size / 2), old.getRect().left - (this.size / 2 + this.size * 2), this.size * 4, this.size * 2);
            Spider spider = new Spider(main, this, 600, 800, this.size * 4, this.size * 2);//just for show
            this.enemyList.add(spider);
        }
    }
    public synchronized void getEnemys(Canvas c) {

        if (this.enemyList != null) {
            for (int x = 0; x < this.enemyList.size(); x++) {
                this.enemyList.get(x).printEnemy(c);
            }
        }
    }
    // MOVE MATRIXX ############################################################################

    public synchronized void moveMatrix(int[] speed) {

        if (!main.penguinX || !main.penguinY) {
            for (int x = 0; x < this.matrix.size(); x++) {
                for (int y = 0; y < this.matrix.get(x).size(); y++) {
                    Block b = this.matrix.get(x).get(y);

                    if (!main.penguinX) {
                        b.moveX(speed[0]);
                    }
                    if (!main.penguinY) {
                        b.moveY(speed[1]);
                    }
                }
            }
            //move enemY
            if (this.enemyList != null) {
                for (int x = 0; x < this.enemyList.size(); x++) {
                    if (!main.penguinX) {
                        this.enemyList.get(x).moveX(speed[0]);
                    }
                    if (!main.penguinY) {
                        this.enemyList.get(x).moveY(speed[1]);
                    }
                }
            }

            checkOffset();
        }
    }

    public synchronized void checkOffset() {

        Block b;

        int foundL = 0;
        int foundT = 0;
        int foundR;
        int foundB;

        boolean left = false;
        boolean top = false;
        boolean right = false;
        boolean bottom = false;

        int lower;

        lower = this.cW + this.offsetX2*10;
        for (int x = 0; x < this.matrix.size(); x++) {
            b = this.matrix.get(x).get(0);
            if (b.getRect().left < lower) {
                foundL = x;
                lower = b.getRect().left;
            }
            if (b.getRect().left < 0 - this.offsetX2) {
                left = true;
            }
        }
        lower = this.cH + this.offsetY2*10;
        for (int y = 0; y < this.matrix.get(0).size(); y++) {
            b = this.matrix.get(0).get(y);
            if (b.getRect().top < lower) {
                foundT = y;
                lower = b.getRect().top;
            }
            if(b.getRect().top < (0 - this.offsetY2)) {
                top = true;
            }
        }

        foundR = foundL-1;
        if (foundR == -1) foundR = blocksWidth-1;
        b = this.matrix.get(foundR).get(0);
        if (b.getRect().right > this.cW + this.offsetX2) {
            right = true;
        }

        foundB = foundT-1;
        if (foundB == -1) foundB = blocksHeight-1;
        b = this.matrix.get(foundL).get(foundB);
        if(b.getRect().bottom > this.cH + this.offsetY2) {
            bottom = true;
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
                newRect(b,padding,0);
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
                newRect(b,padding,2);
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
                newRect(b,padding,1);
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
                newRect(b,padding,3);
                calcX++;
                if (calcX >= blocksWidth) calcX = 0;
            }
            foundT -= 1;
            if (foundT == -1) foundT = blocksHeight-1;
        }
        if (left || right || top || bottom) recalculateDrawable(foundL,foundT);
    }

    public synchronized void recalculateDrawable(int foundL,int foundT) {

        int calcX = foundL;
        int calcY = foundT;

        for (int x = 0; x < blocksWidth; x++) {
            for(int y = 0; y < blocksHeight; y++) {
                generateFromScenario(this.matrix.get(calcX).get(calcY),x+relativeOffsetX,y+relativeOffsetY);
                calcY++;
                if (calcY == blocksHeight) calcY = 0;
            }
            calcX++;
            if (calcX == blocksWidth) calcX = 0;
        }
    }

    private synchronized void newRect(Block b, int padding, int pos) {

        if (pos == 0) {
            b.getRect().left = (this.cW + offsetnW) + padding;
            b.getRect().right = (this.cW + offsetX) + padding;
        }
        if (pos == 1) {
            b.getRect().top =  (this.cH + offsetnH) + padding;
            b.getRect().bottom = (this.cH + offsetY) + padding;
        }
        if (pos == 2) {
            b.getRect().left = -offsetX2 - padding;
            b.getRect().right = -offsetX - padding;
        }
        if (pos == 3) {
            b.getRect().top =  -offsetY - padding;
            b.getRect().bottom = -offsetnH - padding;
        }
        //if (penguin != null) main.setSpeed(penguin.getSpeed());//evitar desfase;
        //despues de mover/recalcular offset/recalcularDrawables;
        //se cambia la velocidad para evitar desfases;
    }

    // IMPRIMIR MATRIXX #########################################################################

    public synchronized void printMatrixBack(Canvas canvas) {//SINCRO CON MOVEMATRIX
        if (this.matrix != null) {
            for (int x = 0; x < blocksWidth; x++) {
                for (int y = 0; y < blocksHeight; y++) {
                    Block b = this.matrix.get(x).get(y);
                    if (b.getPosition() == 0) {//BACK
                        if (b.getSprite() == 0) {
                            b.animatedSprite();
                        }
                        b.getDrawable().draw(canvas);
                    }
                }
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
    public synchronized void printMatrixFront(Canvas canvas) {//SINCRO CON MOVEMATRIX
        if (this.matrix != null) {
            for (int x = 0; x < blocksWidth; x++) {
                for (int y = 0; y < blocksHeight; y++) {
                    Block b = this.matrix.get(x).get(y);
                    if (b.getPosition() == 1) {// FRONT
                        if (b.getSprite() == 0) {
                            b.animatedSprite();
                        }
                        b.getDrawable().draw(canvas);
                    }
                }
            }
        }
    }
}