package com.naturals.orders.service;

import com.naturals.orders.domain.Orders;
import com.naturals.orders.util.AbstractService;
import com.naturals.orders.util.PagedList;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Default
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class OrdersService extends AbstractService<Orders, Long> {

    @Inject
    public OrdersService() {
        super(Orders.class, "ordersPU");
    }

    @SuppressWarnings("unchecked")
    public PagedList<Orders> listAllPaginated(Integer page) {
        List<Orders> data = findAllPaginated(page);
        Long count = countAll();
        int pageSize = configHelper.getInteger("com.naturals.orders.pageSize");

        Integer totalPages = (count.intValue() / pageSize) + (count % pageSize == 0?0:1);

        return PagedList.newBuilder()
                .setTotalPages(totalPages)
                .setCurrentPage(page)
                .setData(data)
                .createPagedList();
    }


}
