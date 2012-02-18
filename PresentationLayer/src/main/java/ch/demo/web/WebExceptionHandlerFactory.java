/**
 * 
 */
package ch.demo.web;

import java.util.Iterator;

import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 * Provides support for handling exception that are related to timeout, inexisting conversations (for instance, because
 * the conversation-id has been saved in a shortcut).
 * 
 * @author hostettler
 * 
 */
public class WebExceptionHandlerFactory extends ExceptionHandlerFactory {

	/** Base exception factory. */
	private ExceptionHandlerFactory mBase;

	
	/**
	 * Build a new factory with an existing base factory.
	 * @param base factory
	 */
	public WebExceptionHandlerFactory(final ExceptionHandlerFactory base) {
		this.mBase = base;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new WebExceptionHandlerWrapper(mBase.getExceptionHandler());
	}

}

/**
 * Handles the timeout-related exceptions.
 * @author hostettler
 *
 */
class WebExceptionHandlerWrapper extends ExceptionHandlerWrapper {

	/** The wrapper to use. */
	private ExceptionHandler mWrapped;

	/**
	 * Creates a new wrapper around the existing handler.
	 * @param wrapped exception handle
	 */
	public WebExceptionHandlerWrapper(final ExceptionHandler wrapped) {
		this.mWrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.mWrapped;
	}

	@Override
	public void handle() {
		Iterable<ExceptionQueuedEvent> events = this.mWrapped.getUnhandledExceptionQueuedEvents();
		for (Iterator<ExceptionQueuedEvent> it = events.iterator(); it.hasNext();) {
			ExceptionQueuedEvent event = it.next();
			ExceptionQueuedEventContext eqec = event.getContext();

			if (eqec.getException() instanceof ViewExpiredException
					|| eqec.getException() instanceof NonexistentConversationException) {
				FacesContext context = eqec.getContext();
				NavigationHandler navHandler = context.getApplication().getNavigationHandler();

				try {
					navHandler.handleNavigation(context, null, "home?faces-redirect=true&expired=true");
				} finally {
					it.remove();
				}
			}
		}

		this.mWrapped.handle();
	}
}