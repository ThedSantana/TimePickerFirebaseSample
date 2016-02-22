package com.nbau21.sampletimepicker;

/**
 * Created by nbau21 on 2/21/16.
 */
public class TimeNameModel {
    private String name;
    private String time;

    public TimeNameModel() {
    }

    public TimeNameModel(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
