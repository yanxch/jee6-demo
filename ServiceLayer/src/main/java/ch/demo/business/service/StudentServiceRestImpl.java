package ch.demo.business.service;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@RestImpl
@ApplicationScoped
public class StudentServiceRestImpl extends StudentServiceImpl {

    /** The default logger for the class. */
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /** The serial-id. */
    private static final long serialVersionUID = 1386507985359072399L;

    /**
     * Empty constructor.
     */
    public StudentServiceRestImpl() {
        super();
        LOGGER.info("This is the real Rest implementation");
    }

}
