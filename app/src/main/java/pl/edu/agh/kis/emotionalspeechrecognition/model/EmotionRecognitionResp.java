package pl.edu.agh.kis.emotionalspeechrecognition.model;

import com.google.gson.annotations.Expose;

public class EmotionRecognitionResp {
    @Expose
    private EmotionType emotion;

    public EmotionType getEmotion() {
        return emotion;
    }

    public void setEmotion(EmotionType emotion) {
        this.emotion = emotion;
    }
}
