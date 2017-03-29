package org.tsd.avwx.api.taf;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tsd.avwx.api.Units;
import org.tsd.avwx.api.metar.Info;

import java.util.List;

public class Taf {

    @JsonProperty(value = "Forecast")
    private List<Forecast> forecastList;

    @JsonProperty(value = "Info")
    private Info info;

    @JsonProperty(value = "Max-Temp")
    private String maxTemp;

    @JsonProperty(value = "Min-Temp")
    private String minTemp;

    @JsonProperty(value = "Raw-Report")
    private String rawReport;

    @JsonProperty(value = "Remarks")
    private String remarks;

    @JsonProperty(value = "Station")
    private String station;

    @JsonProperty(value = "Time")
    private String time;

    @JsonProperty(value = "Translations")
    private TranslatedTaf translations;

    @JsonProperty(value = "Units")
    private Units units;

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
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

    public String getRawReport() {
        return rawReport;
    }

    public void setRawReport(String rawReport) {
        this.rawReport = rawReport;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TranslatedTaf getTranslations() {
        return translations;
    }

    public void setTranslations(TranslatedTaf translations) {
        this.translations = translations;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }
}
