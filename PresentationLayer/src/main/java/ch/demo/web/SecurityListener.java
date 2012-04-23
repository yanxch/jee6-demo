/**
 * 
 */
package ch.demo.web;

import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ch.demo.helpers.MyPrincipal;
import ch.demo.helpers.SecurityContext;

/**
 * Listens to request events to set the current logged user in thread local.
 * This is used to enable security on the service layer.
 *  
 * *******  WARNING ******* 
 * This class only exists for demonstration purposes (JEE6 interceptors). DO NOT USE IT in your project
 * as it is not fully JAAS compatible, not portable, and EXTREMELY slow.
 * Use a library (Spring, Seam, Shiro) instead, to do that is real production projects.
 * 
 * @author hostettler
 * 
 */
public class SecurityListener implements ServletRequestListener {

	/** The list of roles of the current web app. This is not mean to be a real singleton. */
	private static List<String> roles = null;

	@Override
	public void requestInitialized(final ServletRequestEvent sre) {
		SecurityContext.setPrincipal(getMyPrincipal((HttpServletRequest) sre.getServletRequest()));
	}

	@Override
	public void requestDestroyed(final ServletRequestEvent sre) {
		SecurityContext.removePrincipal();
	}

	/**
	 * Builds a MyPrincipal from the HttpServletRequest. This awkward code is necessary because Tomcat does not align
	 * GenericPrincipal and AbstractUser in a single interface that has the notion of roles.
	 * 
	 * @param request to process
	 * @return a MyPrincipal that represents the current logged user and its roles
	 */
	public static MyPrincipal getMyPrincipal(final HttpServletRequest request) {
		if (roles == null) {
			//The role list is reconstructed as there is not standard (JAAS) way to extract it from the context.
			roles = getSecurityRoles(request.getServletContext());
		}

		Principal principal = (Principal) request.getUserPrincipal();
		List<String> currentRoles = new ArrayList<String>();
		for (String role : roles) {
			if (request.isUserInRole(role)) {
				currentRoles.add(role);
			}
		}
		return new MyPrincipal(principal, currentRoles);
	}

	/**
	 * Parses the web.xml to extract the roles. This parses the web.xml using DOM. This is not very
	 * efficient and is due to the lack of standard way to extract the roles from the context in JAAS.
	 * @param ctx to look at.
	 * @return a list of string that represent roles.
	 */
	public static synchronized List<String> getSecurityRoles(final ServletContext ctx) {
		List<String> r = new ArrayList<String>();
		InputStream is = ctx.getResourceAsStream("/WEB-INF/web.xml");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true);
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList elements = doc.getElementsByTagName("role-name");
			for (int i = 0; i < elements.getLength(); i++) {
				r.add(elements.item(i).getTextContent().trim());
			}
		} catch (Exception e) {
			new IllegalAccessException(e.getMessage());
		}
		return r;
	}
}
