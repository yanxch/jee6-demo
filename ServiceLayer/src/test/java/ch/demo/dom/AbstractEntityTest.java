/**
 * 
 */
package ch.demo.dom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Abstracts the entity tests.
 * @author hostettler
 * 
 */
public abstract class AbstractEntityTest {
    
    /** The factory that produces entity manager. */
    private static EntityManagerFactory mEmf;
    /** The entity manager that persists and queries the DB. */
    private static EntityManager mEntityManager;
    /** A transaction. */
    private static EntityTransaction mTrx;
    /** Connection to the database. */
    private static IDatabaseConnection mConnection;
    /** Test dataset. */
    private static IDataSet mDataset;

    /**
     * Initializes the manager and the database.
     * @throws Exception if anything goes wrong.
     */
    @BeforeClass
    public static void initEntityManager() throws Exception {
        // Get the entity manager for the tests.
        mEmf = Persistence
                .createEntityManagerFactory("JEE6Demo-Test-Persistence");
        mEntityManager = mEmf.createEntityManager();

        // Load the test datasets in the database
        mConnection = new DatabaseConnection(
                ((EntityManagerImpl) (mEntityManager.getDelegate())).getServerSession()
                        .getAccessor().getConnection());

        mDataset = new FlatXmlDataSetBuilder().build(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("students-datasets.xml"));
    }

    /**
     * Cleans up the session.
     */
    @AfterClass
    public static void closeEntityManager() {
        mEntityManager.close();
        mEmf.close();
    }

    /**
     * Prepares a new test.
     * @throws Exception if anything goes wrong.
     */
    @Before
    public void initTransaction() throws Exception {
        mTrx = mEntityManager.getTransaction();
        DatabaseOperation.CLEAN_INSERT.execute(mConnection, mDataset);
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
        return mConnection;
    }

    /**
     * @return the dataset
     */
    protected static final IDataSet getDataset() {
        return mDataset;
    }
}
