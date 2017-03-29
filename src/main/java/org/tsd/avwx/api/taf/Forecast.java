package org.tsd.avwx.api.taf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Forecast {

    @JsonProperty(value = "Altimeter")
    private String altimeter;

    @JsonProperty(value = "Cloud-List")
    private List<String[]> clouds;

    @JsonProperty(value = "End-Time")
    private String endTime;

    @JsonProperty(value = "Flight-Rules")
    private String flightRules;

    @JsonProperty(value = "Icing-List")
    private List<String> icingList;

    @JsonProperty(value = "Other-List")
    private List<String> otherList;

    @JsonProperty(value = "Probability")
    private String probability;

    @JsonProperty(value = "Raw-Line")
    private String rawLine;

    @JsonProperty(value = "Start-Time")
    private String startTime;

    @JsonProperty(value = "Turb-List")
    private List<String> turbulenceList;

    @JsonProperty(value = "Type")
    private String type;

    @JsonProperty(value = "Visibility")
    private String visibility;

    @JsonProperty(value = "Wind-Direction")
    private String windDirection;

    @JsonProperty(value = "Wind-Gust")
    private String windGust;

    @JsonProperty(value = "Wind-Shear")
    private String windShear;

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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFlightRules() {
        return flightRules;
    }

    public void setFlightRules(String flightRules) {
        this.flightRules = flightRules;
    }

    public List<String> getIcingList() {
        return icingList;
    }

    public void setIcingList(List<String> icingList) {
        this.icingList = icingList;
    }

    public List<String> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<String> otherList) {
        this.otherList = otherList;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String getRawLine() {
        return rawLine;
    }

    public void setRawLine(String rawLine) {
        this.rawLine = rawLine;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<String> getTurbulenceList() {
        return turbulenceList;
    }

    public void setTurbulenceList(List<String> turbulenceList) {
        this.turbulenceList = turbulenceList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
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

    public String getWindShear() {
        return windShear;
    }

    public void setWindShear(String windShear) {
        this.windShear = windShear;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}
