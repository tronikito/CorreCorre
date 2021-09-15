package correcorrerework.game.graficos;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import correcorrerework.game.scenario.Block;
import static correcorrerework.ResourcesClass.*;

public class MatrixXMovePrint {

    protected int offsetnW;
    protected int offsetX;
    protected int offsetX2;
    protected int offsetnH;
    protected int offsetY;
    protected int offsetY2;

    int[] relativeOffSet = {-5,-10};

    public static synchronized void printBullets(Canvas c) {
        if (bulletList != null) {
            for (int x = 0; x < bulletList.size(); x++) {
                bulletList.get(x).printBullet(c);
            }
        }
    }
    public static synchronized void printEnemys(Canvas c) {

        if (enemyList != null) {
            for (int x = 0; x < enemyList.size(); x++) {
                enemyList.get(x).printEnemy(c);
            }
        }
    }
    public static synchronized void printWeapons(Canvas c) {
        if (penguin.getWeapon() != null) penguin.getWeapon().printWeapon(c);
        if (weaponList != null) {
            for (int x = 0; x < weaponList.size(); x++) {
                weaponList.get(x).printWeapon(c);
            }
        }
    }
    public static synchronized void printExplosions(Canvas c) {
        if (explosionList != null) {
            for (int x = 0; x < explosionList.size(); x++) {
                explosionList.get(x).printExplosion(c);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public  synchronized void printMatrixFront(Canvas canvas) {//SINCRO CON MOVEMATRIX
        if (matrixList != null) {
            for (int x = 0; x < blocksWidth; x++) {
                for (int y = 0; y < blocksHeight; y++) {
                    Block b = matrixList.get(x).get(y);
                    if (b.getPosition() == 1) {// FRONT
                        if (b.getSprite() == 0) {
                            b.animatedSprite(tempo);
                        }
                        if (b.getDrawable() != null) {

                            // DrawableCompat.setTint(b.getDrawable(), Color.BLACK);
                            //b.getDrawable().setColorFilter(R.color.colorPrimary, PorterDuff.Mode.MULTIPLY);
                            b.getDrawable().draw(canvas);
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public synchronized void printMatrixBack(Canvas canvas) {//SINCRO CON MOVEMATRIX
        tempo++;//animated sprites
        if (tempo >= 25) tempo = 0; //animated sprites

        if (matrixList != null) {
            for (int x = 0; x < blocksWidth; x++) {
                for (int y = 0; y < blocksHeight; y++) {
                    Block b = matrixList.get(x).get(y);
                    if (b.getPosition() == 0) {//BACK
                        if (b.getSprite() == 0) {
                            b.animatedSprite(tempo);
                        }
                        if (b.getDrawable() != null) {

                            //int color = Color.parseColor("#AE6118"); //The color u want
                            //b.getDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                            b.getDrawable().draw(canvas);
                        }
                    }
                }
            }
        }
    }

    //MOVE

    public synchronized void moveMatrix(int[] speed) {

        if (!mainGame.penguinX || !mainGame.penguinY) {
            for (int x = 0; x < matrixList.size(); x++) {
                for (int y = 0; y < matrixList.get(x).size(); y++) {
                    Block b = matrixList.get(x).get(y);

                    if (!mainGame.penguinX) {
                        b.moveX(speed[0]);
                    }
                    if (!mainGame.penguinY) {
                        b.moveY(speed[1]);
                    }
                }
            }

            frontground.moveFrontground(speed); //need FIX
            moveEnemys(speed);
            moveWeapons(speed);
            moveBullets(speed);
            moveExplosions(speed);

            matrixX.checkOffset();
        }
    }
    public synchronized void moveBullets(int[] speed) {
        if (bulletList != null) {
            for (int x = 0; x < bulletList.size(); x++) {
                if (!mainGame.penguinX) {
                    bulletList.get(x).moveX(speed[0]);
                }
                if (!mainGame.penguinY) {
                    bulletList.get(x).moveY(speed[1]);
                }
            }
        }
    }
    public synchronized void moveEnemys(int[] speed) {
        if (enemyList != null) {
            for (int x = 0; x < enemyList.size(); x++) {
                if (!mainGame.penguinX) {
                    enemyList.get(x).moveX(speed[0]);
                }
                if (!mainGame.penguinY) {
                    enemyList.get(x).moveY(speed[1]);
                }
            }
        }
    }
    public synchronized void moveWeapons(int[] speed) {
        if (weaponList != null) {
            for (int x = 0; x < weaponList.size(); x++) {
                if (!weaponList.get(x).getPenguin() && !weaponList.get(x).getEnemy()) {
                    if (!mainGame.penguinX) {
                        weaponList.get(x).moveX(speed[0]);
                    }
                    if (!mainGame.penguinY) {
                        weaponList.get(x).moveY(speed[1]);
                    }
                }
            }
        }
    }
    public synchronized void moveExplosions(int[] speed) {
        if (explosionList != null) {
            for (int x = 0; x < explosionList.size(); x++) {
                if (!mainGame.penguinX) {
                    explosionList.get(x).moveX(speed[0]);
                }
                if (!mainGame.penguinY) {
                    explosionList.get(x).moveY(speed[1]);
                }
            }
        }
    }



}
