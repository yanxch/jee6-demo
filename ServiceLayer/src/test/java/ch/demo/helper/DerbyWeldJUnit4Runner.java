/**
 * 
 */
package ch.demo.helper;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import ch.demo.dom.AbstractDBTest;

/**
 * @author hostettler
 * 
 */
public class DerbyWeldJUnit4Runner extends WeldJUnit4Runner {

    /** The factory that produces entity manager. */
    private EntityManagerFactory mEmf;
    /** The entity manager that persists and queries the DB. */
    private EntityManager mEntityManager;
    /** Connection to the database. */
    private IDatabaseConnection mDBUnitConnection;
    /** Test dataset. */
    private IDataSet mDataset;

    /** script to clean up and prepare the DB. */
    private static String mDDLFileName = "/sql/createStudentsDB_DERBY.sql";
    /** Target persistence unit. */
    private static String mPersistenceUnit = "JEE6Demo-Test-Persistence";

    /**
     * Runs the class passed as a parameter within the container.
     * 
     * @param klass
     *            to run
     * @throws InitializationError
     *             if anything goes wrong.
     */
    public DerbyWeldJUnit4Runner(final Class<Object> klass)
            throws InitializationError {
        super(klass);
    }

    @Override
    protected Object createTest() throws Exception {
        final Object test = super.createTest();

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

        return test;
    }

    /**
     * Initializes the Database state.
     * {@inheritDoc}
     */
    @Override
    protected final Statement withBefores(final FrameworkMethod method, final Object target,
            final Statement statement) {
        try {
            DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, mDataset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return super.withBefores(method, target, statement);
    }
}
