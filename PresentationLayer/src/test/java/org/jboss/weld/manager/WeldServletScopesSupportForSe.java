package org.jboss.weld.manager; // required for visibility to BeanManagerImpl#getContexts()

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.jboss.weld.context.AbstractBoundContext;
import org.jboss.weld.context.bound.MutableBoundRequest;

/**
 * This helper class has been taken from http://www.jtips.info/index.php?title=WeldSE/Scopes
 * and simulates request and session scopes outside of an application server.
 *
 */
public class WeldServletScopesSupportForSe implements Extension {
	
	/** {@inheritDoc} */
	public void afterDeployment(@Observes final AfterDeploymentValidation event, 
					final BeanManager beanManager) {
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		activateContext(beanManager, SessionScoped.class, sessionMap);

		Map<String, Object> requestMap = new HashMap<String, Object>();
		activateContext(beanManager, RequestScoped.class, requestMap);

		activateContext(beanManager, ConversationScoped.class, 
				new MutableBoundRequest(requestMap, sessionMap));
	}

	/**
	 * Activates a context for a given manager.
	 * @param beanManager in which the context is activated
	 * @param cls the class that represents the scope
	 * @param storage in which to put the scoped values
	 * @param <S> the type of the storage
	 */
	private <S> void activateContext(final BeanManager beanManager,
				final Class<? extends Annotation> cls, final S storage) {
		BeanManagerImpl beanManagerImpl = (BeanManagerImpl) beanManager;
		@SuppressWarnings("unchecked")
		AbstractBoundContext<S> context = 
			(AbstractBoundContext<S>) beanManagerImpl.getContexts().get(cls).get(0);

		context.associate(storage);
		context.activate();
	}
}