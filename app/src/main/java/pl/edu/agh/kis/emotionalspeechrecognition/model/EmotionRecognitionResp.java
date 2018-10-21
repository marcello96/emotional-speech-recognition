package pl.edu.agh.kis.emotionalspeechrecognition.model;

import com.google.gson.annotations.Expose;

public class EmotionRecognitionResp {
    @Expose
    private String emotion;

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}
