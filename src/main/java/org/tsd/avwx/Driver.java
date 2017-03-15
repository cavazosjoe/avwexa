package org.tsd.avwx;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.InputStreamReader;

public class Driver {

    public static void main(String... args) throws Exception {
        try (CSVParser parser = new CSVParser(new InputStreamReader(Driver.class.getResourceAsStream("/airports.csv")), CSVFormat.DEFAULT)) {
            parser.getRecords().stream()
                    .filter(record -> record.get(3).equalsIgnoreCase("united states"))
//                    .map(record -> acronymize(record.get(4).toCharArray()))
//                    .map(record -> phonetize(record.get(4).toCharArray()))
//                    .map(record -> record.get(2))
                    .map(record -> record.get(1))
                    .forEach(System.out::println);
        }

//        ObjectMapper objectMapper = new ObjectMapper();
//        Metar metar = objectMapper.readValue("{\n" +
//                "  \"Altimeter\": \"3002\", \n" +
//                "  \"Cloud-List\": [\n" +
//                "    [\n" +
//                "      \"SCT\", \n" +
//                "      \"015\"\n" +
//                "    ], \n" +
//                "    [\n" +
//                "      \"BKN\", \n" +
//                "      \"020\"\n" +
//                "    ]\n" +
//                "  ], \n" +
//                "  \"Dewpoint\": \"10\", \n" +
//                "  \"Flight-Rules\": \"MVFR\", \n" +
//                "  \"Other-List\": [], \n" +
//                "  \"Raw-Report\": \"KRNT 140606Z AUTO 00000KT 10SM SCT015 BKN020 10/10 A3002 RMK AO2 T01000100\", \n" +
//                "  \"Remarks\": \"RMK AO2 T01000100\", \n" +
//                "  \"Remarks-Info\": {\n" +
//                "    \"Dew-Decimal\": \"10.0\", \n" +
//                "    \"Temp-Decimal\": \"10.0\"\n" +
//                "  }, \n" +
//                "  \"Runway-Vis-List\": [], \n" +
//                "  \"Station\": \"KRNT\", \n" +
//                "  \"Temperature\": \"10\", \n" +
//                "  \"Time\": \"140606Z\", \n" +
//                "  \"Units\": {\n" +
//                "    \"Altimeter\": \"inHg\", \n" +
//                "    \"Altitude\": \"ft\", \n" +
//                "    \"Temperature\": \"C\", \n" +
//                "    \"Visibility\": \"sm\", \n" +
//                "    \"Wind-Speed\": \"kt\"\n" +
//                "  }, \n" +
//                "  \"Visibility\": \"10\", \n" +
//                "  \"Wind-Direction\": \"000\", \n" +
//                "  \"Wind-Gust\": \"\", \n" +
//                "  \"Wind-Speed\": \"00\", \n" +
//                "  \"Wind-Variable-Dir\": []\n" +
//                "}", Metar.class);
//
//        int i=0;
    }



//    private static String acronymize(char[] array) {
//        StringBuilder sb = new StringBuilder();
//        for (char c : array) {
//            if (sb.length() > 0) {
//                sb.append(". ");
//            }
//            sb.append(c);
//        }
//        return sb.toString();
//    }
//
//    private static String phonetize(char[] array) {
//        StringBuilder sb = new StringBuilder();
//        for (char c : array) {
//            if (sb.length() > 0) {
//                sb.append(" ");
//            }
//            sb.append(natoMap.get(c));
//        }
//        return sb.toString();
//    }




}
