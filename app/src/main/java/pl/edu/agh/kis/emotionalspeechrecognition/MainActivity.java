package pl.edu.agh.kis.emotionalspeechrecognition;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchProcessor;
import io.reactivex.disposables.CompositeDisposable;
import pl.edu.agh.kis.emotionalspeechrecognition.model.EmotionRecognitionReq;
import pl.edu.agh.kis.emotionalspeechrecognition.service.EmotionRecognitionService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int SAMPLE_RATE = 22050;
    private static final float DURATION_OF_SAMPLE_IN_SEC = 1f;
    private static final int AUDIO_BUFFER_SIZE = (int)(SAMPLE_RATE*DURATION_OF_SAMPLE_IN_SEC);
    private static final int BUFFER_OVERLAP = 0;
    private static final String API_BASE_URL = "localhost:8080/api";
    private static final int MY_PERMISSIONS_RECORD_AUDIO = 1;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private EmotionRecognitionReq emotionRequest = new EmotionRecognitionReq();
    private EmotionRecognitionService emotionService;

    public MainActivity() {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        emotionService = retrofit.create(EmotionRecognitionService.class);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioRecordWrapper();
    }

    @Override
    protected void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    private void audioRecord() {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE, AUDIO_BUFFER_SIZE, BUFFER_OVERLAP);
        dispatcher.addAudioProcessor(new MFCCProcessor(AUDIO_BUFFER_SIZE, SAMPLE_RATE, mfcc -> emotionRequest.setMfcc(mfcc)));
        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, SAMPLE_RATE, AUDIO_BUFFER_SIZE,
                (pitchDetectionResult, audioEvent) -> {
                    emotionRequest.setPitch(pitchDetectionResult.getPitch());
                    runOnUiThread(() -> {
                                TextView pitchLabel = findViewById(R.id.pitchResultLabel);
                                TextView mfccLabel = findViewById(R.id.mfccLabel);
                                pitchLabel.setText(String.valueOf(emotionRequest.getPitch()));
                                mfccLabel.setText(Arrays.toString(emotionRequest.getMfcc()));
                            }
                    );
                }));

        new Thread(dispatcher,"Audio Dispatcher").start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    audioRecord();
                } else {
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
        else if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            audioRecord();
        }
    }


}
