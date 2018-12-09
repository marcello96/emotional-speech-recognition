package pl.edu.agh.kis.emotionalspeechrecognition.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

public class EmotionBoxElem {

    private static final float FACTOR = 1.6f;
    private static final int LOW_OPAQUE = 0x77;
    private static final int HIGH_OPAQUE = 0xff;

    private Path mPath;
    private Paint mPaint;
    private Paint textPaint;
    private String text;
    private float rotation;

    public EmotionBoxElem(float x, float y, float side, int elemNo, int color, String text, int amountOfBoxes) {

        this.text = text;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setAlpha(LOW_OPAQUE);

        double halfOfAngle = Math.PI / amountOfBoxes;
        double angle = 2 * halfOfAngle;
        double diagonalLength = computeLongDiagonal(side, angle);

        rotation = (float)(360.0 / amountOfBoxes * elemNo);

        mPath = new Path();
        mPath.moveTo(x , y);
        mPath.lineTo((float)(x - side * FACTOR * Math.sin(halfOfAngle)), (float)(y - side * FACTOR * Math.cos(halfOfAngle)));
        mPath.lineTo(x, (float)(y - diagonalLength));
        mPath.lineTo((float)(x + side * FACTOR * Math.sin(halfOfAngle)), (float)(y - side * FACTOR * Math.cos(halfOfAngle)));
        mPath.close();

        Matrix mMatrix = new Matrix();
        mMatrix.postRotate(rotation, x, y);
        mPath.transform(mMatrix);
    }

    public void darken() {
        mPaint.setAlpha(HIGH_OPAQUE);
        textPaint.setColor(Color.WHITE);
    }

    public void lighten() {
        mPaint.setAlpha(LOW_OPAQUE);
        textPaint.setColor(Color.BLACK);
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        canvas.save();
        canvas.rotate(rotation - 90, 400f, 400f);
        canvas.drawText(text, 600, 405, textPaint);
        canvas.restore();
    }

    private double computeLongDiagonal(float side, double angle) {
        return Math.sqrt(2 * Math.pow(side, 2) * (1 + Math.cos(angle))); // based on Law of cosines
    }
}
