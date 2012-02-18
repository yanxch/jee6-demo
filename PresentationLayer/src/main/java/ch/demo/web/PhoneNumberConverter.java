/**
 * 
 */
package ch.demo.web;

import java.util.Formatter;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import ch.demo.business.dom.PhoneNumber;

/**
 * Converts a string into a PhoneNumber object.
 * 
 * @author hostettler
 * 
 */
public class PhoneNumberConverter implements Converter {

	/** The Phone number regular expression. */
	private static final String PHONE_NUMBER_PATTERN = "^[0-9][0-9]-[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$";
	/** Initialize the pattern. */
	private Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

	/**
	 * Default constructor.
	 */
	public PhoneNumberConverter() {
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

		boolean conversionError = false;

		int hyphenCount = 0;
		StringTokenizer hyphenTokenizer = new StringTokenizer(value, "-");

		Integer countryCode = null;
		Integer areaCode = null;
		Long number = null;

		while (hyphenTokenizer.hasMoreTokens()) {
			String token = hyphenTokenizer.nextToken();
			try {
				if (hyphenCount == 0) {
					countryCode = Integer.parseInt(token);
				}

				if (hyphenCount == 1) {
					areaCode = Integer.parseInt(token);
				}

				if (hyphenCount == 2) {
					number = Long.parseLong(token);
				}
				hyphenCount++;
			} catch (Exception exception) {
				conversionError = true;
			}
		}

		PhoneNumber phoneNumber = null;

		if (conversionError || (hyphenCount != 3)) {
			throw new ConverterException();
		} else {
			phoneNumber = new PhoneNumber(countryCode, areaCode, number);
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
		PhoneNumber phoneNumber = null;
		if (value instanceof PhoneNumber) {
			phoneNumber = (PhoneNumber) value;
			Formatter f = new Formatter(new StringBuilder());
			return f.format("+%02d-%04d-%04d", phoneNumber.getCountryCode(), phoneNumber.getAreaCode(),
					phoneNumber.getNumber()).toString();
		}
		return "";
	}
}
