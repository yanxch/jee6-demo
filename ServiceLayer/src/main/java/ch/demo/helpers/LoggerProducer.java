package ch.demo.helpers;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Produce a slf4j logger.
 * @author hostettler
 *
 */
public class LoggerProducer  implements Serializable {

    /** The serial-id.  */
	private static final long serialVersionUID = -7352371086416154400L;

	/**
     * Produces an instance of a slf4j logger for the given injection point.
     * @param injectionPoint to use
     * @return a logger
     */
    @Produces
    public Logger getLogger(final InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getBean().getBeanClass());
    }

}