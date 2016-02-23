package com.nbau21.sampletimepicker;

/**
 * Created by nbau21 on 2/21/16.
 */
public class TimeNameModel {
    private String name;
    private long time;

    public TimeNameModel() {
    }

    public TimeNameModel(String name, long time) {
        this.name = name;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
