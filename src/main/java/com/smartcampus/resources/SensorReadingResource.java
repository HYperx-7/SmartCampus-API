package com.smartcampus.resources;

import com.smartcampus.models.Sensor;
import com.smartcampus.models.SensorReading;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exceptions.SensorUnavailableException; // Import the 403 exception
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class SensorReadingResource {
    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadingsForSensor() {

        checkSensorStatus();

        List<SensorReading> result = new ArrayList<>();
        for (SensorReading r : DataStore.readings) {
            if (r.getSensorId().equals(sensorId)) {
                result.add(r);
            }
        }
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        if (reading == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        checkSensorStatus();


        reading.setSensorId(this.sensorId);


        DataStore.readings.add(reading);


        for (Sensor s : DataStore.sensors) {
            if (s.getId().equals(this.sensorId)) {
                s.setCurrentValue(reading.getValue());
                break;
            }
        }

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }


    private void checkSensorStatus() {
        for (Sensor s : DataStore.sensors) {
            if (s.getId().equals(this.sensorId)) {
                if ("MAINTENANCE".equalsIgnoreCase(s.getStatus())) {
                    throw new SensorUnavailableException(
                            "Sensor " + sensorId + " is currently in MAINTENANCE mode and cannot be accessed."
                    );
                }
                return;
            }
        }
    }
}