/**
 * 
 */
package ch.demo.web;

import java.io.File;

import org.apache.catalina.startup.Tomcat;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * This abstract class prepares a tomcat server to do integration testing.
 * 
 * @author hostettler
 */
public abstract class AbstractEmbeddedTomcatTest {

	/** The tomcat instance. */
	private static Tomcat tomcat;
	/** The base url of the test app. */
	private static String appBaseURL;

	/**
	 * Prepares a new integration test.
	 * 
	 * @param applicationId
	 *            the name of the webapp
	 * @param workingDir
	 *            the directory in which the app is deployed
	 */
	public AbstractEmbeddedTomcatTest(final String applicationId, final String workingDir) {
		System.out.println(workingDir);
		String contextPath = "/" + applicationId;
		File webApp = new File(workingDir, applicationId);
		new ZipExporterImpl(createWebArchive()).exportTo(new File(workingDir + "/test.war"), true);
		tomcat.setBaseDir(workingDir);
		tomcat.addWebapp(tomcat.getHost(), contextPath, webApp.getAbsolutePath());
		appBaseURL = "http://localhost:" + getTomcatPort() + "/" + applicationId;

	}

	/**
	 * Prepares a new integration test. It deploys the webapp into a temp directory.
	 * 
	 * @param applicationId
	 *            the name of the webapp
	 */
	public AbstractEmbeddedTomcatTest(final String applicationId) {
		this(applicationId, System.getProperty("java.io.tmpdir"));
	}

	/**
	 * Starts tomcat.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@BeforeClass
	public static void initTest() throws Exception {
		startServletEngine("/");
	}

	/**
	 * Stops tomcat.
	 * 
	 * @throws Exception
	 *             if anything goes wrong
	 */
	@AfterClass
	public static void cleanUp() throws Exception {
		tomcat.stop();
	}

	/**
	 * @return the port tomcat is running on
	 */
	protected static int getTomcatPort() {
		return tomcat.getConnector().getLocalPort();
	}

	/**
	 * @return the URL the app is running on
	 */
	protected static String getAppBaseURL() {
		return appBaseURL;
	}

	/**
	 * @return a web archive that will be deployed on the embedded tomcat.
	 */
	protected abstract WebArchive createWebArchive();

	/**
	 * Starts an embedded Tomcat on a random port.
	 * @param baseUrl base url of the app
	 * @throws Exception if anything goes wrong
	 */
	private static void startServletEngine(final String baseUrl) throws Exception {
		tomcat = new Tomcat();
		tomcat.setPort(0);
		tomcat.getHost().setAppBase(".");
		tomcat.getHost().setAutoDeploy(true);
		tomcat.getHost().setDeployOnStartup(true);
		tomcat.start();
	}
}
