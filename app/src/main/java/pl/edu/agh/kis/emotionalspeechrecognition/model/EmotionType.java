package pl.edu.agh.kis.emotionalspeechrecognition.model;

import com.google.gson.annotations.SerializedName;

public enum EmotionType {
    @SerializedName("neutral")
    NEUTRAL,
    @SerializedName("fearful")
    FEARFUL,
    @SerializedName("surprised")
    SURPRISED,
    @SerializedName("sad")
    SAD,
    @SerializedName("disgust")
    DISGUST,
    @SerializedName("angry")
    ANGRY,
    @SerializedName("calm")
    CALM,
    @SerializedName("happy")
    HAPPY;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
