package com.naturals.orders.resource;

import com.naturals.orders.domain.OrderItems;
import com.naturals.orders.service.OrderItemsService;
import com.naturals.orders.util.BasicResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/orderItems")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class OrderItemsResource extends BasicResource {

    @Inject
    private OrderItemsService service;

    @GET
    @Path("/order/{orderId}")
    public Response listByOrderId(@PathParam("orderId") final Long orderId) {
        return ok(service.findByOrderId(orderId));
    }

    @POST
    @Path("/order/{orderId}")
    public Response insertIntoOrder(@PathParam("orderId") final Long orderId, List<OrderItems> orderItems) {
        return ok("Order Items successfully added to Order");
    }

    @DELETE
    @Path("/order/{orderId}")
    public Response deleteByOrderId(@PathParam("orderId") final Long orderId) {
        return ok("Order Items successfully removed from order");
    }
}
