package com.critical.example.sample_bitofcode.service;

import com.critical.example.sample_bitofcode.domain.OrderStatus;
import com.critical.example.sample_bitofcode.domain.Orders;
import com.critical.example.sample_bitofcode.util.AbstractService;

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
public class OrderStatusService extends AbstractService<OrderStatus, Long> {

    @Inject
    private OrdersService ordersService;

    @Inject
    public OrderStatusService() {
        super(OrderStatus.class, "ordersPU");
    }

    public List<OrderStatus> findByOrderId(final Long orderId) {
        Orders o = ordersService.findById(orderId);
        if (o == null) {
            throw new WebApplicationException("Order doesn't exists!", Response.Status.NOT_FOUND);
        } else {
            return findByNamedQuery("OrderStatus.findByOrder", o);
        }
    }
}
