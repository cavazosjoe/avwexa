package org.tsd.avwx;

public class Constants {

    public static class Intent {

        public static class METAR {
            public static final String INTENT_NAME = "GetMetar";
            public static final String AIRPORT_ID_SLOT = "ident";
            public static final String AIRPORT_NAME_SLOT = "airportName";
        }

        public static class TAF {
            public static final String INTENT_NAME = "GetTaf";
        }
    }

    public static class AvWx {
        public static final String METAR_ENDPOINT = "https://avwx.rest/api/metar";
        public static final String TAF_ENDPOINT = "https://avwx.rest/api/taf";
    }

}
