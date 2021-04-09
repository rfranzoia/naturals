package com.critical.example.sample_bitofcode.service;

import com.critical.example.sample_bitofcode.domain.OrderItems;
import com.critical.example.sample_bitofcode.domain.Orders;
import com.critical.example.sample_bitofcode.util.AbstractService;
import com.critical.example.sample_bitofcode.util.PagedList;

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
        Integer totalPages = (count.intValue() / getPageSize()) + (count % getPageSize() == 0?0:1);

        return PagedList.newBuilder()
                .setTotalPages(totalPages)
                .setCurrentPage(page)
                .setData(data)
                .createPagedList();
    }


}
