package com.smartcampus.storage;

import com.smartcampus.models.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    public static List<Room> rooms = new ArrayList<>();
    public static List<Sensor> sensors = new ArrayList<>();
    public static List<SensorReading> readings = new ArrayList<>();

    static {

        rooms.add(new com.smartcampus.models.Room("R1", "Main Lab", 50));


        sensors.add(new com.smartcampus.models.Sensor("S1", "Temperature", "MAINTENANCE", "R1"));
        sensors.add(new com.smartcampus.models.Sensor("S2", "CO2", "Enabled", "R1"));


        readings.add(new com.smartcampus.models.SensorReading("SR1", "S1", 24.5));
    }
}