package pl.edu.agh.kis.emotionalspeechrecognition;

import java.util.function.Consumer;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.mfcc.MFCC;

public class MFCCProcessor extends MFCC {
    private Consumer<float[]> consumer;

    public MFCCProcessor(int samplesPerFrame, int sampleRate, int amountOfMFCCs, Consumer<float[]> consumer) {
        super(samplesPerFrame, (float)sampleRate, amountOfMFCCs, 30, 133.3334F, (float)sampleRate / 2.0F);
        this.consumer = consumer;
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        final boolean result = super.process(audioEvent);
        consumer.accept(getMFCC());
        return result;
    }
}
