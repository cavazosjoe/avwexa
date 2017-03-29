package org.tsd.avwx;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tsd.avwx.api.metar.Metar;
import org.tsd.avwx.api.taf.Taf;

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
                Airport airport = parseAirportFromIntent(request.getIntent());
                if (airport != null) {
                    try {
                        Metar metar = AvwxClient.getMetarForStation(airport.getCode());
                        return getSpokenResponse(SpeechUtil.speakMetar(airport, metar));
                    } catch (Exception e) {
                        return getSpokenResponse("Sorry, I couldn't fetch the me tar for " + airport);
                    }
                } else {
                    return getSpokenResponse("Sorry, I couldn't figure out what airport you wanted a me tar for");
                }
            }
            case Constants.Intent.TAF.INTENT_NAME: {
                Airport airport = parseAirportFromIntent(request.getIntent());
                if (airport != null) {
                    try {
                        Taf taf = AvwxClient.getTafForStation(airport.getCode());
                        return getSpokenResponse(SpeechUtil.speakFullTaf(airport, taf));
                    } catch (Exception e) {
                        return getSpokenResponse("Sorry, I couldn't fetch the taf for " + airport);
                    }
                } else {
                    return getSpokenResponse("Sorry, I couldn't figure out what airport you wanted a taf for");
                }
            }
        }
        return getSpokenResponse("Sorry, I could not understand what you want");
    }

    private static Airport parseAirportFromIntent(Intent intent) {
        Slot airportIdSlot = intent.getSlots().get(Constants.Intent.AIRPORT_ID_SLOT);
        Slot airportNameSlot = intent.getSlots().get(Constants.Intent.AIRPORT_NAME_SLOT);
        Airport airport = null;

        if (airportIdSlot.getValue() != null) {
            String spokenCode = airportIdSlot.getValue();
            airport = AirportUtil.getAirportForSpokenIdentifier(spokenCode);
        } else if (airportNameSlot.getValue() != null) {
            String airportName = airportNameSlot.getValue();
            airport = AirportUtil.getAirportForSpokenName(airportName);
        }

        return airport;
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
