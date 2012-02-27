/**
 * 
 */
package ch.demo.helpers.jpa;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

/**
 * This class proxies an entity manager so that is simulates in container persistence.
 * @author hostettler
 * @author Sebastian Hennebrueder
 */
@ApplicationScoped
public class EntityManagerDelegate implements EntityManager {

    /** The entity manager used for deleguation. */
    @Inject
    private EntityManagerStore entityManagerStore;

    @Override
    public void persist(final Object entity) {
        entityManagerStore.get().persist(entity);
    }

    @Override
    public <T> T merge(final T entity) {
        return entityManagerStore.get().merge(entity);
    }

    @Override
    public void remove(final Object entity) {
        entityManagerStore.get().remove(entity);
    }

    @Override
    public <T> T find(final Class<T> entityClass, final Object primaryKey) {
        return entityManagerStore.get().find(entityClass, primaryKey);
    }

    @Override
    public <T> T find(final Class<T> entityClass, final Object primaryKey,
            final Map<String, Object> properties) {
        return entityManagerStore.get().find(entityClass, primaryKey,
                properties);
    }

    @Override
    public <T> T find(final Class<T> entityClass, final Object primaryKey,
            final LockModeType lockMode) {
        return entityManagerStore.get().find(entityClass, primaryKey, lockMode);
    }

    @Override
    public <T> T find(final Class<T> entityClass, final Object primaryKey,
            final LockModeType lockMode, final Map<String, Object> properties) {
        return entityManagerStore.get().find(entityClass, primaryKey, lockMode,
                properties);
    }

    @Override
    public <T> T getReference(final Class<T> entityClass, final Object primaryKey) {
        return entityManagerStore.get().getReference(entityClass, primaryKey);
    }

    @Override
    public void flush() {
        entityManagerStore.get().flush();
    }

    @Override
    public void setFlushMode(final FlushModeType flushMode) {
        entityManagerStore.get().setFlushMode(flushMode);
    }

    @Override
    public FlushModeType getFlushMode() {
        return entityManagerStore.get().getFlushMode();
    }

    @Override
    public void lock(final Object entity, final LockModeType lockMode) {
        entityManagerStore.get().lock(entity, lockMode);
    }

    @Override
    public void lock(final Object entity, final LockModeType lockMode,
            final Map<String, Object> properties) {
        entityManagerStore.get().lock(entity, lockMode, properties);
    }

    @Override
    public void refresh(final Object entity) {
        entityManagerStore.get().refresh(entity);
    }

    @Override
    public void refresh(final Object entity, final Map<String, Object> properties) {
        entityManagerStore.get().refresh(entity, properties);
    }

    @Override
    public void refresh(final Object entity, final LockModeType lockMode) {
        entityManagerStore.get().refresh(entity, lockMode);
    }

    @Override
    public void refresh(final Object entity, final LockModeType lockMode,
            final Map<String, Object> properties) {
        entityManagerStore.get().refresh(entity, lockMode, properties);
    }

    @Override
    public void clear() {
        entityManagerStore.get().clear();
    }

    @Override
    public void detach(final Object entity) {
        entityManagerStore.get().detach(entity);
    }

    @Override
    public boolean contains(final Object entity) {
        return entityManagerStore.get().contains(entity);
    }

    @Override
    public LockModeType getLockMode(final Object entity) {
        return entityManagerStore.get().getLockMode(entity);
    }

    @Override
    public void setProperty(final String propertyName, final Object value) {
        entityManagerStore.get().setProperty(propertyName, value);
    }

    @Override
    public Map<String, Object> getProperties() {
        return entityManagerStore.get().getProperties();
    }

    @Override
    public Query createQuery(final String qlString) {
        return entityManagerStore.get().createQuery(qlString);
    }

    @Override
    public <T> TypedQuery<T> createQuery(final CriteriaQuery<T> criteriaQuery) {
        return entityManagerStore.get().createQuery(criteriaQuery);
    }

    @Override
    public <T> TypedQuery<T> createQuery(final String qlString, final Class<T> resultClass) {
        return entityManagerStore.get().createQuery(qlString, resultClass);
    }

    @Override
    public Query createNamedQuery(final String name) {
        return entityManagerStore.get().createNamedQuery(name);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(final String name, final Class<T> resultClass) {
        return entityManagerStore.get().createNamedQuery(name, resultClass);
    }

    @Override
    public Query createNativeQuery(final String sqlString) {
        return entityManagerStore.get().createNativeQuery(sqlString);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Query createNativeQuery(final String sqlString, final Class resultClass) {
        return entityManagerStore.get().createNativeQuery(sqlString,
                resultClass);
    }

    @Override
    public Query createNativeQuery(final String sqlString, final String resultSetMapping) {
        return entityManagerStore.get().createNativeQuery(sqlString,
                resultSetMapping);
    }

    @Override
    public void joinTransaction() {
        entityManagerStore.get().joinTransaction();
    }

    @Override
    public <T> T unwrap(final Class<T> cls) {
        return entityManagerStore.get().unwrap(cls);
    }

    @Override
    public Object getDelegate() {
        return entityManagerStore.get().getDelegate();
    }

    @Override
    public void close() {
        entityManagerStore.get().close();
    }

    @Override
    public boolean isOpen() {
        return entityManagerStore.get().isOpen();
    }

    @Override
    public EntityTransaction getTransaction() {
        return entityManagerStore.get().getTransaction();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerStore.get().getEntityManagerFactory();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return entityManagerStore.get().getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return entityManagerStore.get().getMetamodel();
    }
}
