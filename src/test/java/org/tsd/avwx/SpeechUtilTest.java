package org.tsd.avwx;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class SpeechUtilTest {

    @Test
    public void testSpeakTime() {
        Date date = new Date(0);
        String output = SpeechUtil.speakTime("312345Z", date, null);
        assertEquals(" observation 15 minutes ago at 2 3 4 5 zulu", output);
    }
}
