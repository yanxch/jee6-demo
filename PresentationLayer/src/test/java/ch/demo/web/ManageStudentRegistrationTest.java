/**
 * 
 */
package ch.demo.web;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.chart.PieChartModel;

import ch.demo.helper.WeldJUnit4Runner;

/**
 * Tests the ManageStudentRegistration bean.
 * 
 * @author hostettler
 * 
 */
@RunWith(WeldJUnit4Runner.class)
public class ManageStudentRegistrationTest {

	/** Service retrieved by the Weld container. */
	@Inject
	private ManageStudentRegistration mManageStudentRegistration;

	/** A conversation for the test. */
	@Inject
	private Conversation mConversation;

	/**
	 * Tests the pie chart production.
	 */
	@Test
	public void testPieChartCreation() {
		PieChartModel model = this.mManageStudentRegistration.getPieModel();
		Assert.assertNotNull(model);
		Assert.assertEquals(4, model.getData().size());
	}

	/**
	 * Tests a new conversation.
	 */
	@Test
	public void toRegistrationTest() {
		this.mConversation.begin("ConversationId");
		Assert.assertEquals("register", mManageStudentRegistration.toRegistration());
	}

}
