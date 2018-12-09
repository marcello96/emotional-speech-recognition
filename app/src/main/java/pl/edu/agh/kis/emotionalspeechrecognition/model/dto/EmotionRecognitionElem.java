package pl.edu.agh.kis.emotionalspeechrecognition.model.dto;

import com.google.gson.annotations.Expose;

import lombok.Data;
import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionType;

@Data
public class EmotionRecognitionElem {
    @Expose
    private EmotionType emotionType;
    @Expose
    private Float prediction;
}
