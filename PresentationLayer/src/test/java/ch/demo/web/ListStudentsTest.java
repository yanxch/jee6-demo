/**
 * 
 */
package ch.demo.web;

import java.io.File;

import junit.framework.Assert;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

import ch.demo.web.helper.AbstractEmbeddedTomcatTest;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Tests the page in which the student list is displayed.
 * @author hostettler
 */
public class ListStudentsTest extends AbstractEmbeddedTomcatTest {

	/**
	 * Build a new web app called test.
	 * @throws Exception if anything goes wrong
	 */
	public ListStudentsTest() throws Exception {
		super("test");
	}

	/**
	 * Test a simple workflow. It verifies that the student list doest return
	 * something useful.
	 * @throws Exception if anything goes wrong
	 */
	@Test
	public void testListStudents() throws Exception {
		final WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		final HtmlPage page = webClient.getPage(getAppBaseURL());
		final String pageAsXml = page.asXml();
		Assert.assertTrue(pageAsXml.contains("Steve Hostettler"));
		webClient.closeAllWindows();
	}

	/** Where the web sources are. */
	private static final String WEBAPP_SRC = "src/main/webapp";

	/**
	 * Build a war to test a simple workflow.
	 * @return a war
	 */
	protected WebArchive createWebArchive() {
		final WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
		archive.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/faces-config.xml"))
				.addPackage(java.lang.Package.getPackage("ch.demo.web"))
				.addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "index.jsp"))
				.addAsWebResource(new File(WEBAPP_SRC, "xhtml/listStudents.xhtml"), "xhtml/listStudents.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC, "xhtml/index.xhtml"), "xhtml/index.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC, "xhtml/registerStudent.xhtml"), "xhtml/registerStudent.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC, "xhtml/templates/layout.xhtml"), "xhtml/templates/layout.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC, "xhtml/templates/searchbox.xhtml"),
						"xhtml/templates/searchbox.xhtml")
				.addAsWebResource(new File(WEBAPP_SRC, "xhtml/templates/dialog.xhtml"), "xhtml/templates/dialog.xhtml");
		return archive;
	}
}
