package pl.edu.agh.kis.emotionalspeechrecognition.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.Data;

@Data
public class EmotionRecognitionResp {

    @Expose
    private List<EmotionRecognitionElem> results;
}
