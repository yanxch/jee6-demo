/**
 * 
 */
package ch.demo.helpers.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author hostettler
 *
 */
@ApplicationScoped
public class EntityManagerFactoryProducer {

    /**
     * @return an entity manager factory.
     */
    @Produces
    public EntityManagerFactory produceEntityManager() {
        return Persistence.createEntityManagerFactory("JEE6Demo-Persistence");    
    }
    
}
