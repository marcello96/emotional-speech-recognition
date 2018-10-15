package pl.edu.agh.kis.emotionalspeechrecognition.service;

import io.reactivex.Single;
import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionRecognitionReq;
import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionRecognitionResp;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmotionRecognitionService {
    @POST("/emotion/analyze")
    Single<EmotionRecognitionResp> getEmotionType(@Body EmotionRecognitionReq request);
}
