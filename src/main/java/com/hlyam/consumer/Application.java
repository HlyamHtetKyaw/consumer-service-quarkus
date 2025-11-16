package com.hlyam.consumer;

import com.hlyam.consumer.service.ConsumerService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1")
public class Application {
	
	@Inject
	ConsumerService consumerService;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/consume/{food}")
    public String consume(@PathParam("food") String food) {
        return consumerService.consume(food);
    }
}