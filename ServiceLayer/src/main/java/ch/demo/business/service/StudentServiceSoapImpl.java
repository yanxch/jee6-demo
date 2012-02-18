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
@SoapImpl
@ApplicationScoped
public class StudentServiceSoapImpl extends StudentServiceImpl {

	/** The default logger for the class. */
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	/** The serial-id. */
	private static final long serialVersionUID = 1386508985359072399L;

	/**
	 * Empty constructor.
	 */
	public StudentServiceSoapImpl() {
		super();
		LOGGER.info("This is the real Soap implementation");
	}
}
