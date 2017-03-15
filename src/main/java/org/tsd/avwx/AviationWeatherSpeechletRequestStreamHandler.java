package org.tsd.avwx;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.Collections;
import java.util.Set;

public class AviationWeatherSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    public AviationWeatherSpeechletRequestStreamHandler() {
        this(new AviationWeatherSpeechlet(), Collections.<String>emptySet());
    }

    public AviationWeatherSpeechletRequestStreamHandler(SpeechletV2 speechlet, Set<String> supportedApplicationIds) {
        super(speechlet, supportedApplicationIds);
    }

    public AviationWeatherSpeechletRequestStreamHandler(Speechlet speechlet, Set<String> supportedApplicationIds) {
        super(speechlet, supportedApplicationIds);
    }
}
