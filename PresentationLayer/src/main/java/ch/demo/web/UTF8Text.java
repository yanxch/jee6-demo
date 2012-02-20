package ch.demo.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * This helper loads resources in UTF-8 format and expose them the JSF. Taken from
 * http://jdevelopment.nl/java/internationalization-jsf-utf8-encoded-properties-files/
 * 
 * @author hostettler
 * 
 */
public class UTF8Text extends ResourceBundle {

	/** The name of the bundle resources/labels. */
	protected static final String BUNDLE_NAME = "resources.labels";
	/** Bundle extension. */
	protected static final String BUNDLE_EXTENSION = "properties";
	/** Control class. */
	protected static final Control UTF8_CONTROL = new UTF8Control();

	/**
	 * Default constructor.
	 */
	public UTF8Text() {
		setParent(ResourceBundle.getBundle(BUNDLE_NAME, FacesContext.getCurrentInstance().getViewRoot().getLocale(),
				UTF8_CONTROL));
	}

	@Override
	protected Object handleGetObject(final String key) {
		return parent.getObject(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		return parent.getKeys();
	}

	/**
	 * Constrols the callbacks.
	 * 
	 * @author hostettler
	 * 
	 */
	protected static class UTF8Control extends Control {

		@Override
		public ResourceBundle newBundle(final String baseName, final Locale locale, final String format,
				final ClassLoader loader, final boolean reload) throws IllegalAccessException, InstantiationException,
				IOException {
			// The below code is copied from default Control#newBundle() implementation.
			// Only the PropertyResourceBundle line is changed to read the file as UTF-8.
			String bundleName = toBundleName(baseName, locale);
			String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
			ResourceBundle bundle = null;
			InputStream stream = null;
			if (reload) {
				URL url = loader.getResource(resourceName);
				if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null) {
				try {
					bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
				} finally {
					stream.close();
				}
			}
			return bundle;
		}
	}

}