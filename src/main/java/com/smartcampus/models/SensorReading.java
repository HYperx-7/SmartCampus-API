package com.smartcampus.models;

import java.time.LocalDateTime;

public class SensorReading {
    private String id;
    private String sensorId;
    private LocalDateTime timestamp;
    private double value;

    public SensorReading() {
        this.timestamp = LocalDateTime.now();
    }

    public SensorReading(String id, String sensorId, double value) {
        this.id = id;
        this.sensorId = sensorId;
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}