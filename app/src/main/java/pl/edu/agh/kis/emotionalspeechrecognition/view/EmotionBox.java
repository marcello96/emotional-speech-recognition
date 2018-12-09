package pl.edu.agh.kis.emotionalspeechrecognition.view;

import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionType;
import pl.edu.agh.kis.emotionalspeechrecognition.utils.EmotionTypeToColorConverter;

public class EmotionBox {
    private static final int AMOUNT_OF_EMOTION = EmotionType.values().length;

    private Map<EmotionType, EmotionBoxElem> boxMap = new HashMap<>();

    public EmotionBox(float x, float y, float side) {
        int i = 0;
        for(EmotionType emotionType : EmotionType.values()) {
            boxMap.put(emotionType, new EmotionBoxElem(x, y, side, i, EmotionTypeToColorConverter.map(emotionType).getHex(), emotionType.toString(), AMOUNT_OF_EMOTION));
            ++i;
        }
    }

    public void draw(Canvas canvas) {
        boxMap.values().forEach(e -> e.draw(canvas));
    }

    public void darkenEmotion(EmotionType emotion) {
        for (EmotionType emotionKey : boxMap.keySet()) {
            if (emotionKey.equals(emotion)) {
                boxMap.get(emotionKey).darken();
            } else {
                boxMap.get(emotionKey).lighten();
            }
        }
    }
}
