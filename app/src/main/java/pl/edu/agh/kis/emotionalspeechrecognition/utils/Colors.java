package pl.edu.agh.kis.emotionalspeechrecognition.utils;

import lombok.Getter;

@Getter
public enum Colors {
    LIGHT_BLUE(0xff66b3ff),
    BLUE(0xff0066cc),
    RED(0xff990000),
    PURPLE(0xff800080),
    ORANGE(0xffff6600),
    YELLOW(0xffffcc00),
    GREEN(0xff009933),
    LIGHT_GREEN(0xff00cc99);

    private int hex;

    Colors(int hex) {
        this.hex = hex;
    }
}
