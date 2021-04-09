package com.critical.example.sample_bitofcode.resource;

import com.critical.example.sample_bitofcode.domain.OrderItems;
import com.critical.example.sample_bitofcode.domain.OrderStatus;
import com.critical.example.sample_bitofcode.service.OrderStatusService;
import com.critical.example.sample_bitofcode.util.BasicResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/orderStatus")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class OrderStatusResource extends BasicResource {

    @Inject
    private OrderStatusService service;

    @GET
    @Path("/order/{orderId}")
    public Response listByOrderId(@PathParam("orderId") final Long orderId) {
        return ok(service.findByOrderId(orderId));
    }

    @POST
    @Path("/order/{orderId}")
    public Response insertIntoOrder(@PathParam("orderId") final Long orderId, OrderStatus orderStatus) {
        return ok("Order Status successfully added to Order");
    }

}
