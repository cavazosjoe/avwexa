package org.tsd.avwx;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.tsd.avwx.api.Metar;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SpeechUtil {

    private static final long ONE_HOUR_IN_MILLIS = TimeUnit.HOURS.toMillis(1);
    private static final long ONE_MINUTE_IN_MILLIS = TimeUnit.MINUTES.toMillis(1);

    public static String speakMetar(Airport airport, Metar metar) {

        Date now = new Date();

        String airportSpoken = String.format("%s %s", speakCharacters(airport.getCode()),
                StringUtils.isNotBlank(metar.getInfo().getName()) ? metar.getInfo().getName() : airport.getName());

        StringBuilder speech = new StringBuilder(airportSpoken)
                .append(speakTime(metar.getTime(), now, airport.getTimeZone())).append(". ");

        speech.append(speakWind(metar)).append(". ");

        if (StringUtils.isNotBlank(metar.getVisibility())) {
            speech.append(speakVisibility(metar)).append(". ");
        }

        if (StringUtils.isNotBlank(metar.getTranslations().getClouds())) {
            speech.append(metar.getTranslations().getClouds()).append(". ");
        }

        if (StringUtils.isNotBlank(metar.getTranslations().getOther())) {
            speech.append(metar.getTranslations().getOther()).append(". ");
        }

        if (StringUtils.isNotBlank(metar.getTemperature())) {
            speech.append(speakTemperature(metar)).append(". ");
        }

        if (StringUtils.isNotBlank(metar.getAltimeter())) {
            speech.append(speakAltimeter(metar)).append(". ");
        }

        return speech.toString();
    }

    private static String speakCharacters(String input) {
        char[] chars = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    private static String speakNumber(String input) {
        if (input.contains("/")) {
            // probably a fraction
            return input;
        } else if (input.contains(".")) {
            // probably a decimal
            String out = speakCharacters(input);
            return out.replace(".", "point");
        } else if (input.startsWith("M")) {
            // negative number
            return "minus " + speakCharacters(input.substring(1));
        }
        return input;
    }

    private static String speakAltimeter(Metar metar) {
        String altimeterString;
        if (metar.getAltimeter().startsWith("2") || metar.getAltimeter().startsWith("3")) {
            altimeterString = metar.getAltimeter().substring(0, 2)+"."+metar.getAltimeter().substring(2, 4);
        } else {
            altimeterString = metar.getAltimeter();
        }
        return "altimeter " +
                speakNumber(altimeterString) + " " +
                speakAltimeterUnits(metar.getUnits().getAltimeterUnits());
    }

    private static String speakTemperature(Metar metar) {
        return "temperature " +
                speakNumber(metar.getTemperature()) + ", " +
                "dewpoint " + speakNumber(metar.getDewpoint()) + " degrees " +
                speakTemperatureUnits(metar.getUnits().getTemperatureUnits());
    }

    protected static String speakTime(String time, Date now, TimeZone timeZone) {
        String dayString = time.substring(0, 2);
        String timeString = time.substring(2, 6);

        int dayOfMonth = Integer.parseInt(dayString);
        int hourOfDay = Integer.parseInt(timeString.substring(0, 2));
        int minuteOfHour = Integer.parseInt(timeString.substring(2, 4));

        Calendar nowCal = Calendar.getInstance();
        nowCal.setTimeZone(TimeZone.getTimeZone("UTC"));
        nowCal.setTime(now);

        Calendar metarCal = Calendar.getInstance();
        metarCal.setTimeZone(TimeZone.getTimeZone("UTC"));
        metarCal.setTime(now);
        metarCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        metarCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        metarCal.set(Calendar.MINUTE, minuteOfHour);

        if (dayOfMonth > nowCal.get(Calendar.DAY_OF_MONTH)) {
            // the day-of-month on this METAR is bigger than the current one
            // month boundary detected
            metarCal.add(Calendar.MONTH, -1);
        } else if (hourOfDay > nowCal.get(Calendar.HOUR_OF_DAY)) {
            // the hour on this METAR is bigger than the current hour
            // day boundary detected
            metarCal.add(Calendar.DATE, -1);
        }

        long timeDiff = nowCal.getTimeInMillis() - metarCal.getTimeInMillis();
        long hoursDiff = timeDiff/ONE_HOUR_IN_MILLIS;
        long minutesDiff = (timeDiff % ONE_HOUR_IN_MILLIS)/(ONE_MINUTE_IN_MILLIS);

        StringBuilder timeAgoBuilder = new StringBuilder();
        if (hoursDiff > 0) {
            timeAgoBuilder.append(hoursDiff).append(hoursDiff == 1 ? " hour" : " hours");
        }
        if (minutesDiff > 0) {
            if (timeAgoBuilder.length() > 0) {
                timeAgoBuilder.append(" and ");
            }
            timeAgoBuilder.append(minutesDiff).append(minutesDiff == 1 ? " minute" : " minutes");
        }

        StringBuilder output = new StringBuilder(" observation");
        if (timeAgoBuilder.length() > 0) {
            output.append(" ").append(timeAgoBuilder.toString()).append(" ago");
        }
        output.append(" at ").append(speakCharacters(timeString)).append(" zulu");

        if (timeZone != null) {
            metarCal.setTimeZone(timeZone);
            String localTimeString =
                    speakCharacters(String.format("%02d", metarCal.get(Calendar.HOUR_OF_DAY)))
                    + " " + speakCharacters(String.format("%02d", metarCal.get(Calendar.MINUTE)));
            output.append(", ").append(localTimeString).append(" local");
        }

        return output.toString();
    }

    private static String speakWind(Metar metar) {
        String windSpeed = metar.getWindSpeed();
        if (StringUtils.isBlank(windSpeed) || Integer.parseInt(windSpeed) == 0) {
            return "wind calm";
        }

        StringBuilder sb = new StringBuilder("wind ");

        if ("vrb".equalsIgnoreCase(metar.getWindDirection())) {
            sb.append("variable ");
            if (CollectionUtils.isNotEmpty(metar.getWindVariableDirection())) {
                sb.append(speakCharacters(metar.getWindVariableDirection().get(0)))
                        .append(" to ")
                        .append(speakCharacters(metar.getWindVariableDirection().get(1)));
            }
        } else {
            sb.append(speakCharacters(metar.getWindDirection()));
        }

        sb.append(" at ").append(speakNumber(metar.getWindSpeed()));
        if (StringUtils.isNotBlank(metar.getWindGust())) {
            sb.append(" gusting ").append(speakNumber(metar.getWindGust()));
        }
        sb.append(" ").append(speakWindSpeedUnits(metar.getUnits().getWindSpeed()));

        return sb.toString();
    }

    private static String speakVisibility(Metar metar) {
        return "visibility " +
                speakNumber(metar.getVisibility()) +
                " " + speakDistanceUnits(metar.getUnits().getVisibilityUnits());
    }

    private static String speakAltimeterUnits(String input) {
        switch (input.toLowerCase()) {
            case "inhg": return "inches of mercury";
            case "hpa": return "millibars";
        }
        return null;
    }

    private static String speakDistanceUnits(String input) {
        switch (input.toLowerCase()) {
            case "sm": return "statute miles";
            case "km": return "kilometers";
            case "nm": return "nautical miles";
        }
        return null;
    }

    private static String speakWindSpeedUnits(String input) {
        switch (input.toLowerCase()) {
            case "kt": return "knots";
        }
        return null;
    }

    private static String speakTemperatureUnits(String input) {
        switch (input.toLowerCase()) {
            case "c": return "celsius";
            case "f": return "fahrenheit";
        }
        return null;
    }
}
