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
	/** The temporary directory in which Tomcat and the app are deployed. */
	private static String mWorkingDir = System.getProperty("java.io.tmpdir");
	/** The class logger. */
	private  static final Logger LOGGER = LoggerFactory.getLogger(AbstractEmbeddedTomcatTest.class);

	/** The base url of the test app. */
	private String mAppBaseURL;
	/** The context path of the application under test. */
	private String mContextPath;
	/** File that references the WAR. */
	private File mWebApp;

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
	 * Initializes a test case.
	 * @throws Throwable if anything wrong happens
	 */
	@Before
	public final void init() throws Throwable {
		mTomcat.start();
		mTomcat.addUser("admin", "admin");
		mTomcat.addUser("user", "user");
		mTomcat.addRole("admin", "admin");
		mTomcat.addRole("admin", "user");
		mTomcat.addRole("user", "user");
		mTomcat.addWebapp(mTomcat.getHost(), this.mContextPath, this.mWebApp.getAbsolutePath());
		mAppBaseURL = "http://localhost:" + getTomcatPort() + this.mContextPath;
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
		
		if (mTomcat.getServer() != null
                && mTomcat.getServer().getState() != LifecycleState.DESTROYED) {
            if (mTomcat.getServer().getState() != LifecycleState.STOPPED) {
            	mTomcat.stop();
            }
            mTomcat.destroy();
        }
	}

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
		this.mContextPath = "/" + applicationId;
		this.mWebApp = new File(mWorkingDir, applicationId);
		File oldWebApp = new File(mWebApp.getAbsolutePath());
		FileUtils.deleteDirectory(oldWebApp);
		new ZipExporterImpl(createWebArchive()).exportTo(new File(mWorkingDir + "/test.war"), true);
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
