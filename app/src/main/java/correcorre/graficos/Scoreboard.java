package correcorre.graficos;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import correcorre.Main;
import correcorre.R;

public class Scoreboard {

    private Rect rNum0;//position score board
    private Rect rNum1;
    private Rect rNum2;
    private Rect rNum3;
    private Rect rNum4;
    private Rect rNum5;
    private Rect rNum6;
    private Rect rNum7;
    private Drawable dNum0;//just drawables
    private Drawable dNum1;
    private Drawable dNum2;
    private Drawable dNum3;
    private Drawable dNum4;
    private Drawable dNum5;
    private Drawable dNum6;
    private Drawable dNum7;
    private Drawable dNum8;
    private Drawable dNum9;
    private Rect rectNum;
    private int score;

    public Scoreboard(Main m, MatrixX ma) {

        this.dNum0 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_zero,null);
        this.dNum1 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_one,null);
        this.dNum2 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_two,null);
        this.dNum3 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_three,null);
        this.dNum4 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_four,null);
        this.dNum5 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_five,null);
        this.dNum6 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_six,null);
        this.dNum7 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_seven,null);
        this.dNum8 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_eight,null);
        this.dNum9 = VectorDrawableCompat.create(m.getResources(), R.drawable.n_nine,null);

        this.rNum7 = new Rect();
        this.rNum7.left = ma.getWidth() - ma.getSize()*3 -ma.getSize()/2;
        this.rNum7.right = ma.getWidth() - ma.getSize()*3;
        this.rNum7.top = ma.getSize();
        this.rNum7.bottom = ma.getSize()*2;

        this.rNum6 = new Rect();
        this.rNum6.left = ma.getWidth() - ma.getSize()*4;
        this.rNum6.right = ma.getWidth() - ma.getSize()*3 -ma.getSize()/2 ;
        this.rNum6.top = ma.getSize();
        this.rNum6.bottom = ma.getSize()*2;

        this.rNum5 = new Rect();
        this.rNum5.left = ma.getWidth() - ma.getSize()*4 -ma.getSize()/2;
        this.rNum5.right = ma.getWidth() - ma.getSize()*4 ;
        this.rNum5.top = ma.getSize();
        this.rNum5.bottom = ma.getSize()*2;

        this.rNum4 = new Rect();
        this.rNum4.left = ma.getWidth() - ma.getSize()*5 ;
        this.rNum4.right = ma.getWidth() - ma.getSize()*4 -ma.getSize()/2 ;
        this.rNum4.top = ma.getSize();
        this.rNum4.bottom = ma.getSize()*2;

        this.rNum3 = new Rect();
        this.rNum3.left = ma.getWidth() - ma.getSize()*5 -ma.getSize()/2;
        this.rNum3.right = ma.getWidth() - ma.getSize()*5  ;
        this.rNum3.top = ma.getSize();
        this.rNum3.bottom = ma.getSize()*2;

        this.rNum2 = new Rect();
        this.rNum2.left = ma.getWidth() - ma.getSize()*6 ;
        this.rNum2.right = ma.getWidth() - ma.getSize()*5 -ma.getSize()/2;
        this.rNum2.top = ma.getSize();
        this.rNum2.bottom = ma.getSize()*2;

        this.rNum1 = new Rect();
        this.rNum1.left = ma.getWidth() - ma.getSize()*6 -ma.getSize()/2;
        this.rNum1.right = ma.getWidth() - ma.getSize()*6 ;
        this.rNum1.top = ma.getSize();
        this.rNum1.bottom = ma.getSize()*2;

        this.rNum0 = new Rect();
        this.rNum0.left = ma.getWidth() - ma.getSize()*7;
        this.rNum0.right = ma.getWidth() - ma.getSize()*6 -ma.getSize()/2;
        this.rNum0.top = ma.getSize();
        this.rNum0.bottom = ma.getSize()*2;

        this.score = 0;
    }

    public void scoreAdd(int s) {
        this.score += s;
    }

    public void printScoreBoard(Canvas c) {

        String scoreString = Integer.toString(this.score);

        for (int x = 0; x < scoreString.length(); x++) {

            int posNum = scoreString.length() - x;//invertir posiciones
            if (posNum == 7) rectNum = this.rNum0;
            if (posNum == 6) rectNum = this.rNum1;
            if (posNum == 5) rectNum = this.rNum2;
            if (posNum == 4) rectNum = this.rNum3;
            if (posNum == 3) rectNum = this.rNum4;
            if (posNum == 2) rectNum = this.rNum5;
            if (posNum == 1) rectNum = this.rNum6;
            if (posNum == 0) rectNum = this.rNum7;

            if (Character.getNumericValue(scoreString.charAt(x)) == 0) {
                this.dNum0.setBounds(rectNum);
                this.dNum0.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 1) {
                this.dNum1.setBounds(rectNum);
                this.dNum1.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 2) {
                this.dNum2.setBounds(rectNum);
                this.dNum2.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 3) {
                this.dNum3.setBounds(rectNum);
                this.dNum3.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 4) {
                this.dNum4.setBounds(rectNum);
                this.dNum4.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 5) {
                this.dNum5.setBounds(rectNum);
                this.dNum5.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 6) {
                this.dNum6.setBounds(rectNum);
                this.dNum6.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 7) {
                this.dNum7.setBounds(rectNum);
                this.dNum7.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 8) {
                this.dNum8.setBounds(rectNum);
                this.dNum8.draw(c);
            }
            else if (Character.getNumericValue(scoreString.charAt(x)) == 9) {
                this.dNum9.setBounds(rectNum);
                this.dNum9.draw(c);
            }
        }
    }
}