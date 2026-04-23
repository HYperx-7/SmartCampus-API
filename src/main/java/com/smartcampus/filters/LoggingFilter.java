package com.smartcampus.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.LocalDateTime;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("\n>>>> [SERVER REQUEST] " + LocalDateTime.now());
        System.out.println("Method: " + requestContext.getMethod());
        System.out.println("Path:   " + requestContext.getUriInfo().getAbsolutePath());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        System.out.println("<<<< [SERVER RESPONSE] " + LocalDateTime.now());
        System.out.println("Status: " + responseContext.getStatus() + " " + responseContext.getStatusInfo().getReasonPhrase());
        System.out.println("--------------------------------------------------");
    }
}