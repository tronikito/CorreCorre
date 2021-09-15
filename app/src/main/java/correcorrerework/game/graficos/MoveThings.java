package correcorrerework.game.graficos;

import static correcorrerework.ResourcesClass.*;

public abstract class MoveThings {

    //There every thing that move independent

    /***Includes
     Sprite animation
     Random moviment
     Gravity Aceleration
     Colissions
     **/

    public static synchronized void calcMoves() {
        calcEnemyMove();
        calcBulletMove();
        calcExplosionSprite();
    }

    //because explosion not move independent of matrixX, only change sprite.
    public static synchronized void calcExplosionSprite() {
        if (explosionList != null) {
            for (int x = 0; x < explosionList.size(); x++) {
                explosionList.get(x).moveExplosionSprite();
            }
        }
    }

    public static synchronized void calcEnemyMove() {
        if (enemyList != null) {
            for (int x = 0; x < enemyList.size(); x++) {
                if (!enemyList.get(x).checkCollisionEnemy()) {//check Wall collision and out-screen;
                    enemyList.get(x).randomControls();//random Controls;
                    enemyList.get(x).moveSprite();//change sprite
                    enemyList.get(x).calcAcelerationEnemy();//calc Acceleration
                    enemyList.get(x).moveEnemyActualSpeed();//move it
                    enemyList.get(x).checkCollisionPenguin(penguin.getRect());//check if penguin collision
                }
            }
        }
    }

    public static synchronized void calcBulletMove() {
        if (bulletList != null) {
            for (int x = 0; x < bulletList.size(); x++) {
                bulletList.get(x).moveBulletSprite();//first move the sprite
                bulletList.get(x).moveBulletActualSpeed();//move it
                if (!bulletList.get(x).checkColissionBullet()) {//check Wall Collision
                    if (bulletList.get(x).checkColissionBulletEnemy()) scoreboard.scoreAdd(50);//check enemy Collision
                }
            }
        }
    }
}
