/**
 * 
 */
package ch.demo.business.interceptors;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import ch.demo.helpers.MyPrincipal;
import ch.demo.helpers.SecurityContext;

/**
 * @author hostettler
 * 
 */
@Secure(roles = { })
@Interceptor
public class SecurityInterceptor implements Serializable {

    /** Serial-id. */
    private static final long serialVersionUID = 7577146045595590500L;
    /** The default logger for the class. */
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * Secure a method call.
     * 
     * @param context
     *            of the call
     * @return the result of the called method
     * @throws Exception
     *             if anything goes wrong
     */
    @AroundInvoke
    public Object invoke(final InvocationContext context) throws Exception {
        LOGGER.info("Check the roles of the current thread");
        String[] roles = getRoles(context.getMethod());
        LOGGER.info("Check the following roles " + roles);

        MyPrincipal principal = SecurityContext.getPrincipal();
        LOGGER.info("The name of the current principal is "
                + principal.getName());

        if (!principal.isUserInRoles(roles)) {
            throw new IllegalAccessException("Current user not autorised!");
        }

        Object value = context.proceed();
        return value;
    }

    /**
     * Get the list of roles from the meta-annotation.
     * 
     * @param method
     *            to analyze
     * @return the list of roles.
     */
    private String[] getRoles(final Method method) {
        // first look at method-level annotations, since they take priority

        if (method.isAnnotationPresent(Secure.class)) {
            return method.getAnnotation(Secure.class).roles();
        }

        // now try class-level annotations
        if (method.getDeclaringClass().isAnnotationPresent(Secure.class)) {
            return method.getDeclaringClass().getAnnotation(Secure.class)
                    .roles();
        }

        return null;
    }

}
