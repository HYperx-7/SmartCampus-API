package com.smartcampus.resources;

import com.smartcampus.models.Room;
import com.smartcampus.storage.DataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/rooms")
public class RoomResource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return DataStore.rooms;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {

        if (room == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        DataStore.rooms.add(room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String id) {
        for (com.smartcampus.models.Room r : com.smartcampus.storage.DataStore.rooms) {
            if (r.getId().equals(id)) {

                for (com.smartcampus.models.Sensor s : com.smartcampus.storage.DataStore.sensors) {
                    if (s.getRoomId().equals(id)) {

                        throw new com.smartcampus.exceptions.RoomNotEmptyException(
                                "Room " + id + " cannot be deleted because it still contains sensors."
                        );
                    }
                }
                com.smartcampus.storage.DataStore.rooms.remove(r);
                return Response.noContent().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}