package pl.edu.agh.kis.emotionalspeechrecognition.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionType;

public class EmotionView extends View {
    private EmotionBox emotionBox;

    private Path mPath;
    private Paint mPaint;

    public EmotionView(Context context) {
        super(context);

        init(null);
    }

    public EmotionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public EmotionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public EmotionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet attributeSet) {

        emotionBox = new EmotionBox(400f, 400f, 200f);
    }

    public void darkenEmotionBox(EmotionType emotion) {
        emotionBox.darkenEmotion(emotion);

        postInvalidate();
    }

    public void lightenAllEmotion() {
        emotionBox.lightenAllEmotions();

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        emotionBox.draw(canvas);
    }
}
