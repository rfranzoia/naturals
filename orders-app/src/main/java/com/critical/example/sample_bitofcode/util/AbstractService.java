package com.critical.example.sample_bitofcode.util;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class AbstractService<T, ID extends Serializable> implements Service<T, ID> {

    @Resource
    private UserTransaction utx;
    
    private final Class<T> persistentClass;
    private final int pageSize = 10;
    private final String persistenceUnitName;

    protected EntityManager entityManager;

    public AbstractService(final Class<T> persistentClass, String persistenceUnitName) {
        this.persistentClass = persistentClass;
        this.persistenceUnitName = persistenceUnitName;

        entityManager = dynamicLoad();
    }

    private EntityManager dynamicLoad() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);

        if (!emf.isOpen()) {
            throw new RuntimeException("Database Communication Error!");
        }

        return (EntityManager) emf.createEntityManager();
    }

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public Class<T> getEntityClass() {
        return persistentClass;
    }

    @Override
    public Long countAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(persistentClass)));
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(persistentClass);
        cq.select(cq.from(persistentClass));
        return (List<T>) entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<T> findAllPaginated(final Integer startPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(persistentClass);
        cq.select(cq.from(persistentClass));

        Query q = entityManager.createQuery(cq);
        q.setFirstResult((startPage - 1) * pageSize);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }

    @Override
    public T findById(final ID id) {
        entityManager.clear();
        final T result = entityManager.find(persistentClass, id);
        return result;
    }

    @Override
    public List<T> findByNamedQuery(final String name, Object... params) {
        javax.persistence.Query query = entityManager.createNamedQuery(name);
        entityManager.clear();
        
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        final List<T> result = (List<T>) query.getResultList();
        return result;
    }

    @Override
    public List<T> findByNamedQueryAndNamedParams(final String name, final Map<String, ? extends Object> params) {
        javax.persistence.Query query = entityManager.createNamedQuery(name);
        entityManager.clear();
        
        for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        final List<T> result = (List<T>) query.getResultList();
        return result;
    }
    
    @Override
    public T getByNamedQuery(final String name) {
        return getByNamedQuery(name, null);
    }
    
    @Override
    public T getByNamedQuery(final String name, final Map<String, ? extends Object> params) {
        javax.persistence.Query query = entityManager.createNamedQuery(name);
        entityManager.clear();
        
        if (params != null) {
            for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
        
        final List<T> result = (List<T>) query.getResultList();
        
        if (!result.isEmpty()) {
            return result.get(0);
        }
        
        return null;
    }

    @PersistenceContext
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void delete(T entity) throws Exception {
        try {
            utx.begin();
            entityManager.remove(entity);
            utx.commit();
        } catch (RollbackException rex) {
            throw rex;
            
        } catch (Exception e) {
            utx.rollback();
            throw e;
        }
    }

    @Override
    public T update(T entity) throws Exception {
        try {
            utx.begin();
            final T savedEntity = entityManager.merge(entity);
            utx.commit();
            return savedEntity;    
        } catch (RollbackException rex) {
            throw rex;
            
        } catch (Exception e) {
            utx.rollback();
            throw e;
        }
    }
    
    @Override
    public void save(T entity) throws Exception {
        try {
            utx.begin();
            entityManager.persist(entity);
            utx.commit();
        } catch (RollbackException rex) {
            throw rex;

        } catch (Exception e) {
            utx.rollback();
            throw e;
        }
    }
    
    @Override
    public List<T> listByQuery(String query) throws Exception { 
        return this.listByQueryAndParameters(query, null);
    }
    
    @Override
    public List<T> listByHQL(String query) throws Exception { 
        return this.listByHQLAndParameters(query, null);
    }
    
    @Override
    public List<T> listByQueryAndParameters(String query, final Map<String, ? extends Object> params)
        throws Exception {
        try {
            entityManager.clear();
            Query nativeQuery = entityManager.createNativeQuery(query, persistentClass.getClass());

            if (params != null) {
                for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
                    nativeQuery.setParameter(param.getKey(), param.getValue());
                }
            }
            return (List<T>) nativeQuery.getResultList();
        } catch (Exception e) {
            throw e;
        }
            
    }
    
    @Override
    public List<T> listByHQLAndParameters(String query, final Map<String, ? extends Object> params)
        throws Exception {
        try {
            entityManager.clear();
            Query hqlQuery = entityManager.createQuery(query);
            
            if (params != null) {
                for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
                    hqlQuery.setParameter(param.getKey(), param.getValue());       
                }
            }
            return (List<T>) hqlQuery.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            
        }
            
    }

    @Override
    public T getByHQLAndParameters(String hql,final Map<String, ? extends Object> params)
        throws Exception {
        
        try {
            entityManager.clear();
            Query query = entityManager.createQuery(hql);
            
            if (params != null) {
                for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
                    query.setParameter(param.getKey(), param.getValue());
                }
            }

            query.setFlushMode(FlushModeType.AUTO);
            
            return (T) query.getSingleResult();
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    
    @Override
    public T getByQueryAndParameters(String query, final Map<String, ? extends Object> params) 
        throws Exception {
        
        try {
            entityManager.clear();
            Query sqlQuery = entityManager.createQuery(query);

            if (params != null) {
                for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
                    sqlQuery.setParameter(param.getKey(), param.getValue());
                }
            }

            sqlQuery.setFlushMode(FlushModeType.AUTO);
            
            return (T) sqlQuery.getSingleResult();
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    public String addClausule(String SQL, String clausule) {
        String where = "";

        if (SQL.toUpperCase().indexOf("WHERE") < 0) {
            where += "\n WHERE ";
        } else {
            where += "\n AND ";
        }

        where += clausule;

        return where;

    }
}
