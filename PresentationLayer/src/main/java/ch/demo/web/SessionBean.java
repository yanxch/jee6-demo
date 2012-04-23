package ch.demo.web;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ch.demo.helpers.SecurityContext;

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

	/** The name of the username field in the login page. */
	private static final String USERNAME = "username";
	/** The name of the password field in the login page. */
	private static final String PASSWORD = "password";
	/** The name of the admin role. */
	private static final String ADMIN = "admin";

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
	public String getUsername() {
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
		if (localeCode == null) {
			FacesMessage msg = new FacesMessage("Unknown locale");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			throw new ValidatorException(msg);
		}
		FacesContext.getCurrentInstance().getViewRoot().setLocale(localeCode);

		this.mLocale = localeCode;
	}

	/**
	 * Invalidates the session.
	 * 
	 * @return a string to navigate to the login page
	 */
	public String logout() {
		HttpSession session = getSession();
		if (session != null) {
			session.invalidate();
		}
		SecurityContext.removePrincipal();
		return "logout";
	}

	/**
	 * Logs into the standard JEE login system.
	 * 
	 * @return "success" upon correct login and "failure" otherwise
	 */
	public String login() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			String user = getRequest().getParameter(USERNAME);
			String password = getRequest().getParameter(PASSWORD);
			HttpServletRequest request = ((HttpServletRequest) fc.getExternalContext().getRequest());
			request.login(user, password);
			
			// The following line sets the principal in thread local to be used by the back end beans.
			// This is not meant to use is real project and is there only for demonstration purposes.
			SecurityContext.setPrincipal(SecurityListener.getMyPrincipal(request));

		} catch (ServletException ex) {
			FacesMessage message = new FacesMessage();

			ResourceBundle bundle = fc.getApplication().getResourceBundle(fc, "msgs");
			message.setDetail(bundle.getString("errorLoginForbidden"));
			message.setSummary(bundle.getString("errorLoginInvalid"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage("loginError", message);
			return "failure";
		}

		return "success";
	}

	/**
	 * @return whether the current user is an admin.
	 */
	public boolean isAdmin() {
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(ADMIN);
	}

	/**
	 * @return the current session
	 */
	private HttpSession getSession() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return (HttpSession) fc.getExternalContext().getSession(false);
	}

	/**
	 * @return the current request
	 */
	private HttpServletRequest getRequest() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return (HttpServletRequest) fc.getExternalContext().getRequest();
	}

}