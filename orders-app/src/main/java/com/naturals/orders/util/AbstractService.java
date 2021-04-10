package com.naturals.orders.util;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class AbstractService<T, ID extends Serializable> implements Service<T, ID> {

    private final Class<T> persistentClass;
    private final String persistenceUnitName;
    protected EntityManager entityManager;

    @Resource
    private UserTransaction utx;

    @Inject
    protected ConfigHelper configHelper;

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

        return emf.createEntityManager();
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
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<T> findAllPaginated(final Integer startPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(persistentClass);
        cq.select(cq.from(persistentClass));

        int pageSize = configHelper.getInteger("com.naturals.orders.pageSize");

        Query q = entityManager.createQuery(cq);
        q.setFirstResult((startPage - 1) * pageSize);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }

    @Override
    public T findById(final ID id) {
        entityManager.clear();
        return entityManager.find(persistentClass, id);
    }

    @Override
    public List<T> findByNamedQuery(final String name, Object... params) {
        Query query = entityManager.createNamedQuery(name);
        entityManager.clear();

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        return (List<T>) query.getResultList();
    }

    @Override
    public List<T> findByNamedQueryAndNamedParams(final String name, final Map<String, ?> params) {
        Query query = entityManager.createNamedQuery(name);
        entityManager.clear();

        for (final Map.Entry<String, ?> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        return (List<T>) query.getResultList();
    }

    @Override
    public T getByNamedQuery(final String name) {
        return getByNamedQuery(name, null);
    }

    @Override
    public T getByNamedQuery(final String name, final Map<String, ?> params) {
        Query query = entityManager.createNamedQuery(name);
        entityManager.clear();

        if (params != null) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }

        return (T) query.getSingleResult();
    }

    @Override
    public void delete(T entity) throws Exception {
        try {
            utx.begin();
            entityManager.remove(entity);
            utx.commit();
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
        } catch (Exception e) {
            utx.rollback();
            throw e;
        }
    }

    @Override
    public List<T> listByQuery(final String query) {
        return this.listByQueryAndParameters(query, null);
    }

    @Override
    public List<T> listByHQL(final String query) {
        return this.listByHQLAndParameters(query, null);
    }

    @Override
    public List<T> listByQueryAndParameters(final String query, final Map<String, ?> params) {
        entityManager.clear();
        Query nativeQuery = entityManager.createNativeQuery(query, persistentClass);

        if (params != null) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                nativeQuery.setParameter(param.getKey(), param.getValue());
            }
        }
        return (List<T>) nativeQuery.getResultList();

    }

    @Override
    public List<T> listByHQLAndParameters(final String query, final Map<String, ?> params) {
        entityManager.clear();
        Query hqlQuery = entityManager.createQuery(query);

        if (params != null) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                hqlQuery.setParameter(param.getKey(), param.getValue());
            }
        }
        return (List<T>) hqlQuery.getResultList();

    }

    @Override
    public T getByHQLAndParameters(final String hql, final Map<String, ?> params) {

        entityManager.clear();
        Query query = entityManager.createQuery(hql);

        if (params != null) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }

        query.setFlushMode(FlushModeType.AUTO);

        return (T) query.getSingleResult();

    }


    @Override
    public T getByQueryAndParameters(final String query, final Map<String, ?> params) {

        entityManager.clear();
        Query sqlQuery = entityManager.createQuery(query);

        if (params != null) {
            for (final Map.Entry<String, ?> param : params.entrySet()) {
                sqlQuery.setParameter(param.getKey(), param.getValue());
            }
        }

        sqlQuery.setFlushMode(FlushModeType.AUTO);

        return (T) sqlQuery.getSingleResult();

    }

    public String addClause(final String SQL, final String clause) {
        StringBuilder where = new StringBuilder();

        if (!SQL.toUpperCase().contains("WHERE")) {
            where.append("\n WHERE ");
        } else {
            where.append("\n AND ");
        }

        where.append(clause);

        return where.toString();

    }
}
