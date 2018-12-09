package pl.edu.agh.kis.emotionalspeechrecognition.utils;

import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionType;

public class EmotionTypeToColorConverter {

    public static Colors map(EmotionType emotionType) {
        switch(emotionType) {
            case ANGRY:
                return Colors.RED;
            case CALM:
                return Colors.ORANGE;
            case DISGUST:
                return Colors.PURPLE;
            case FEARFUL:
                return Colors.LIGHT_GREEN;
            case HAPPY:
                return Colors.YELLOW;
            case NEUTRAL:
                return Colors.GREEN;
            case SAD:
                return Colors.LIGHT_BLUE;
            case SURPRISED:
                return Colors.BLUE;
            default:
                throw new IllegalArgumentException("This code shouldn't be executed!");
        }
    }
}
