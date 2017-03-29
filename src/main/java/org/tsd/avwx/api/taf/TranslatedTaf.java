package org.tsd.avwx.api.taf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TranslatedTaf {

    @JsonProperty(value = "Forecast")
    private List<TranslatedForecast> forecasts;

    @JsonProperty(value = "Max-Temp")
    private String maxTemp;

    @JsonProperty(value = "Min-Temp")
    private String minTemp;

    public List<TranslatedForecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<TranslatedForecast> forecasts) {
        this.forecasts = forecasts;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }
}
