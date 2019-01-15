package pl.edu.agh.kis.emotionalspeechrecognition.service;

import io.reactivex.Single;
import pl.edu.agh.kis.emotionalspeechrecognition.model.NetworkType;
import pl.edu.agh.kis.emotionalspeechrecognition.model.dto.EmotionRecognitionReq;
import pl.edu.agh.kis.emotionalspeechrecognition.model.dto.EmotionRecognitionResp;
import pl.edu.agh.kis.emotionalspeechrecognition.model.dto.InitConfiguration;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EmotionRecognitionService {
    @POST("/prediction/{networkType}")
    Single<EmotionRecognitionResp> predictEmotion(@Path("networkType") NetworkType networkType,
                                                  @Body EmotionRecognitionReq request);
    @GET("/configuration/init")
    Single<InitConfiguration> getConfiguration();
}
