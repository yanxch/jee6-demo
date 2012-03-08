/**
 * 
 */
package ch.demo.business.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.persistence.Persistence;

import ch.demo.helpers.jpa.EntityManagerStore;

/**
 * The entity manager store partially simulates the JPA in container behavior by
 * injecting the entity manager factory. This implementation is highly based on
 * http://www.laliluna.de/articles/2011/01/12/jboss-weld-jpa-hibernate.html
 * 
 * @author hostettler
 * @author Sebastian Hennebrueder
 * 
 */
@Alternative
@ApplicationScoped
public class EntityManagerStore4Test extends EntityManagerStore implements
        Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = 7555845018816625696L;

    /**
     * Produces a new event manager factory.
     */
    public EntityManagerStore4Test() {
        setEntityManagerFactory(Persistence
                .createEntityManagerFactory("JEE6Demo-Test-Persistence"));
    }

}
