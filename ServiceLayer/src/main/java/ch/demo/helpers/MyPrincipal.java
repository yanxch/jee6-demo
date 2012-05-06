/**
 * 
 */
package ch.demo.helpers;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

/**
 * This class represents a principal and its roles.
 * *******  WARNING ******* 
 * This class only exists for demonstration purposes (JEE6 interceptors). DO NOT USE IT in your project
 * as it is not fully JAAS compatible, not portable, and EXTREMELY slow.
 * Use a library (Spring, Seam, Shiro) instead, to do that is real production projects.
 * @author hostettler
 */
public class MyPrincipal implements Principal, Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = 8896267557054716392L;
    /** The principal. */
    private Principal principal;
    /** The principal's roles. */
    private List<String> roles;

    /**
     * Builds a new principal with the given roles.
     * @param pPrincipal to build
     * @param pRoles and its roles
     */
    public MyPrincipal(final Principal pPrincipal, final List<String> pRoles) {
        this.principal = pPrincipal;
        this.roles = pRoles;
    }

    @Override
    public String getName() {
        return this.principal.getName();
    }

    /**
     * @param pRole to check
     * @return whether the current principal is autorised on the given role.
     */
    public boolean isUserInRole(final String pRole) {
        return roles.contains(pRole);
    }
    
    /**
     * @param pRoles is the list of roles to check
     * @return whether the current principal is authorised on the given role.
     */
    public boolean isUserInRoles(final String[] pRoles) {
        for (String role : pRoles) {
            if (isUserInRole(role)) {
                return true;
            }
        }
        return false;
    }
}
