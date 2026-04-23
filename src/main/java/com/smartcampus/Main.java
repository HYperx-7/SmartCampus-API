package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://localhost:8081/smartcampus/api/v1/";

    public static void main(String[] args) {

        final ResourceConfig rc = new ResourceConfig().packages("com.smartcampus.resources", "com.smartcampus.exceptions", "com.smartcampus.filters");

        // Starting the server
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        System.out.println(String.format("Jersey app started at %s\nHit enter to stop it...", BASE_URI));

        try {
            System.in.read();
            server.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}