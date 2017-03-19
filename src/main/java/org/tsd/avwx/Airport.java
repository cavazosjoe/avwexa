package org.tsd.avwx;

import java.util.TimeZone;

public class Airport {

    private String code;
    private String name;
    private TimeZone timeZone;

    public Airport(String code, String name, TimeZone timeZone) {
        this.code = code;
        this.name = name;
        this.timeZone = timeZone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
