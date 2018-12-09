package pl.edu.agh.kis.emotionalspeechrecognition.model.dto;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class EmotionRecognitionReq {

    @Expose
    private float[] mfcc;
}
