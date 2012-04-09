/**
 * 
 */
package ch.demo.web.helper;

import java.io.File;

import org.apache.catalina.startup.Tomcat;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This abstract class prepares a tomcat server to do integration testing.
 * 
 * @author hostettler
 */
public abstract class AbstractEmbeddedTomcatTest {

	/** The tomcat instance. */
	private static Tomcat mTomcat = new Tomcat();
	/** the tempprary directory in which Tomcat and the app are deployed. */
	private static String mWorkingDir = System.getProperty("java.io.tmpdir");
	/** The class logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEmbeddedTomcatTest.class);

	/** The base url of the test app. */
	private String mAppBaseURL;

	/**
	 * Stops the tomcat server.
	 * 
	 * @throws Throwable
	 *             if anything goes wrong.
	 */
	@BeforeClass
	public static final void setup() throws Throwable {
		LOGGER.info("Tomcat's base directory : {}", mWorkingDir);
		mTomcat.setPort(0);
		mTomcat.setBaseDir(mWorkingDir);
		mTomcat.getHost().setAppBase(mWorkingDir);
		mTomcat.getHost().setAutoDeploy(true);
		mTomcat.getHost().setDeployOnStartup(true);
	}

	/**
	 * Stops the tomcat server.
	 * 
	 * @throws Throwable
	 *             if anything goes wrong.
	 */
	@AfterClass
	public static final void teardown() throws Throwable {
		LOGGER.info("Stop the server...");
		mTomcat.stop();
	};

	/**
	 * Prepares a new integration test.
	 * 
	 * @param applicationId
	 *            the name of the webapp
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public AbstractEmbeddedTomcatTest(final String applicationId) throws Exception {
		LOGGER.info("Start the server...");
		String contextPath = "/" + applicationId;
		File webApp = new File(mWorkingDir, applicationId);
		new ZipExporterImpl(createWebArchive()).exportTo(new File(mWorkingDir + "/test.war"), true);
		mTomcat.start();
		mTomcat.addWebapp(mTomcat.getHost(), contextPath, webApp.getAbsolutePath());
		mAppBaseURL = "http://localhost:" + getTomcatPort() + "/" + applicationId;
	}

	/**
	 * @return the port tomcat is running on
	 */
	protected int getTomcatPort() {
		return mTomcat.getConnector().getLocalPort();
	}

	/**
	 * @return the URL the app is running on
	 */
	protected String getAppBaseURL() {
		return mAppBaseURL;
	}

	/**
	 * @return a web archive that will be deployed on the embedded tomcat.
	 */
	protected abstract WebArchive createWebArchive();

}