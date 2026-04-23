package com.smartcampus.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.MediaType;

@Provider
public class RoomNotEmptyMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException exception) {

        String entity = "{\"error\": \"" + exception.getMessage() + "\"}";

        return Response.status(Response.Status.CONFLICT) // 409
                .entity(entity)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}