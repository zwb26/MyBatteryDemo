package com.example.demo.vo;

public class WarningResponse {
    private Integer carId;
    private String batteryType;
    private String warnName;
    private Integer warnLevel;

    // Getters and setters

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public Integer getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(Integer warnLevel) {
        this.warnLevel = warnLevel;
    }
}
