package pl.edu.agh.kis.emotionalspeechrecognition.model;

import com.google.gson.annotations.SerializedName;

public enum EmotionType {
    @SerializedName("0")
    NEUTRAL,
    @SerializedName("1")
    CALM,
    @SerializedName("2")
    HAPPY,
    @SerializedName("3")
    SAD,
    @SerializedName("4")
    ANGRY,
    @SerializedName("5")
    FEARFUL,
    @SerializedName("6")
    DISGUST,
    @SerializedName("7")
    SURPRISED
}
