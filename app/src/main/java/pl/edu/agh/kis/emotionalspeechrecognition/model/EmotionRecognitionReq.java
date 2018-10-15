package pl.edu.agh.kis.emotionalspeechrecognition.model;

import com.google.gson.annotations.Expose;

public class EmotionRecognitionReq {
    @Expose
    private float[] mfcc;
    @Expose
    private float pitch;

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float[] getMfcc() {
        return mfcc;
    }

    public void setMfcc(float[] mfcc) {
        this.mfcc = mfcc;
    }
}
