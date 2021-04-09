package com.naturals.orders.service;

import com.naturals.orders.domain.OrderItems;
import com.naturals.orders.domain.Orders;
import com.naturals.orders.util.AbstractService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

@Default
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class OrderItemsService extends AbstractService<OrderItems, Long> {

    @Inject
    private OrdersService ordersService;

    @Inject
    public OrderItemsService() {
        super(OrderItems.class, "ordersPU");
    }

    public List<OrderItems> findByOrderId(final Long orderId) {
        Orders o = ordersService.findById(orderId);
        if (o == null) {
            throw new WebApplicationException("Order doesn't exists!", Response.Status.NOT_FOUND);
        } else {
            return findByNamedQuery("OrderItems.findByOrder", o);
        }
    }
}
