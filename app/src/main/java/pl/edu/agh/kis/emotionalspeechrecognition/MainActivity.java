package pl.edu.agh.kis.emotionalspeechrecognition;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Comparator;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionType;
import pl.edu.agh.kis.emotionalspeechrecognition.model.NetworkType;
import pl.edu.agh.kis.emotionalspeechrecognition.model.dto.EmotionRecognitionElem;
import pl.edu.agh.kis.emotionalspeechrecognition.model.dto.EmotionRecognitionReq;
import pl.edu.agh.kis.emotionalspeechrecognition.model.dto.EmotionRecognitionResp;
import pl.edu.agh.kis.emotionalspeechrecognition.service.EmotionRecognitionService;
import pl.edu.agh.kis.emotionalspeechrecognition.view.EmotionView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int SAMPLE_RATE = 22050;
    private static final int AMOUNT_OF_MFFCS = 25;
    private static final float DURATION_OF_SAMPLE_IN_SEC = 2f;
    private static final int AUDIO_BUFFER_SIZE = (int)(SAMPLE_RATE*DURATION_OF_SAMPLE_IN_SEC);
    private static final int BUFFER_OVERLAP = 0;
    private static final String API_BASE_URL = "http://ec2-18-195-107-170.eu-central-1.compute.amazonaws.com/";
    private static final int MY_PERMISSIONS_RECORD_AUDIO = 1;

    private Thread audioThread;
    private AudioDispatcher audioDispatcher;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private EmotionRecognitionReq emotionRequest = new EmotionRecognitionReq();
    private EmotionRecognitionService emotionService;
    private NetworkType chosenNetworkType = NetworkType.DNN;
    private boolean isRecording = false;


    private EmotionView emotionView;

    public MainActivity() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                                        .retryOnConnectionFailure(false)
                                        .build())
                .build();
        emotionService = retrofit.create(EmotionRecognitionService.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emotionView = findViewById(R.id.emotionView);

        initializeView();
        audioRecordWrapper();
    }

    @Override
    protected void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    private void prepareAudioRecordThread() {
        audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE, AUDIO_BUFFER_SIZE, BUFFER_OVERLAP);
        audioDispatcher.addAudioProcessor(new MFCCProcessor(AUDIO_BUFFER_SIZE, SAMPLE_RATE, AMOUNT_OF_MFFCS,
                mfcc -> {
                    emotionRequest.setMfcc(mfcc);
                    runOnUiThread(this::updateSpectrogram);
                    emotionService.predictEmotion(chosenNetworkType, emotionRequest)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<EmotionRecognitionResp>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {
                                                compositeDisposable.add(d);
                                            }

                                            @Override
                                            public void onSuccess(EmotionRecognitionResp emotion) {
                                                updatePredictionResultsView(emotion);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                handleError(e);
                                            }
                                        });
                }));
        audioThread = new Thread(audioDispatcher,"Audio Dispatcher");
    }

    public void onButtonClick(View view) throws InterruptedException {
        isRecording = !isRecording;
        Button startButton = findViewById(R.id.startButton);
        if (isRecording) {
            startButton.setText(R.string.stopLabel);
            prepareAudioRecordThread();
            audioThread.start();
        } else {
            startButton.setText(R.string.startLabel);
            audioDispatcher.stop();
            audioThread.join();
            emotionView.lightenAllEmotion();
        }
    }

    private void updateSpectrogram() {}

    private void updatePredictionResultsView(EmotionRecognitionResp response) {
        EmotionType predictedEmotion = getPredictedEmotion(response);
        System.out.println("Got: " + predictedEmotion.toString());
        emotionView.darkenEmotionBox(predictedEmotion);
    }

    private void handleError(Throwable e) {
        System.out.println("error: "+ e);
    }

    private EmotionType getPredictedEmotion(EmotionRecognitionResp response) {
        return response.getResults().stream()
                                    .max(Comparator.comparing(EmotionRecognitionElem::getPrediction))
                                    .get()
                                    .getEmotionType();
    }

    private void initializeView() {
        ((Switch)findViewById(R.id.networkTypeSwitch)).setOnCheckedChangeListener(
                (buttonView, isCheckedCnn) -> chosenNetworkType = isCheckedCnn ? NetworkType.CNN : NetworkType.DNN
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void audioRecordWrapper() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
        }
    }
}