package org.tsd.avwx.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Units implements Serializable {

    @JsonProperty(value = "Altimeter")
    private String altimeterUnits;

    @JsonProperty(value = "Altitude")
    private String altimterUnits;

    @JsonProperty(value = "Temperature")
    private String temperatureUnits;

    @JsonProperty(value = "Visibility")
    private String visibilityUnits;

    @JsonProperty(value = "Wind-Speed")
    private String windSpeed;

    public String getAltimeterUnits() {
        return altimeterUnits;
    }

    public void setAltimeterUnits(String altimeterUnits) {
        this.altimeterUnits = altimeterUnits;
    }

    public String getAltimterUnits() {
        return altimterUnits;
    }

    public void setAltimterUnits(String altimterUnits) {
        this.altimterUnits = altimterUnits;
    }

    public String getTemperatureUnits() {
        return temperatureUnits;
    }

    public void setTemperatureUnits(String temperatureUnits) {
        this.temperatureUnits = temperatureUnits;
    }

    public String getVisibilityUnits() {
        return visibilityUnits;
    }

    public void setVisibilityUnits(String visibilityUnits) {
        this.visibilityUnits = visibilityUnits;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("altimeterUnits", altimeterUnits)
                .append("altimterUnits", altimterUnits)
                .append("temperatureUnits", temperatureUnits)
                .append("visibilityUnits", visibilityUnits)
                .append("windSpeed", windSpeed)
                .toString();
    }
}
