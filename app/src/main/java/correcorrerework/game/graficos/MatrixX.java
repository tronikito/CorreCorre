package correcorrerework.game.graficos;

import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;

import correcorrerework.game.enemy.Plant;
import correcorrerework.game.enemy.Spider;
import correcorrerework.game.enemy.Enemy;
import correcorrerework.game.scenario.Block;
import correcorrerework.game.scenario.BlockSprites;
import correcorrerework.game.weapons.Bullet;
import correcorrerework.game.weapons.Chicken;
import correcorrerework.game.weapons.Explosion;
import correcorrerework.game.weapons.Ferret;
import correcorrerework.game.weapons.Unicorn;
import correcorrerework.game.weapons.Weapon;

import static correcorrerework.ResourcesClass.*;


public class MatrixX extends MatrixXMovePrint {

    public boolean generateMatrix = false;

    public MatrixX() {

        double size = Math.ceil(widthScreen / blocksWidth);
        if (size % 2 != 0) size++;
        sizeDot = (int) size;

        int nW = blocksWidth * sizeDot;
        this.offsetnW = ((nW - widthScreen) / 2);//where start block at spawn
        this.offsetX = this.offsetnW + sizeDot;//where to spawn block
        blocksWidth += 2;//make margin for hidden move of blocks
        this.offsetX2 = this.offsetnW + sizeDot * 2; //new offset to check out of map block.

        //Calc new Height, and num blocksHeight matrix;

        double calcblocksHeight = Math.ceil(heightScreen / sizeDot);
        if (calcblocksHeight % 2 != 0) calcblocksHeight++;
        blocksHeight = (int) calcblocksHeight;
        int nH = blocksHeight * sizeDot;
        this.offsetnH = (nH - heightScreen) / 2;//where start block at spawn
        this.offsetY = this.offsetnH + sizeDot;//where to spawn block
        blocksHeight += 2;//make margin for hidden move of blocks
        this.offsetY2 = this.offsetnH + sizeDot * 2;//new offset to check out of map block.

        generateNewMatrix();
    }

    // GENERATE ################################################################################

    public synchronized void generateNewMatrix() {

        matrixList = new ArrayList<>();
        ArrayList<Block> column;

        for (int x = 0; x < blocksWidth; x++) {
            column = new ArrayList<>();
            for (int y = 0; y < blocksHeight; y++) {
                Block newB = new Block("block", 1, 1, "empty");

                Rect r = newB.getRect();
                r.left = (x * sizeDot) - offsetX2;
                r.right = (x * sizeDot) - offsetX;
                r.top = (y * sizeDot) - offsetY2;
                r.bottom = (y * sizeDot) - offsetY;

                generateFromScenario(newB, x + relativeOffSet[0], y + relativeOffSet[1]);

                column.add(newB);
            }
            matrixList.add(column);
        }
        generateMatrix = true;
    }

    private synchronized void generateFromScenario(Block old, int x, int y) {
        boolean newType = false;
        if (x < matrixScenario.size() && x >= 0) {
            if (y < matrixScenario.get(x).size() && y >= 0) {

                String type = (String) matrixScenario.get(x).get(y).get(0);
                int position = (int) matrixScenario.get(x).get(y).get(1);
                String enemyType = (String) matrixScenario.get(x).get(y).get(2);
                int solid = (int) matrixScenario.get(x).get(y).get(3);
                String weaponType = (String) matrixScenario.get(x).get(y).get(4);
                int sprite = (int) matrixScenario.get(x).get(y).get(5);
                String blockType = (String) matrixScenario.get(x).get(y).get(6);

                old.setSprite(sprite);
                old.setSolid(solid);
                old.setPosition(position);

                if (enemyType != null) old.setType(type, enemyType);
                if (weaponType != null) old.setType(type, weaponType);
                if (blockType != null) old.setType(type, blockType);

                if (type.equals("enemy")) {

                    matrixScenario.get(x).get(y).set(0, "block");//reset to not more spawns.
                    matrixScenario.get(x).get(y).set(6, "empty");
                    matrixScenario.get(x).get(y).set(2, null);
                    old.setType(type, enemyType);
                    generateEnemy(old);
                    old.setType("block", "empty");//reset from matrixX for not double spawn.

                }
                if (type.equals("weapon")) {

                    matrixScenario.get(x).get(y).set(0, "block");//reset to not more spawns.
                    matrixScenario.get(x).get(y).set(6, "empty");
                    matrixScenario.get(x).get(y).set(4, null);
                    old.setType(type, weaponType);
                    generateWeapon(old);
                    old.setType("block", "empty");//reset from matrixX for not double spawn.

                }
                newType = true;
            }
        }
        if (!newType) {

            old.setSprite(5);
            old.setSolid(0);
            old.setPosition(0);

            old.setType("block", "dirt");//if null block
        }
    }

    public synchronized void generateBullet(Bullet b) {
        if (bulletList == null) {
            bulletList = new ArrayList<Bullet>();
        }
        bulletList.add(b);
    }

    private synchronized void generateWeapon(Block old) {
        if (weaponList == null) {
            weaponList = new ArrayList<Weapon>();
        }
        if (old.getWeaponType().equals("chicken")) {
            Chicken chicken = new Chicken(old);
            weaponList.add(chicken);
        }
        if (old.getWeaponType().equals("ferret")) {
            Ferret ferret = new Ferret(old);
            weaponList.add(ferret);
        }
        if (old.getWeaponType().equals("unicorn")) {
            Unicorn unicorn = new Unicorn(old);
            weaponList.add(unicorn);
        }
    }

    private synchronized void generateEnemy(Block old) {

        if (enemyList == null) {
            enemyList = new ArrayList<Enemy>();
        }

        if (old.getEnemyType().equals("spider")) {
            Spider spider = new Spider(old.getRect().left - (sizeDot / 2) + sizeDot, old.getRect().top - (sizeDot / 2), sizeDot * 2, sizeDot);
            enemyList.add(spider);
        }
        if (old.getEnemyType().equals("plant")) {//revert X and Y
            Plant plant = new Plant(old.getRect().top - (sizeDot), old.getRect().left - (sizeDot / 2 + sizeDot), sizeDot * 2, sizeDot * 3);
            enemyList.add(plant);
        }
    }

    public synchronized void generateExplosion(Rect enemy) {

        Explosion explosion = new Explosion(enemy);
        if (explosionList == null) {
            explosionList = new ArrayList<Explosion>();
        }
        if (explosionList != null) {
            explosionList.add(explosion);
        }
    }

    // MOVE MATRIXX ############################################################################

    public synchronized void checkOffset() {

        int[] foundPositions = {0, 0, 0, 0};

        boolean top = checkOffSetTop(foundPositions);
        boolean left = checkOffSetLeft(foundPositions);
        boolean right = checkOffSetRight(foundPositions);
        boolean bottom = checkOffSetBottom(foundPositions);

        if (top) moveMatrixRowTop(foundPositions);
        if (left) moveMatrixColumnLeft(foundPositions);
        if (right) moveMatrixColumnRight(foundPositions);
        if (bottom && penguinSpeed[1] < 0) moveMatrixRowBottom(foundPositions);

        if (left || right || top || bottom) recalculateDrawable(foundPositions);
    }

    public synchronized boolean checkOffSetTop(int[] foundPositions) {
        int lower = heightScreen + this.offsetY2 * 10;
        for (int y = 0; y < matrixList.get(0).size(); y++) {
            Block b = matrixList.get(0).get(y);
            if (b.getRect().top < lower) {
                foundPositions[0] = y;
                lower = b.getRect().top;
            }
            if (b.getRect().top < (-this.offsetY2)) {
                relativeOffSet[1] += 1;
                return true;
            }
        }
        return false;
    }

    public synchronized boolean checkOffSetLeft(int[] foundPositions) {
        int lower = widthScreen + offsetX2 * 10;
        for (int x = 0; x < matrixList.size(); x++) {
            Block b = matrixList.get(x).get(0);
            if (b.getRect().left < lower) {
                foundPositions[1] = x;
                lower = b.getRect().left;
            }
            if (b.getRect().left < -this.offsetX2) {
                relativeOffSet[0] += 1;
                return true;
            }
        }
        return false;
    }

    public synchronized boolean checkOffSetRight(int[] foundPositions) {
        foundPositions[2] = foundPositions[1] - 1;
        if (foundPositions[2] == -1) foundPositions[2] = blocksWidth - 1;
        Block b = matrixList.get(foundPositions[2]).get(0);
        if (b.getRect().right > widthScreen + this.offsetX2) {
            relativeOffSet[0] -= 1;
            return true;
        }
        return false;
    }

    public synchronized boolean checkOffSetBottom(int[] foundPositions) {
        foundPositions[3] = foundPositions[0] - 1;
        if (foundPositions[3] == -1) foundPositions[3] = blocksHeight - 1;
        Block b = matrixList.get(foundPositions[1]).get(foundPositions[3]);
        if (b.getRect().bottom > heightScreen + this.offsetY2) {
            relativeOffSet[1] -= 1;
            return true;
        }
        return false;
    }

    public synchronized void moveMatrixColumnLeft(int[] foundPositions) {
        int calcY = foundPositions[0];
        for (int y = 0; y < blocksHeight; y++) {
            Block b = matrixList.get(foundPositions[1]).get(calcY);
            int padding = b.getRect().left + this.offsetX2;
            RepositionRect(b, padding, 0);
            calcY++;
            if (calcY == blocksHeight) calcY = 0;
        }
        foundPositions[1] += 1;
        if (foundPositions[1] == blocksWidth) foundPositions[1] = 0;
    }
    public synchronized void moveMatrixColumnRight(int[] foundPositions) {
        int calcY = foundPositions[0];
        for (int y = 0; y < blocksHeight; y++) {
            Block b = matrixList.get(foundPositions[2]).get(calcY);
            int padding = ((widthScreen + offsetX) - b.getRect().right);
            RepositionRect(b, padding, 2);
            calcY++;
            if (calcY == blocksHeight) calcY = 0;
        }
        foundPositions[1] -= 1;
        if (foundPositions[1] == -1) foundPositions[1] = blocksWidth - 1;
    }

    public synchronized void moveMatrixRowTop(int[] foundPositions) {
        int calcX = foundPositions[1];
        for (int x = 0; x < blocksWidth; x++) {
            Block b = matrixList.get(calcX).get(foundPositions[0]);
            int padding = (b.getRect().top + offsetY2);
            RepositionRect(b, padding, 1);
            calcX++;
            if (calcX >= blocksWidth) calcX = 0;
        }
        foundPositions[0] += 1;
        if (foundPositions[0] == blocksHeight) foundPositions[0] = 0;
    }

    public synchronized void moveMatrixRowBottom(int[] foundPositions) {
        int calcX = foundPositions[1];
        for (int x = 0; x < blocksWidth; x++) {
            Block b = matrixList.get(calcX).get(foundPositions[3]);
            int padding = ((heightScreen + offsetY2) - b.getRect().bottom);
            RepositionRect(b, padding, 3);
            calcX++;
            if (calcX >= blocksWidth) calcX = 0;
        }
        foundPositions[0] -= 1;
        if (foundPositions[0] == -1) foundPositions[0] = blocksHeight - 1;
    }

    public synchronized void recalculateDrawable(int[] foundPositions) {

        int calcX = foundPositions[1];
        int calcY = foundPositions[0];

        for (int x = 0; x < blocksWidth; x++) {
            for (int y = 0; y < blocksHeight; y++) {
                generateFromScenario(matrixList.get(calcX).get(calcY), x + relativeOffSet[0], y + relativeOffSet[1]);
                calcY++;
                if (calcY == blocksHeight) calcY = 0;
            }
            calcX++;
            if (calcX == blocksWidth) calcX = 0;
        }
    }

    private synchronized void RepositionRect(Block b, int padding, int pos) {

        padding--; //fixbugs

        if (pos == 0) {
            b.getRect().left = (widthScreen + offsetnW) + padding;
            b.getRect().right = (widthScreen + offsetX) + padding;
        }
        if (pos == 1) {
            b.getRect().top = (heightScreen + offsetnH) + padding;
            b.getRect().bottom = (heightScreen + offsetY) + padding;
        }
        if (pos == 2) {
            b.getRect().left = -offsetX2 - padding;
            b.getRect().right = -offsetX - padding;
        }
        if (pos == 3) {
            b.getRect().top = -offsetY - padding;
            b.getRect().bottom = -offsetnH - padding;
        }
    }


    // IMPRIMIR MATRIXX #########################################################################

    public synchronized void printBack(Canvas c) {
        printMatrixBack(c);
    }
    public synchronized void printFront(Canvas c) {
        printWeapons(c);
        printEnemys(c);
        printBullets(c);
        printMatrixFront(c);
        printExplosions(c);
    }
}