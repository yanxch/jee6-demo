/**
 * 
 */
package ch.demo.helpers.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * @author hostettler
 * 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {

}
