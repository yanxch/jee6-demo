/**
 * 
 */
package ch.demo.business.interceptors;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author hostettler
 * 
 */
@Benchmarkable
@Interceptor
public class PerformanceInterceptor {

    /** The default logger for the class. */
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * log the actual performance of a method.
     * @param context of the call
     * @return the result of the called method
     * @throws Exception if anything goes wrong
     */
    @AroundInvoke
    public Object logPerformance(final InvocationContext context) throws Exception {
        long start = System.currentTimeMillis();

        Object value = context.proceed();

        StringBuilder str = new StringBuilder("******  ");
        str.append(context.getMethod().getName());
        str.append(":");
        str.append(System.currentTimeMillis() - start);
        str.append(" ms  ******");

        LOGGER.info(str.toString());

        return value;
    }
}
