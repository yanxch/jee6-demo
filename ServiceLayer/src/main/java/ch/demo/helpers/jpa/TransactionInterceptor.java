/**
 * 
 */
package ch.demo.helpers.jpa;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

/**
 * A simple transaction interceptor which registers an entity mangager in a
 * ThreadLocal and unregisters after the method was called. It does not support
 * any kind of context propagation. If a transactional method calls another's
 * bean transactional method a new entity manager is created and added to the
 * stack.
 * 
 * @author Sebastian Hennebrueder
 */
@Interceptor
@Transactional
public class TransactionInterceptor {

    /** The entity manager that comes from the entity manager store. */
    @Inject
    private EntityManagerStore mEntityManagerStore;

    /** local logger. */
    @Inject
    private transient Logger mLogger;

    /**
     * The interceptor itself. It starts transaction, watches for exception and commit or
     * rollback accordingly.
     * @param invocationContext used for invokation
     * @return the result of the invoked method.
     * @throws Exception if anything wrong happens
     */
    @AroundInvoke
    public Object runInTransaction(final InvocationContext invocationContext)
            throws Exception {

        EntityManager em = mEntityManagerStore.createAndRegister();

        Object result = null;
        try {
            em.getTransaction().begin();

            result = invocationContext.proceed();

            em.getTransaction().commit();

        } catch (Exception e) {
            try {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                    mLogger.debug("Rolled back transaction");
                }
            } catch (Exception e1) {
                mLogger.warn("Rollback of transaction failed -> " + e1);
            }
            throw e;
        } finally {
            if (em != null) {
                mEntityManagerStore.unregister(em);
                em.close();
            }
        }

        return result;
    }
}
