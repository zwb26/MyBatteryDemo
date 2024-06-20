package com.example.demo.entity;

import jakarta.persistence.*;

public class VehicleInfo {
    private Integer carId;
    private String vid;
    private BatteryType batteryType;
    private Integer totalMileage;
    private Integer batteryHealthState;

    // Getters and setters

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public BatteryType getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(BatteryType batteryType) {
        this.batteryType = batteryType;
    }

    public Integer getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(Integer totalMileage) {
        this.totalMileage = totalMileage;
    }

    public Integer getBatteryHealthState() {
        return batteryHealthState;
    }

    public void setBatteryHealthState(Integer batteryHealthState) {
        this.batteryHealthState = batteryHealthState;
    }
}