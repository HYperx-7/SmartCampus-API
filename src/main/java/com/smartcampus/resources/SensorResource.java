package com.smartcampus.resources;

import com.smartcampus.models.Sensor;
import com.smartcampus.storage.DataStore;
import com.smartcampus.exceptions.LinkedResourceNotFoundException; // IMPORT NEW EXCEPTION
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/sensors")
public class SensorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        if (type == null || type.isEmpty()) {
            return DataStore.sensors;
        }

        List<Sensor> filteredSensors = new java.util.ArrayList<>();
        for (Sensor s : DataStore.sensors) {
            if (s.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(s);
            }
        }
        return filteredSensors;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        boolean roomExists = false;
        for (com.smartcampus.models.Room r : DataStore.rooms) {
            if (r.getId().equals(sensor.getRoomId())) {
                roomExists = true;
                break;
            }
        }

        if (!roomExists) {

            throw new LinkedResourceNotFoundException(
                    "Room ID " + sensor.getRoomId() + " does not exist. Cannot register sensor."
            );
        }

        DataStore.sensors.add(sensor);
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @GET
    @Path("/room/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensorsByRoom(@PathParam("roomId") String roomId) {
        List<Sensor> roomSensors = new java.util.ArrayList<>();
        for (Sensor s : DataStore.sensors) {
            if (s.getRoomId().equals(roomId)) {
                roomSensors.add(s);
            }
        }
        return roomSensors;
    }

    @PUT
    @Path("/{sensorId}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSensorStatus(@PathParam("sensorId") String id, String newStatus) {
        for (Sensor s : DataStore.sensors) {
            if (s.getId().equals(id)) {
                s.setStatus(newStatus);
                return Response.ok(s).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String id) {
        return new SensorReadingResource(id);
    }
}