package correcorre.graficos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import correcorre.Main;
import correcorre.enemy.Plant;
import correcorre.enemy.Spider;
import correcorre.enemy.Enemy;
import correcorre.penguin.Penguin;
import correcorre.scenario.Block;
import correcorre.scenario.Scenario;
import correcorre.weapons.Bullet;
import correcorre.weapons.Explosion;
import correcorre.weapons.Ferret;
import correcorre.weapons.Chicken;
import correcorre.weapons.Unicorn;
import correcorre.weapons.Weapon;

public class MatrixX {

    //throw new java.lang.RuntimeException("error block"); <<usar para casting blocks

    public volatile ArrayList<ArrayList<Block>> matrix = null;
    public volatile ArrayList<ArrayList<ArrayList>> matrixScenario = null;
    public volatile ArrayList<Enemy> enemyList = null;
    public volatile ArrayList<Weapon> weaponList = null;
    public volatile ArrayList<Bullet> bulletList = null;
    public volatile ArrayList<Explosion> explosionList = null;

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
    private int relativeOffsetX = 0;
    private int relativeOffsetY = 0;//-5
    private static Main main;
    private static Scenario scenario;
    private static MyCanvas myCanvas;
    private static Penguin penguin;
    private static Scoreboard scoreboard;
    private Context context;
    private int[] scenarioStart;//debe ser el offset
    private int tempo;

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
    public void setScoreboard(Scoreboard b) { scoreboard = b; }
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
                Block newB = new Block(this.context,"block",1,1,"empty");

                Rect r = newB.getRect();
                r.left = (x * size) - offsetX2;
                r.right = (x * size) - offsetX;
                r.top = (y * size) - offsetY2;
                r.bottom = (y * size) - offsetY;

                generateFromScenario(newB,x+relativeOffsetX,y+relativeOffsetY);

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
                String enemyType = (String) this.matrixScenario.get(x).get(y).get(2);
                int solid = (int) this.matrixScenario.get(x).get(y).get(3);
                String weaponType = (String) this.matrixScenario.get(x).get(y).get(4);
                int sprite = (int) this.matrixScenario.get(x).get(y).get(5);
                String blockType = (String) this.matrixScenario.get(x).get(y).get(6);

                if (enemyType != null) old.setType(type,enemyType);
                if (weaponType != null) old.setType(type,weaponType);
                if (blockType != null) old.setType(type,blockType);
                old.setPosition(position);
                old.setSolid(solid);
                old.setSprite(sprite);


                if (type.equals("enemy")) {

                    this.matrixScenario.get(x).get(y).set(0, "block");//reset to not more spawns.
                    this.matrixScenario.get(x).get(y).set(6, "empty");
                    this.matrixScenario.get(x).get(y).set(2,null);
                    old.setType(type,enemyType);
                    generateEnemy(old);
                    old.setType("block","empty");//reset from matrixX for not double spawn.


                }
                if (type.equals("weapon")) {

                    this.matrixScenario.get(x).get(y).set(0, "block");//reset to not more spawns.
                    this.matrixScenario.get(x).get(y).set(6, "empty");
                    this.matrixScenario.get(x).get(y).set(4,null);
                    old.setType(type,weaponType);
                    generateWeapon(old);
                    old.setType("block","empty");//reset from matrixX for not double spawn.


                }
                newType = true;
            }
        }
        if (!newType) {
            old.setType("block","dirt");//if null block
            old.setSprite(1);
            old.setSolid(0);
            old.setPosition(0);
        }
    }

    public synchronized void generateBullet(Bullet b) {
        if (this.bulletList == null) {
            this.bulletList = new ArrayList<Bullet>();
        }
        this.bulletList.add(b);
    }

    private synchronized void generateWeapon(Block old) {
        if (this.weaponList == null) {
            this.weaponList = new ArrayList<Weapon>();
        }
        if (old.getWeaponType().equals("chicken")) {
            Chicken chicken = new Chicken(main,this,old);
            this.weaponList.add(chicken);
        }
        if (old.getWeaponType().equals("ferret")) {
            Ferret ferret = new Ferret(main,this,old);
            this.weaponList.add(ferret);
        }
        if (old.getWeaponType().equals("unicorn")) {
            Unicorn unicorn = new Unicorn(main,this,old);
            this.weaponList.add(unicorn);
        }
    }

    private synchronized void generateEnemy(Block old) {

        if (this.enemyList == null) {
            this.enemyList = new ArrayList<Enemy>();
        }

        if (old.getEnemyType().equals("spider")) {
            Spider spider = new Spider(main, this, old.getRect().top - (this.size / 2), old.getRect().left - (this.size / 2 + this.size), this.size * 2, this.size);
            this.enemyList.add(spider);
        }
        if (old.getEnemyType().equals("plant")) {
            Plant plant = new Plant(main, this, old.getRect().top - (this.size), old.getRect().left - (this.size / 2 + this.size), this.size * 2, this.size*3);
            this.enemyList.add(plant);
        }
    }

    public synchronized boolean getPenguinImmunity() { return penguin.getImmunity(); }
    public synchronized void setPenguinImmunity(Boolean i) { penguin.setImmunity(i); }
    public synchronized int getPenguinLife() { return scoreboard.getLife(); }//because the life is in scoreboard
    public synchronized void setPenguinLife(int l) { scoreboard.setLife(l); }//because the life is in scoreboard

    public synchronized void generateExplosion(Rect enemy) {

        Explosion explosion = new Explosion(main,this,enemy);
        if (this.explosionList == null) {
            this.explosionList = new ArrayList<Explosion>();
        }
        if (this.explosionList != null) {
            this.explosionList.add(explosion);
        }
    }
    public synchronized void calcEnemyMove() {
        if (this.enemyList != null) {
            for (int x = 0; x < this.enemyList.size(); x++) {
                if (!this.enemyList.get(x).checkCollisionEnemy()) {//check Wall collision and out-screen;
                    this.enemyList.get(x).randomControls();//random Controls;
                    this.enemyList.get(x).moveSprite();//change sprite
                    this.enemyList.get(x).calcAcelerationEnemy();//calc Acceleration
                    this.enemyList.get(x).moveEnemyActualSpeed();//move it
                    this.enemyList.get(x).checkCollisionPenguin(penguin.getRect());//check if penguin collision
                }
            }
        }
    }
    public synchronized void calcBulletMove() {
        if (this.bulletList != null) {
            for (int x = 0; x < this.bulletList.size(); x++) {
                this.bulletList.get(x).moveBulletSprite();//first move the sprite
                this.bulletList.get(x).moveBulletActualSpeed();//move it
                if (!this.bulletList.get(x).checkColissionBullet()) {//check Wall Collision
                    if (this.bulletList.get(x).checkColissionBulletEnemy()) scoreboard.scoreAdd(50);//check enemy Collision
                }
            }
        }
    }
    public synchronized void calcExplosionSprite() {
        if (this.explosionList != null) {
            for (int x = 0; x < this.explosionList.size(); x++) {
                this.explosionList.get(x).moveExplosionSprite();
            }
        }
    }

    public synchronized void printBullets(Canvas c) {
        if (this.bulletList != null) {
            for (int x = 0; x < this.bulletList.size(); x++) {
                this.bulletList.get(x).printBullet(c);
            }
        }
    }
    public synchronized void printEnemys(Canvas c) {

        if (this.enemyList != null) {
            for (int x = 0; x < this.enemyList.size(); x++) {
                this.enemyList.get(x).printEnemy(c);
            }
        }
    }
    public synchronized void printWeapons(Canvas c) {
        if (penguin.getWeapon() != null) penguin.getWeapon().printWeapon(c);
        if (this.weaponList != null) {
            for (int x = 0; x < this.weaponList.size(); x++) {
                this.weaponList.get(x).printWeapon(c);
            }
        }
        //no needed because weapon is deleted from matrixX weapon list.
        /*if (this.weaponList != null) {
            if (penguin.getWeapon() != null) {
                penguin.getWeapon().printWeapon(c);//first Print penguin weapon to be back
            }
            for (int x = 0; x < this.weaponList.size(); x++) {
                if (penguin.getWeapon() != null) {
                    if (this.weaponList.get(x) != penguin.getWeapon()) {//no print if is weapon of penguin
                        this.weaponList.get(x).printWeapon(c);
                    }
                } else {//print if is in floor
                    this.weaponList.get(x).printWeapon(c);
                }
            }
        }*/
    }
    public synchronized void printExplosions(Canvas c) {
        if (this.explosionList != null) {
            for (int x = 0; x < this.explosionList.size(); x++) {
                this.explosionList.get(x).printExplosion(c);
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
            if (this.weaponList != null) {
                for (int x = 0; x < this.weaponList.size(); x++) {
                    if (!this.weaponList.get(x).getPenguin() && !this.weaponList.get(x).getEnemy()) {
                        if (!main.penguinX) {
                            this.weaponList.get(x).moveX(speed[0]);
                        }
                        if (!main.penguinY) {
                            this.weaponList.get(x).moveY(speed[1]);
                        }
                    }
                }
            }
            if (this.bulletList != null) {
                for (int x = 0; x < this.bulletList.size(); x++) {
                    if (!main.penguinX) {
                        this.bulletList.get(x).moveX(speed[0]);
                    }
                    if (!main.penguinY) {
                        this.bulletList.get(x).moveY(speed[1]);
                    }
                }
            }
            if (this.explosionList != null) {
                for (int x = 0; x < this.explosionList.size(); x++) {
                    if (!main.penguinX) {
                        this.explosionList.get(x).moveX(speed[0]);
                    }
                    if (!main.penguinY) {
                        this.explosionList.get(x).moveY(speed[1]);
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
    }

    // IMPRIMIR MATRIXX #########################################################################

    public synchronized void printMatrixBack(Canvas canvas) {//SINCRO CON MOVEMATRIX
        tempo++;//animated sprites
        if (tempo >= 25) tempo = 0; //animated sprites

        if (this.matrix != null) {
            for (int x = 0; x < blocksWidth; x++) {
                for (int y = 0; y < blocksHeight; y++) {
                    Block b = this.matrix.get(x).get(y);
                    if (b.getPosition() == 0) {//BACK
                        if (b.getSprite() == 0) {
                            b.animatedSprite(tempo);
                        }
                        b.getDrawable().draw(canvas);
                    }
                }
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
                            b.animatedSprite(tempo);
                        }
                        b.getDrawable().draw(canvas);
                    }
                }
            }
        }
    }
}