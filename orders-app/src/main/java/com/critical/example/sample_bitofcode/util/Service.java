package com.critical.example.sample_bitofcode.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Service<T, ID extends Serializable> {
    
    public Class<T> getEntityClass();
    public T findById(final ID id);
    public List<T> findAll();
    public List<T> findAllPaginated(final Integer startPage);
    public List<T> findByNamedQuery(final String queryName, Object... params);
    public T getByNamedQuery(final String name);
    public T getByNamedQuery(final String name, final Map<String, ? extends Object> params);
    public List<T> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ?extends Object> params);
    public Long countAll();
    public T update(final T entity) throws Exception;
    public void save(final T entity) throws Exception;
    public void delete(final T entity) throws Exception;
    public List<T> listByQuery(String query) throws Exception;
    public List<T> listByQueryAndParameters(String query, final Map<String, ? extends Object> params) throws Exception;
    public List<T> listByHQL(String query) throws Exception;
    public List<T> listByHQLAndParameters(String query, final Map<String, ? extends Object> params) throws Exception;
    public T getByHQLAndParameters(String hql,final Map<String, ? extends Object> params) throws Exception;
    public T getByQueryAndParameters(String query, final Map<String, ? extends Object> params) throws Exception;
    
}