package pl.edu.agh.kis.emotionalspeechrecognition.utils;

import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionType;

public class EmotionTypeToColorConverter {

    public static Colors map(EmotionType emotionType) {
        switch(emotionType) {
            case ANGRY:
                return Colors.RED; //Colors.PURPLE;
            case CALM:
                return Colors.ORANGE; //Colors.BLUE;
            case DISGUST:
                return Colors.PURPLE; //Colors.BLUE;
            case FEARFUL:
                return Colors.LIGHT_GREEN; //Colors.PURPLE;
            case HAPPY:
                return Colors.YELLOW; //Colors.PURPLE;
            case NEUTRAL:
                return Colors.GREEN; //Colors.BLUE;
            case SAD:
                return Colors.LIGHT_BLUE; //Colors.PURPLE;
            case SURPRISED:
                return Colors.BLUE;
            default:
                throw new IllegalArgumentException("This code shouldn't be executed!");
        }
    }
}
