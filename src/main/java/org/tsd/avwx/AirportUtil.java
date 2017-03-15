package org.tsd.avwx;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.ArrayUtils;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AirportUtil {

    private static final Map<String, String> natoMap = new HashMap<>();

    public static String getCodeForSpokenIdentifier(String input) {
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
        return sb.toString();
    }

    public static String getCodeForAirportName(String input) {
        try (CSVParser parser = new CSVParser(new InputStreamReader(Driver.class.getResourceAsStream("/airports.csv")), CSVFormat.DEFAULT)) {

            String bestMatch = null;
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
                        bestMatch = record.get(5);
                        bestWordsMatched = numWordsMatched;
                    }
                }
            }

            return bestMatch;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
