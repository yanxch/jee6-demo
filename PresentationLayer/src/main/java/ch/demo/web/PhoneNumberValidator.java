package ch.demo.web;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ch.demo.business.dom.PhoneNumber;

/**
 * Ensures that the given value is a correct Phone Number.
 * 
 * @author hostettler
 * 
 */
public class PhoneNumberValidator implements Validator {

	/**
	 * Ensures that the given value is a correct Phone Number.
	 * 
	 * @param context
	 *            of the application
	 * @param component
	 *            to display
	 * @param value
	 *            to convert
	 */
	@Override
	public void validate(final FacesContext context, final UIComponent component, final Object value) {

		PhoneNumber number = (PhoneNumber) value;
		if (number.getCountryCode() == 41) {
			FacesMessage message = new FacesMessage();
			
			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");

					
			message.setDetail(bundle.getString("errorSwissNumberForbidden"));
			message.setSummary(bundle.getString("errorPhoneInvalid"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
