/**
 * 
 */
package ch.demo.helpers;

import java.io.Serializable;


/**
 * A security context that stores a principal in thread local.
 * 
 * @author hostettler
 * 
 * *******  WARNING ******* 
 * This class only exists for demonstration purposes (JEE6 interceptors). DO NOT USE IT in your project
 * as it is not fully JAAS compatible, not portable, and EXTREMELY slow.
 * Use a library (Spring, Seam, Shiro) instead, to do that is real production projects.
 * Furthermore, using thread local can (if wrongly used) lead to severe memory leaks.
 */
public final class SecurityContext implements Serializable {

    /** the serial id. */
    private static final long serialVersionUID = -8647411577991386261L;
    /** Thread local store for the principal. */
    private static InheritableThreadLocal<MyPrincipal> principalHolder = 
                new InheritableThreadLocal<MyPrincipal>();

    /** Private constructor for utility class. */
    private SecurityContext() { }

    /**
     * @return the principal of the current thread.
     */
    public static MyPrincipal getPrincipal() {
        if (principalHolder.get() == null) {
            principalHolder.set(new MyPrincipal(null, null));
        }
        return (MyPrincipal) principalHolder.get();
    }

    /**
     * Sets the principal of the current thread.
     * @param principal to set
     */
    public static void setPrincipal(final MyPrincipal principal) {
        principalHolder.set(principal);
    }

    /**
     * Removes the principal from the current thread local store.
     */
    public static void removePrincipal() {
        principalHolder.remove();
    }

}
