package org.tsd.avwx;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tsd.avwx.api.Metar;

public class AviationWeatherSpeechlet implements Speechlet {

    private static final Logger log = LoggerFactory.getLogger(AviationWeatherSpeechlet.class);

    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        log.info("onSessionStarted");
    }

    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        log.info("onLaunch");
        return getWelcomeResponse();
    }

    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        log.info("onIntent: " + request.getIntent().getName());
        switch (request.getIntent().getName()) {
            case Constants.Intent.METAR.INTENT_NAME: {

                Slot airportIdSlot = request.getIntent().getSlots().get(Constants.Intent.METAR.AIRPORT_ID_SLOT);
                Slot airportNameSlot = request.getIntent().getSlots().get(Constants.Intent.METAR.AIRPORT_NAME_SLOT);
                String airportIdentifier = null;

                if (airportIdSlot.getValue() != null) {
                    String spokenCode = airportIdSlot.getValue();
                    airportIdentifier = AirportUtil.getCodeForSpokenIdentifier(spokenCode);
                } else if (airportNameSlot.getValue() != null) {
                    String airportName = airportNameSlot.getValue();
                    airportIdentifier = AirportUtil.getCodeForAirportName(airportName);
                } else {
                    return getSpokenResponse("You wanted a METAR but didn't specify a code");
                }

                if (airportIdentifier != null) {
                    try {
                        Metar metar = AvwxClient.getMetarForStation(airportIdentifier);
                        return getSpokenResponse("Altimeter at " + airportIdentifier + " is " + metar.getAltimeter());
                    } catch (Exception e) {
                        return getSpokenResponse("Sorry, I couldn't fetch the METAR for " + airportIdentifier);
                    }
                } else {
                    return getSpokenResponse("I couldn't figure out what airport you wanted");
                }
            }
        }
        return getSpokenResponse("I have no idea what you want");
    }

    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        log.info("onSessionEnded: " + request.getReason());
    }

    private static SpeechletResponse getWelcomeResponse() {
        return getSpokenResponse("Hello there");
    }

    private static SpeechletResponse getSpokenResponse(String speechText) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(speechText);
        return SpeechletResponse.newTellResponse(outputSpeech);
    }
}
