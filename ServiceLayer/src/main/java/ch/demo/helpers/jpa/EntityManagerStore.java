/**
 * 
 */
package ch.demo.helpers.jpa;

import java.io.Serializable;
import java.util.Stack;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;

/**
 * The entity manager store partially simulates the JPA in container behavior by
 * injecting the entity manager factory. This implementation is highly based on
 * http://www.laliluna.de/articles/2011/01/12/jboss-weld-jpa-hibernate.html
 * 
 * @author hostettler
 * @author Sebastian Hennebrueder
 * 
 */
@ApplicationScoped
public class EntityManagerStore implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = -7352371086416154400L;

    /** The local logger. */
    @Inject
    private transient Logger mLogger;

    /** The factory. */
    private EntityManagerFactory mEntityManagerFactory;

    /** A thread local container for the entity managers. */
    private ThreadLocal<Stack<EntityManager>> mEmStackThreadLocal = new ThreadLocal<Stack<EntityManager>>();

    /**
     * Produces a new event manager factory.
     */
    public EntityManagerStore() {
        setEntityManagerFactory(Persistence
                .createEntityManagerFactory("JEE6Demo-Persistence"));
    }

    /**
     * Looks for the current entity manager and returns it. If no entity manager
     * was found, this method logs a warn message and returns null. This will
     * cause a NullPointerException in most cases and will cause a stack trace
     * starting from your service method.
     * 
     * @return the currently used entity manager or {@code null} if none was
     *         found
     */
    public EntityManager get() {
        mLogger.info("Getting the current entity manager");
        final Stack<EntityManager> entityManagerStack = mEmStackThreadLocal
                .get();
        if (entityManagerStack == null || entityManagerStack.isEmpty()) {
            /*
             * if nothing is found, we return null to cause a NullPointer
             * exception in the business code. This leeds to a nicer stack trace
             * starting with client code.
             */
            mLogger.warn("No entity manager was found. Did you forget to mark your method as transactional?");
            return null;
        } else {
            return entityManagerStack.peek();
        }
    }

    /**
     * Creates an entity manager and stores it in a stack. The use of a stack
     * allows to implement transaction with a 'requires new' behaviour.
     * 
     * @return the created entity manager
     */
    public EntityManager createAndRegister() {
        mLogger.info("Creating and registering an entity manager");
        Stack<EntityManager> entityManagerStack = mEmStackThreadLocal.get();
        if (entityManagerStack == null) {
            entityManagerStack = new Stack<EntityManager>();
            mEmStackThreadLocal.set(entityManagerStack);
        }

        final EntityManager entityManager = mEntityManagerFactory
                .createEntityManager();
        entityManagerStack.push(entityManager);
        return entityManager;
    }

    /**
     * Removes an entity manager from the thread local stack. It needs to be
     * created using the {@link #createAndRegister()} method.
     * 
     * @param entityManager
     *            - the entity manager to remove
     */
    public void unregister(final EntityManager entityManager) {
        mLogger.info("Unregistering an entity manager");
        final Stack<EntityManager> entityManagerStack = mEmStackThreadLocal
                .get();
        if (entityManagerStack == null || entityManagerStack.isEmpty()) {
            throw new IllegalStateException(
                    "Removing of entity manager failed. Your entity manager was not found.");
        }

        if (entityManagerStack.peek() != entityManager) {
            throw new IllegalStateException(
                    "Removing of entity manager failed. Your entity manager was not found.");
        }
        entityManagerStack.pop();
    }

    /**
     * @param entityManagerFactory
     *            the entityManagerFactory to set
     */
    protected void setEntityManagerFactory(final EntityManagerFactory entityManagerFactory) {
        mEntityManagerFactory = entityManagerFactory;
    }
}
