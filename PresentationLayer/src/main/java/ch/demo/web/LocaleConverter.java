/**
 * 
 */
package ch.demo.web;

import java.util.Locale;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Converts a string into a Locale object.
 * @author hostettler
 * 
 */
public class LocaleConverter implements Converter {

	/**
	 * Default constructor.
	 */
	public LocaleConverter() { }

	
	/**
	 * @param context of the application
	 * @param component to display
	 * @param value to convert
	 * @return  an object that corresponds to the string representation.
	 */	
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null || (value.trim().length() == 0)) {
			return value;
		}

		/* a locale string representation is of the form language_COUNTRY. */
		int hyphenCount = 0;
		boolean conversionError = false;
		StringTokenizer hyphenTokenizer = new StringTokenizer(value, "_");
		
		String language = null;
		String countryCode = null;
		
		while (hyphenTokenizer.hasMoreTokens()) {
			String token = hyphenTokenizer.nextToken();
			try {
				if (hyphenCount == 0) {
					language = token;
				}

				if (hyphenCount == 1) {
					countryCode = token;
				}
				hyphenCount++;
			} catch (Exception exception) {
				conversionError = true;
			}
		}

		Locale locale = null;
		
		if (conversionError || (hyphenCount != 2)) {
			throw new ConverterException();
		} else {
			locale = new Locale(language, countryCode);
		}

		return locale;
	}

	/**
	 * @param context of the application
	 * @param component to display
	 * @param value to convert
	 * @return  a string representation for the Locale object.
	 */
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		Locale locale = null;
		if (value instanceof Locale) {
			locale = (Locale) value;
			return locale.toString();
		}
		return "";
	}
}
