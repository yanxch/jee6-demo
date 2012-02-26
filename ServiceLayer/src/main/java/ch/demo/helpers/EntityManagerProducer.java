/**
 * 
 */
package ch.demo.helpers;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



/**
 * @author hostettler
 *
 */
public class EntityManagerProducer implements Serializable {

    /** The serial-id.  */
    private static final long serialVersionUID = -7352371086416154400L;

    /**
     * Produces an instance of an entity manager for the given injection point.
     * @param injectionPoint to use
     * @return a logger
     */
    @Produces
    public EntityManager getEntityManager(final InjectionPoint injectionPoint) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JEE6Demo-Test-Persistence");
        return emf.createEntityManager();
    }

}
