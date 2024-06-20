package com.example.demo.vo;

public class WarningRequest {
    private Integer carId;
    private Integer warnId;
    private String signal;


    // Getters and setters

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getWarnId() {
        return warnId;
    }

    public void setWarnId(Integer warnId) {
        this.warnId = warnId;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }
}
