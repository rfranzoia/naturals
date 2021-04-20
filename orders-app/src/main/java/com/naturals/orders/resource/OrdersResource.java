package com.naturals.orders.resource;

import com.naturals.orders.domain.Orders;
import com.naturals.orders.service.OrdersService;
import com.naturals.orders.util.BasicResource;
import com.naturals.orders.util.ConfigHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@ApplicationScoped
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource extends BasicResource {

    @Inject
    private OrdersService service;

    @Inject
    private ConfigHelper configHelper;

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
