package org.tsd.avwx.api.taf;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslatedForecast {

    @JsonProperty(value = "Altimeter")
    private String altimeter;

    @JsonProperty(value = "Clouds")
    private String clouds;

    @JsonProperty(value = "Icing")
    private String icing;

    @JsonProperty(value = "Other")
    private String other;

    @JsonProperty(value = "Turbulance")
    private String turbulence;

    @JsonProperty(value = "Visibility")
    private String visibility;

    @JsonProperty(value = "Wind")
    private String wind;

    @JsonProperty(value = "Wind-Shear")
    private String windShear;

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

    public String getIcing() {
        return icing;
    }

    public void setIcing(String icing) {
        this.icing = icing;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getTurbulence() {
        return turbulence;
    }

    public void setTurbulence(String turbulence) {
        this.turbulence = turbulence;
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

    public String getWindShear() {
        return windShear;
    }

    public void setWindShear(String windShear) {
        this.windShear = windShear;
    }
}
