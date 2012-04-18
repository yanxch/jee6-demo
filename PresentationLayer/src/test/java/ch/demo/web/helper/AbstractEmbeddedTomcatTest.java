/**
 * 
 */
package ch.demo.web.helper;

import java.io.File;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.io.FileUtils;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This abstract class prepares a tomcat server to do integration testing.
 * 
 * @author hostettler
 */
public abstract class AbstractEmbeddedTomcatTest {

	/** The tomcat instance. */
	private Tomcat mTomcat;
	/** The temporary directory in which Tomcat and the app are deployed. */
	private String mWorkingDir = System.getProperty("java.io.tmpdir");
	/** The class logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEmbeddedTomcatTest.class);


	/**
	 * Stops the tomcat server.
	 * 
	 * @throws Throwable
	 *             if anything goes wrong.
	 */
	@Before
	public final void setup() throws Throwable {
		LOGGER.info("Tomcat's base directory : {}", mWorkingDir);
		
		LOGGER.info("Creates a new server...");
		mTomcat = new Tomcat();
		mTomcat.setPort(0);
		mTomcat.setBaseDir(mWorkingDir);
		mTomcat.getHost().setAppBase(mWorkingDir);
		mTomcat.getHost().setAutoDeploy(true);
		mTomcat.getHost().setDeployOnStartup(true);

		LOGGER.info("Prepares and adds the web app");
		String contextPath = "/" + getApplicationId();
		File webApp = new File(mWorkingDir, getApplicationId());
		File oldWebApp = new File(webApp.getAbsolutePath());
		FileUtils.deleteDirectory(oldWebApp);
		new ZipExporterImpl(createWebArchive()).exportTo(new File(mWorkingDir + "/" + getApplicationId() + ".war"),
				true);
		mTomcat.addWebapp(mTomcat.getHost(), contextPath, webApp.getAbsolutePath());


		LOGGER.info("Init users and roles");
		mTomcat.addUser("admin", "admin");
		mTomcat.addUser("user", "user");
		mTomcat.addRole("admin", "admin");
		mTomcat.addRole("admin", "user");
		mTomcat.addRole("user", "user");

		LOGGER.info("Start the server...");
		mTomcat.start();
	}

	/**
	 * Stops the tomcat server.
	 * 
	 * @throws Throwable
	 *             if anything goes wrong.
	 */
	@After
	public final void teardown() throws Throwable {
		LOGGER.info("Stop the server...");

		if (mTomcat.getServer() != null && mTomcat.getServer().getState() != LifecycleState.DESTROYED) {
		if (mTomcat.getServer().getState() != LifecycleState.STOPPED) {
			mTomcat.stop();
		}
		 mTomcat.destroy();
		 }
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
		return "http://localhost:" + getTomcatPort() + "/" + getApplicationId();
	}

	/**
	 * @return a web archive that will be deployed on the embedded tomcat.
	 */
	protected abstract WebArchive createWebArchive();

	/**
	 * @return the name of the application to test.
	 */
	protected abstract String getApplicationId();
}
