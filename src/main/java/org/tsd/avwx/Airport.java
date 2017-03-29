package org.tsd.avwx;

import org.joda.time.DateTimeZone;

public class Airport {

    private String code;
    private String name;
    private DateTimeZone timeZone;

    public Airport(String code, String name, DateTimeZone timeZone) {
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

    public DateTimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(DateTimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
