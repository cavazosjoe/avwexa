package org.tsd.avwx;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.tsd.avwx.api.Units;
import org.tsd.avwx.api.metar.Metar;
import org.tsd.avwx.api.taf.Forecast;
import org.tsd.avwx.api.taf.Taf;
import org.tsd.avwx.api.taf.TranslatedForecast;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SpeechUtil {

    private static final long ONE_HOUR_IN_MILLIS = TimeUnit.HOURS.toMillis(1);
    private static final long ONE_MINUTE_IN_MILLIS = TimeUnit.MINUTES.toMillis(1);

    public static String speakMetar(Airport airport, Metar metar) {

        String airportSpoken = String.format("%s %s", speakCharacters(airport.getCode()),
                StringUtils.isNotBlank(metar.getInfo().getName()) ? metar.getInfo().getName() : airport.getName());

        StringBuilder speech = new StringBuilder(airportSpoken)
                .append(speakTime(metar.getTime(), DateTime.now(), airport.getTimeZone())).append(". ");

        String windDirection = metar.getWindDirection();
        String windGust = metar.getWindGust();
        String windSpeed = metar.getWindSpeed();
        List<String> windVariable = metar.getWindVariableDirection();

        speech.append(speakWind(windDirection, windGust, windSpeed, null, windVariable, metar.getUnits()))
                .append(". ");

        if (StringUtils.isNotBlank(metar.getVisibility())) {
            speech.append(speakVisibility(metar.getVisibility(), metar.getUnits())).append(". ");
        }

        if (StringUtils.isNotBlank(metar.getTranslatedMetar().getClouds())) {
            speech.append(metar.getTranslatedMetar().getClouds()).append(". ");
        }

        if (StringUtils.isNotBlank(metar.getTranslatedMetar().getOther())) {
            speech.append(metar.getTranslatedMetar().getOther()).append(". ");
        }

        if (StringUtils.isNotBlank(metar.getTemperature())) {
            speech.append(speakTemperature(metar)).append(". ");
        }

        if (StringUtils.isNotBlank(metar.getAltimeter())) {
            speech.append(speakAltimeter(metar)).append(". ");
        }

        return speech.toString();
    }

    public static String speakFullTaf(Airport airport, Taf taf) {

        String airportSpoken = String.format("%s %s", speakCharacters(airport.getCode()),
                StringUtils.isNotBlank(taf.getInfo().getName()) ? taf.getInfo().getName() : airport.getName());

        Forecast baseForecast = taf.getForecastList().get(0);

        StringBuilder speech = new StringBuilder(airportSpoken)
                .append(speakTime(taf.getTime(), DateTime.now(), airport.getTimeZone())).append(". ");

        speech.append("valid from ").append(speakDateAndHour(baseForecast.getStartTime(), airport.getTimeZone()))
                .append(" to ").append(speakDateAndHour(baseForecast.getEndTime(), airport.getTimeZone())).append(". ");

        addPause(speech, 500);

        DateTimeZone timeZone = airport.getTimeZone() == null ? DateTimeZone.UTC : airport.getTimeZone();
        List<Forecast> rawForecasts = taf.getForecastList();
        List<TranslatedForecast> translatedForecasts = taf.getTranslations().getForecasts();

        for (int i=1 ; i < rawForecasts.size() ; i++) {
            speech.append(speakTafPeriod(rawForecasts.get(i), translatedForecasts.get(i), taf.getUnits(), timeZone));
            addPause(speech, 500);
        }

        return speech.toString();
    }

    public static String speakTafPeriod(Forecast rawForecast, TranslatedForecast translatedForecast, Units units, DateTimeZone timeZone) {
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(speakDateAndHour(rawForecast.getStartTime(), timeZone))
                .append(" until ").append(speakDateAndHour(rawForecast.getEndTime(), timeZone))
                .append(" expect ");

        if (StringUtils.isNotBlank(translatedForecast.getClouds())) {
            sb.append(translatedForecast.getClouds()).append(", ");
        }

        if (StringUtils.isNotBlank(rawForecast.getVisibility())) {
            sb.append(speakVisibility(rawForecast.getVisibility(), units)).append(", ");
        }

        sb.append(speakWind(rawForecast.getWindDirection(), rawForecast.getWindGust(), rawForecast.getWindSpeed(), rawForecast.getWindShear(), null, units))
                .append(", ");

        if (StringUtils.isNotBlank(translatedForecast.getOther())) {
            sb.append(translatedForecast.getOther());
        }

        sb.append(". ");

        return sb.toString();
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

    protected static String speakDateAndHour(String input, DateTimeZone timeZone) {

        int currentDayOfYear = DateTime.now().getDayOfYear();
        int currentYear = DateTime.now().getYear();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddHH");
        formatter = formatter.withZone(DateTimeZone.UTC);
        DateTime dateTime = formatter.parseDateTime(input);
        dateTime = dateTime.withYear(currentYear);
        dateTime = dateTime.withDayOfYear(currentDayOfYear);

        StringBuilder sb = new StringBuilder();

        if (timeZone == null) {
            int dayOfMonth = dateTime.getDayOfMonth();
            int hourOfDay = dateTime.getHourOfDay();
            if (hourOfDay == 24) {
                hourOfDay = 0;
                dayOfMonth++;
            }

            int currentDayOfMonth = DateTime.now().withZone(DateTimeZone.UTC).getDayOfMonth();
            if (currentDayOfMonth == dayOfMonth-1) {
                sb.append("tomorrow at ");
            } else if (currentDayOfMonth == dayOfMonth-2) {
                sb.append("the day after tomorrow at ");
            }

            sb.append(hourOfDay).append(" hundred ").append("zulu");

        } else {
            dateTime = dateTime.withZone(timeZone);

            int dayOfMonth = dateTime.getDayOfMonth();
            int hourOfDay = dateTime.getHourOfDay();
            if (hourOfDay == 24) {
                hourOfDay = 0;
                dayOfMonth++;
            }

            int currentDayOfMonth = DateTime.now().withZone(timeZone).getDayOfMonth();
            if (currentDayOfMonth == dayOfMonth-1) {
                sb.append("tomorrow at ");
            } else if (currentDayOfMonth == dayOfMonth-2) {
                sb.append("the day after tomorrow at ");
            }

            String spokenHour;
            if (hourOfDay == 0) {
                spokenHour = "midnight";
            } else if (hourOfDay < 12) {
                spokenHour = hourOfDay + ":00 AM";
            } else if (hourOfDay == 12){
                spokenHour = "12:00 PM";
            } else {
                spokenHour = (hourOfDay-12) + ":00 PM";
            }
            sb.append(spokenHour).append(" local");
        }

        return sb.toString();
    }

    protected static String speakTime(String time, DateTime now, DateTimeZone timeZone) {
        String dayString = time.substring(0, 2);
        String timeString = time.substring(2, 6);

        int dayOfMonth = Integer.parseInt(dayString);
        int hourOfDay = Integer.parseInt(timeString.substring(0, 2));
        int minuteOfHour = Integer.parseInt(timeString.substring(2, 4));

        DateTime metarTime = new DateTime(now.getMillis(), DateTimeZone.UTC);
        metarTime = metarTime.withDayOfMonth(dayOfMonth);
        metarTime = metarTime.withHourOfDay(hourOfDay);
        metarTime = metarTime.withMinuteOfHour(minuteOfHour);

        if (dayOfMonth > now.getDayOfMonth()) {
            // the day-of-month on this METAR is bigger than the current one
            // month boundary detected
            metarTime = metarTime.minusMonths(1);
        } else if (hourOfDay > now.getHourOfDay()) {
            // the hour on this METAR is bigger than the current hour
            // day boundary detected
            metarTime = metarTime.minusDays(1);
        }

        long timeDiff = now.getMillis() - metarTime.getMillis();
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
            metarTime = metarTime.withZone(timeZone);
            String localTimeString =
                    speakCharacters(String.format("%02d", metarTime.getHourOfDay()))
                    + " " + speakCharacters(String.format("%02d", metarTime.getMinuteOfHour()));
            output.append(", ").append(localTimeString).append(" local");
        }

        return output.toString();
    }

    private static String speakWind(String direction, String gust, String speed,
                                    String shear, List<String> variable, Units units) {
        if (StringUtils.isBlank(speed) || Integer.parseInt(speed) == 0) {
            return "wind calm";
        }

        StringBuilder sb = new StringBuilder("wind ");

        if ("vrb".equalsIgnoreCase(direction)) {
            sb.append("variable ");
            if (CollectionUtils.isNotEmpty(variable)) {
                sb.append(speakCharacters(variable.get(0)))
                        .append(" to ")
                        .append(speakCharacters(variable.get(1)));
            }
        } else {
            sb.append(speakCharacters(direction));
        }

        sb.append(" at ").append(speakNumber(speed));
        if (StringUtils.isNotBlank(gust)) {
            sb.append(" gusting ").append(speakNumber(gust));
        }
        sb.append(" ").append(speakWindSpeedUnits(units.getWindSpeed()));

        return sb.toString();
    }

    private static String speakVisibility(String input, Units units) {
        return "visibility " +
                speakVisibilityNumber(input) +
                " " + speakDistanceUnits(units.getVisibilityUnits());
    }

    private static String speakCharacters(String input) {
        char[] chars = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    private static String speakVisibilityNumber(String input) {
        return speakNumber(input.replaceAll("P", ""));
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

    public static String getOrdinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }

    private static void addPause(StringBuilder sb, int lengthMillis) {
        sb.append("<break time=\"").append(lengthMillis).append("ms\"/>");
    }
}
