package ch.demo.web;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * Manage the locales of the application. This session bean contains the locale information of the current (attached to
 * the session) user.
 * 
 * @author hostettler
 * 
 */
@Named
@SessionScoped
public class SessionBean implements Serializable {

	/** The serial-id. */
	private static final long serialVersionUID = 6401571000111241780L;

	/** The list of languages that are supported by the application. */
	private static List<Locale> mLanguages;

	/** The current locale. */
	private Locale mLocale;

	/** Let us initialize to languages. */
	static {
		Locale enUS = new Locale(Locale.ENGLISH.getLanguage(), Locale.US.getCountry());
		Locale frCH = new Locale(Locale.FRENCH.getLanguage(), "CH");

		mLanguages = new ArrayList<Locale>();
		mLanguages.add(enUS);
		mLanguages.add(frCH);

	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (principal != null) {
			return principal.getName();
		}
		return "guest";
	}

	/**
	 * @return the country list.
	 */
	public Collection<Locale> getCountries() {
		return mLanguages;
	}

	/**
	 * @return the current locale
	 */
	public Locale getLocaleCode() {
		if (this.mLocale == null) {
			return FacesContext.getCurrentInstance().getViewRoot().getLocale();
		}
		return this.mLocale;
	}

	/**
	 * Sets the current locale.
	 * 
	 * @param localeCode
	 *            tos set
	 */
	public void setLocaleCode(final Locale localeCode) {
		this.mLocale = localeCode;
	}

	/**
	 * Listener that watch the select one item list.
	 * 
	 * @param e
	 *            the event to process
	 */
	public void countryLocaleCodeChanged(final ValueChangeEvent e) {
		Locale newLocale = (Locale) e.getNewValue();

		if (newLocale == null) {
			FacesMessage msg = new FacesMessage("Unknown locale");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			throw new ValidatorException(msg);
		}
		FacesContext.getCurrentInstance().getViewRoot().setLocale(newLocale);
	}

	/**
	 * Invalidates the session.
	 * @return a string to navigate to the login page
	 */
	public String logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "logout";
	}

}