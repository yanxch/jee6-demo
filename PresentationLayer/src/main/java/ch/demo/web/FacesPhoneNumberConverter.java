/**
 * 
 */
package ch.demo.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import ch.demo.dom.PhoneNumber;

/**
 * Converts a string into a PhoneNumber object.
 * 
 * @author hostettler
 * 
 */
public class FacesPhoneNumberConverter implements Converter {

	/** The Phone number regular expression. */
	private static final String PHONE_NUMBER_PATTERN = "^[0-9][0-9]-[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$";
	/** Initialize the pattern. */
	private Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

	/**
	 * Default constructor.
	 */
	public FacesPhoneNumberConverter() {
	}

	/**
	 * @param context
	 *            of the application
	 * @param component
	 *            to display
	 * @param value
	 *            to convert
	 * @return an object that corresponds to the string representation.
	 */
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null || (value.trim().length() == 0)) {
			return value;
		}

		Matcher matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Phone number not valid: Should be of the form 00-0000-0000");
			message.setSummary("Phone number not valid");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(message);
		}

		PhoneNumber phoneNumber = null;
		try {
			phoneNumber = PhoneNumber.getAsObject(value);
		} catch (Exception e) {
			throw new ConverterException(e);
		}

		return phoneNumber;
	}

	/**
	 * @param context
	 *            of the application
	 * @param component
	 *            to display
	 * @param value
	 *            to convert
	 * @return a string representation for the PhoneNumber object.
	 */
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value instanceof PhoneNumber) {
			return PhoneNumber.getAsString((PhoneNumber) value);
		}
		return "";
	}
}
