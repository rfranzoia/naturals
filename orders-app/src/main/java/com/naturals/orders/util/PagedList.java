package com.naturals.orders.util;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagedList<T extends Object> implements Serializable {

    @JsonbProperty
    private final Integer totalPages;

    @JsonbProperty
    private final Integer currentPage;

    @JsonbProperty
    private final List<T> data;

    private PagedList(Integer totalPages, Integer currentPage, List<T> data) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.data = data;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public static PagedListBuilder newBuilder() {
        return new PagedListBuilder();
    }

    public static class PagedListBuilder<T extends Object> {
        private Integer totalPages;
        private Integer currentPage;
        private List<T> data = new ArrayList<>();

        public PagedListBuilder<T> setTotalPages(final Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public PagedListBuilder<T> setCurrentPage(final Integer currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public PagedListBuilder<T> setData(final List<T> data) {
            this.data = data;
            return this;
        }

        public PagedList<T> createPagedList() {
            return new PagedList<T>(totalPages, currentPage, data);
        }
    }
}