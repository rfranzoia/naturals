package com.critical.example.sample_bitofcode.resource;

import com.critical.example.sample_bitofcode.domain.OrderItems;
import com.critical.example.sample_bitofcode.domain.Orders;
import com.critical.example.sample_bitofcode.service.OrdersService;
import com.critical.example.sample_bitofcode.util.BasicResource;
import com.critical.example.sample_bitofcode.util.MessageHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/orders")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource extends BasicResource {

    @Inject
    private OrdersService service;

    @Inject
    private MessageHelper messageHelper;

    @GET
    public Response list(@QueryParam("page") @DefaultValue("-1") Integer page) {
        if (page <= 0) {
            return ok(service.findAll());
        } else {
            return ok(service.listAllPaginated(page));
        }
    }

    @GET
    @Path("{orderId}")
    public Response get(@PathParam("orderId") Long orderId) {
        return ok(service.findById(orderId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Orders order, @Context UriInfo uriInfo) {
        try {
            service.save(order);
            URI location = uriInfo.getRequestUriBuilder()
                    .path(order.getId().toString())
                    .build();
            return created(location, "Order created successfully");
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{orderId}")
    public Response delete(@PathParam("orderId") Long orderId) {
        try {
            service.delete(service.findById(orderId));
            return ok("Order successfully removed");
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}