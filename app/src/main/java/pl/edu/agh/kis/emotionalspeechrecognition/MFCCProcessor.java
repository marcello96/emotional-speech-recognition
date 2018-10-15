package pl.edu.agh.kis.emotionalspeechrecognition;

import java.util.function.Consumer;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.mfcc.MFCC;

public class MFCCProcessor extends MFCC {
    private Consumer<float[]> consumer;

    public MFCCProcessor(int samplesPerFrame, int sampleRate, Consumer<float[]> consumer) {
        super(samplesPerFrame, sampleRate);
        this.consumer = consumer;
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        final boolean result = super.process(audioEvent);
        consumer.accept(getMFCC());
        return result;
    }
}
