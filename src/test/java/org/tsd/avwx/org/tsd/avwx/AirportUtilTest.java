package org.tsd.avwx.org.tsd.avwx;

import org.junit.Test;
import org.tsd.avwx.AirportUtil;

import static org.junit.Assert.assertEquals;

public class AirportUtilTest {

    @Test
    public void testGetCodeForAirportName() {
        String name = "Renton aIrpOrt";
        String code = AirportUtil.getCodeForAirportName(name);
        assertEquals("KRNT", code);
    }
}
