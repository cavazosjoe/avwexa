package org.tsd.avwx.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Info {

    @JsonProperty(value = "City")
    private String city;

    @JsonProperty(value = "Country")
    private String country;

    @JsonProperty(value = "Elevation")
    private String elevation;

    @JsonProperty(value = "IATA")
    private String iata;

    @JsonProperty(value = "ICAO")
    private String icao;

    @JsonProperty(value = "Latitude")
    private String latitude;

    @JsonProperty(value = "Longitude")
    private String longitude;

    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Priority")
    private String priority;

    @JsonProperty(value = "State")
    private String state;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
