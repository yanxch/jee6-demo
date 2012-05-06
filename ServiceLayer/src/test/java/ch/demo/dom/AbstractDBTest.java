/**
 * 
 */
package ch.demo.dom;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstracts the entity tests.
 * 
 * @author hostettler
 * 
 */
public abstract class AbstractDBTest {

    /** The default logger for the class. */
    public static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractDBTest.class);

    /** The factory that produces entity manager. */
    private static EntityManagerFactory mEmf;
    /** The entity manager that persists and queries the DB. */
    private static EntityManager mEntityManager;
    /** A transaction. */
    private static EntityTransaction mTrx;
    /** Connection to the database. */
    private static IDatabaseConnection mDBUnitConnection;
    /** Test dataset. */
    private static IDataSet mDataset;

    /** script to clean up and prepare the DB. */
    private static String mDDLFileName = "/sql/createStudentsDB_DERBY.sql";
    /** Target persistence unit. */
    private static String mPersistenceUnit = "JEE6Demo-Test-Persistence";
    
    /**
     * Initializes the manager and the database.
     * 
     * @throws Exception
     *             if anything goes wrong.
     */
    @BeforeClass
    public static void initTestFixture() throws Exception {
        // Get the entity manager for the tests.
        mEmf = Persistence.createEntityManagerFactory(mPersistenceUnit);
        mEntityManager = mEmf.createEntityManager();


        Connection connection = ((EntityManagerImpl) (mEntityManager
                .getDelegate())).getServerSession().getAccessor()
                .getConnection();

        ij.runScript(connection,
                AbstractDBTest.class.getResourceAsStream(mDDLFileName),
                "UTF-8", System.out, "UTF-8");

        // Load the test datasets in the database
        mDBUnitConnection = new DatabaseConnection(connection);
        mDataset = new FlatXmlDataSetBuilder().build(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("students-datasets.xml"));

    }

    /**
     * Cleans up the session.
     * @throws SQLException if anything goes wrong during connection closing.
     */
    @AfterClass
    public static void closeTestFixture() throws SQLException {
        mDBUnitConnection.close();
        mEntityManager.close();
        mEmf.close();
    }

    /**
     * Prepares a new test.
     * 
     * @throws Exception
     *             if anything goes wrong.
     */
    @Before
    public void initTest() throws Exception {
        LOGGER.info("Prepare the database.");
        DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, mDataset);
        LOGGER.info("Start a new transaction for the test");
        mTrx = mEntityManager.getTransaction();
    }

    /**
     * @return the emf
     */
    protected static final EntityManagerFactory getEmf() {
        return mEmf;
    }

    /**
     * @return the em
     */
    protected static final EntityManager getEntityManager() {
        return mEntityManager;
    }

    /**
     * @return the tx
     */
    protected static final EntityTransaction getTrx() {
        return mTrx;
    }

    /**
     * @return the connection
     */
    protected static final IDatabaseConnection getConnection() {
        return mDBUnitConnection;
    }

    /**
     * @return the dataset
     */
    protected static final IDataSet getDataset() {
        return mDataset;
    }
}
