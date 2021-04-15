package com.naturals.orders.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Service<T, ID extends Serializable> {
    
     Class<T> getEntityClass();
     T findById(final ID id);
     List<T> findAll();
     List<T> findAllPaginated(final Integer startPage);
     List<T> findByNamedQuery(final String queryName, Object... params);
     T getByNamedQuery(final String name);
     T getByNamedQuery(final String name, final Map<String, ?> params);
     List<T> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ?extends Object> params);
     Long countAll();
     T update(final T entity) throws Exception;
     void save(final T entity) throws Exception;
     void delete(final T entity) throws Exception;
     List<T> listByNativeQuery(String query) throws Exception;
     List<T> listByNativeQueryAndParameters(String query, final Map<String, ?> params) throws Exception;
     List<T> listByHQL(String query) throws Exception;
     List<T> listByJPQLAndParameters(String query, final Map<String, ?> params) throws Exception;
     T getByJPQLAndParameters(final String query, final Object... params);
     T getByJPQLAndNamedParameters(String query, final Map<String, ?> params) throws Exception;
    
}