package org.tsd.avwx.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metar implements Serializable {

    @JsonProperty(value = "Altimeter")
    private String altimeter;

    @JsonProperty(value = "Cloud-List")
    private List<String[]> clouds;

    @JsonProperty(value = "Dewpoint")
    private String dewpoint;

    @JsonProperty(value = "Flight-Rules")
    private String flightRules;

    @JsonProperty(value = "Other-List")
    private List<String> otherList;

    @JsonProperty(value = "Temperature")
    private String temperature;

    @JsonProperty(value = "Time")
    private String time;

    @JsonProperty(value = "Units")
    private Units units;

    @JsonProperty(value = "Wind-Direction")
    private String windDirection;

    @JsonProperty(value = "Wind-Gust")
    private String windGust;

    @JsonProperty(value = "Wind-Speed")
    private String windSpeed;

    public String getAltimeter() {
        return altimeter;
    }

    public void setAltimeter(String altimeter) {
        this.altimeter = altimeter;
    }

    public List<String[]> getClouds() {
        return clouds;
    }

    public void setClouds(List<String[]> clouds) {
        this.clouds = clouds;
    }

    public String getDewpoint() {
        return dewpoint;
    }

    public void setDewpoint(String dewpoint) {
        this.dewpoint = dewpoint;
    }

    public String getFlightRules() {
        return flightRules;
    }

    public void setFlightRules(String flightRules) {
        this.flightRules = flightRules;
    }

    public List<String> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<String> otherList) {
        this.otherList = otherList;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindGust() {
        return windGust;
    }

    public void setWindGust(String windGust) {
        this.windGust = windGust;
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
                .append("altimeter", altimeter)
                .append("clouds", clouds == null ? "null" : clouds.stream().flatMap(Arrays::stream).collect(Collectors.toList()))
                .append("dewpoint", dewpoint)
                .append("flightRules", flightRules)
                .append("otherList", otherList)
                .append("temperature", temperature)
                .append("time", time)
                .append("units", units)
                .append("windDirection", windDirection)
                .append("windGust", windGust)
                .append("windSpeed", windSpeed)
                .toString();
    }
}
