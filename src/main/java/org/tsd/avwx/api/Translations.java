package org.tsd.avwx.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Translations {

    @JsonProperty("Altimeter")
    private String altimeter;

    @JsonProperty("Clouds")
    private String clouds;

    @JsonProperty("Dewpoint")
    private String dewpoint;

    @JsonProperty("Other")
    private String other;

    @JsonProperty("Temperature")
    private String temperature;

    @JsonProperty("Visibility")
    private String visibility;

    @JsonProperty("Wind")
    private String wind;

    public String getAltimeter() {
        return altimeter;
    }

    public void setAltimeter(String altimeter) {
        this.altimeter = altimeter;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getDewpoint() {
        return dewpoint;
    }

    public void setDewpoint(String dewpoint) {
        this.dewpoint = dewpoint;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
