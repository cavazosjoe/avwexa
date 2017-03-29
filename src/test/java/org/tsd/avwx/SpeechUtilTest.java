package org.tsd.avwx;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpeechUtilTest {

    @Test
    public void testSpeakTime() {
        DateTime now = new DateTime(0L, DateTimeZone.UTC);
        String output = SpeechUtil.speakTime("312345Z", now, null);
        assertEquals(" observation 15 minutes ago at 2 3 4 5 zulu", output);
    }
}
