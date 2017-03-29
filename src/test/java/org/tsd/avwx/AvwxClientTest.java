package org.tsd.avwx;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tsd.avwx.api.metar.Metar;
import org.tsd.avwx.api.taf.Taf;

import static org.junit.Assert.assertNotNull;

public class AvwxClientTest {

    private static final Logger log = LoggerFactory.getLogger(AvwxClientTest.class);

    @Test
    public void testGetMetar() throws Exception {
        Metar metar = AvwxClient.getMetarForStation("KRNT");
        System.out.println("Got metar: "+metar);
        assertNotNull(metar);
    }

    @Test
    public void testGetTaf() throws Exception {
        Taf taf = AvwxClient.getTafForStation("KPAE");
        System.out.println("Got taf: "+taf);
        assertNotNull(taf);
    }
}
