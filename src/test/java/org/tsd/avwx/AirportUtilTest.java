package org.tsd.avwx;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AirportUtilTest {

    @Test
    public void testGetCodeForAirportName() {
        String name = "Renton aIrpOrt";
        Airport airport = AirportUtil.getAirportForSpokenName(name);
        assertEquals("KRNT", airport.getCode());
    }
}
