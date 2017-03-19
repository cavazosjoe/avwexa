package org.tsd.avwx;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class AirportUtil {

    private static final Map<String, String> natoMap = new HashMap<>();

    private static final int AIRPORT_NAME_IDX = 1;
    private static final int AIRPORT_IATA_IDX = 4;
    private static final int AIRPORT_ICAO_IDX = 5;
    private static final int AIRPORT_TIMEZONE_IDX = 11;

    public static Airport getAirportForSpokenIdentifier(String input) {
        String[] words = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (natoMap.containsKey(w.toUpperCase())) {
                // this is a letter
                sb.append(w);
            } else {
                // not a letter, maybe a word
                String sanitizedWord = w.replaceAll(" ", "").replaceAll("-", "");
                for (Map.Entry<String, String> natoMapEntry : natoMap.entrySet()) {
                    if (sanitizedWord.equalsIgnoreCase(natoMapEntry.getValue())) {
                        sb.append(natoMapEntry.getKey());
                    }
                }
            }
        }

        if (sb.length() > 0) {
            try (CSVParser parser = new CSVParser(new InputStreamReader(Driver.class.getResourceAsStream("/airports.csv")), CSVFormat.DEFAULT)) {
                for (CSVRecord record : parser) {
                    if (sb.toString().equalsIgnoreCase(record.get(AIRPORT_IATA_IDX)) || sb.toString().equalsIgnoreCase(record.get(AIRPORT_ICAO_IDX))) {
                        TimeZone timeZone = parseTimeZoneFromRecord(record);
                        return new Airport(
                                record.get(AIRPORT_ICAO_IDX),
                                record.get(AIRPORT_NAME_IDX),
                                timeZone);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public static Airport getAirportForSpokenName(String input) {
        try (CSVParser parser = new CSVParser(new InputStreamReader(Driver.class.getResourceAsStream("/airports.csv")), CSVFormat.DEFAULT)) {

            Airport bestMatch = null;
            int bestWordsMatched = 0;

            String airportNameInCsv;
            String[] airportNameInCsvParts;
            String[] airportNameSpokenParts;
            int numWordsMatched;

            for (CSVRecord record : parser) {
                numWordsMatched = 0;
                airportNameInCsv = record.get(1).toLowerCase();
                airportNameInCsvParts = airportNameInCsv.split("\\s+");
                airportNameSpokenParts = input.toLowerCase().split("\\s+");
                for (String spoken : airportNameSpokenParts) {
                    if (ArrayUtils.contains(airportNameInCsvParts, spoken)) {
                        numWordsMatched++;
                    }
                    if (numWordsMatched > bestWordsMatched) {
                        TimeZone timeZone = parseTimeZoneFromRecord(record);
                        bestMatch = new Airport(
                                record.get(AIRPORT_ICAO_IDX),
                                record.get(AIRPORT_NAME_IDX),
                                timeZone);
                        bestWordsMatched = numWordsMatched;
                    }
                }
            }

            return bestMatch;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TimeZone parseTimeZoneFromRecord(CSVRecord record) {
        String timeZoneString = record.get(AIRPORT_TIMEZONE_IDX);
        if (StringUtils.isNotBlank(timeZoneString)) {
            try {
                return TimeZone.getTimeZone(timeZoneString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static {
        natoMap.put("A", "alpha");
        natoMap.put("B", "bravo");
        natoMap.put("C", "charlie");
        natoMap.put("D", "delta");
        natoMap.put("E", "echo");
        natoMap.put("F", "foxtrot");
        natoMap.put("G", "golf");
        natoMap.put("H", "hotel");
        natoMap.put("I", "india");
        natoMap.put("J", "juliet");
        natoMap.put("K", "kilo");
        natoMap.put("L", "lima");
        natoMap.put("M", "mike");
        natoMap.put("N", "november");
        natoMap.put("O", "oscar");
        natoMap.put("P", "papa");
        natoMap.put("Q", "quebec");
        natoMap.put("R", "romeo");
        natoMap.put("S", "sierra");
        natoMap.put("T", "tango");
        natoMap.put("U", "uniform");
        natoMap.put("V", "victor");
        natoMap.put("W", "whiskey");
        natoMap.put("X", "xray");
        natoMap.put("Y", "yankee");
        natoMap.put("Z", "zulu");
        natoMap.put("0", "zero");
        natoMap.put("1", "one");
        natoMap.put("2", "two");
        natoMap.put("3", "three");
        natoMap.put("4", "four");
        natoMap.put("5", "five");
        natoMap.put("6", "six");
        natoMap.put("7", "seven");
        natoMap.put("8", "eight");
        natoMap.put("9", "nine");
    }
}
