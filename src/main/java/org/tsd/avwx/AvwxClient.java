package org.tsd.avwx;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.tsd.avwx.api.metar.Metar;
import org.tsd.avwx.api.taf.Taf;

import java.io.IOException;
import java.net.URISyntaxException;

public class AvwxClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Metar getMetarForStation(String code) throws IOException, URISyntaxException {
        return getWeather("/api/metar/"+code, Metar.class);
    }

    public static Taf getTafForStation(String code) throws IOException, URISyntaxException {
        return getWeather("/api/taf/"+code, Taf.class);
    }

    private static <T> T getWeather(String path, Class<T> clazz) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https");
        uriBuilder.setHost("avwx.rest");
        uriBuilder.setPath(path);
        uriBuilder.addParameter("options", "info,translate");
        try (CloseableHttpClient httpClient = HttpClients.createMinimal()) {
            HttpGet get = new HttpGet(uriBuilder.build());
            String responseString = EntityUtils.toString(httpClient.execute(get).getEntity());
            System.out.println("Raw response: " + responseString);
            return objectMapper.readValue(responseString, clazz);
        }
    }
}
