package com.critical.example.sample_bitofcode.service;

import com.critical.example.sample_bitofcode.domain.OrderItems;
import com.critical.example.sample_bitofcode.domain.Orders;
import com.critical.example.sample_bitofcode.util.AbstractService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
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
