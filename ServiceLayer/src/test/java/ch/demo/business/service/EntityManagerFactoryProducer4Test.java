/**
 * 
 */
package ch.demo.business.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author hostettler
 * 
 */
@Alternative
@ApplicationScoped
public class EntityManagerFactoryProducer4Test {

    /**
     * @return an entity manager factory for test purposes
     */
    @Produces
    public EntityManagerFactory produceEntityManager() {
            return Persistence.createEntityManagerFactory("JEE6Demo-Test-Persistence");    
    }
}
