package ch.demo.example.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.demo.dom.AbstractDBTest;

/**
 * 
 */

/**
 * @author hostettler
 * 
 */
public class StudentJDBCTest {
    /** JDBC driver name. */
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    /** JDBC database URL. */
    public static final String DB_URL = "jdbc:derby:memory:StudentsDB;create=true";
    // Database credentials
    /** User name. */
    public static final String USER = "";
    /** Password. */
    public static final String PASS = "";
    /** script to clean up and prepare the DB. */
    private static String mDDLFileName = "/sql/createStudentsDB_DERBY.sql";

    
    /**
     * Prepares the test setting up the DB and initializing some test data.
     * @throws Exception of anything goes wrong.
     */
    @BeforeClass
    public static void initTest() throws Exception {
        Connection conn = null;

        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        
        initDB(conn);
        
    }

    /**
     * Connects to the database and load the DB version. Warning : exception and
     * connection management is bad is this example
     * 
     * @throws Exception
     *             if anything goes wrong
     */
    @Test
    public void testDisplayStudentList() throws Exception {
        //WARNING: this test aims at display bad practice
        //Exception are just thrown and nothing is closed
        Connection conn = null;
        Statement stmt = null;

        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        DatabaseMetaData dbMetaData = conn.getMetaData();
        String productName = dbMetaData.getDatabaseProductName();
        System.out.println("Database: " + productName);
        String productVersion = dbMetaData.getDatabaseProductVersion();
        System.out.println("Version: " + productVersion);

        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT id, first_name, last_name, birth_date FROM students";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            Date birthdate = rs.getDate("birth_date");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            // Display values
            System.out.print("ID: " + id);
            System.out.print(", First Name: " + firstname);
            System.out.print(", Last Name: " + lastname);
            System.out.println(", Birthdate: " + birthdate);
        }

    }

    /**
     * Connects to the database, loads the DB version
     * and display the current student list.
     */
    @Test
    public void testSelect() {

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            DatabaseMetaData dbMetaData = conn.getMetaData();
            String productName = dbMetaData.getDatabaseProductName();
            System.out.println("Database: " + productName);
            String productVersion = dbMetaData.getDatabaseProductVersion();
            System.out.println("Version: " + productVersion);

            // STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, first_name, last_name, birth_date FROM students";
            ResultSet rs = stmt.executeQuery(sql);
            // STEP 5: Extract data from result set
            while (rs.next()) {
                // Retrieve by column name

                int id = rs.getInt("id");
                Date birthdate = rs.getDate("birth_date");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                // Display values
                System.out.print("ID: " + id);
                System.out.print(", First Name: " + firstname);
                System.out.print(", Last Name: " + lastname);
                System.out.println(", Birthdate: " + birthdate);
            }
            // STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * Connects to the database and load the DB version.
     */
    @Test
    public void testLoadOneStudent() {

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            DatabaseMetaData dbMetaData = conn.getMetaData();
            String productName = dbMetaData.getDatabaseProductName();
            System.out.println("Database: " + productName);
            String productVersion = dbMetaData.getDatabaseProductVersion();
            System.out.println("Version: " + productVersion);

            // STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, first_name, last_name, birth_date FROM students WHERE last_name = 'Barrett'";
            ResultSet rs = stmt.executeQuery(sql);
            // STEP 5: Extract data from result set
            while (rs.next()) {
                // Retrieve by column name

                int id = rs.getInt("id");
                Date birthdate = rs.getDate("birth_date");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                // Display values
                System.out.print("ID: " + id);
                System.out.print(", First Name: " + firstname);
                System.out.print(", Last Name: " + lastname);
                System.out.println(", Birthdate: " + birthdate);
            }
            // STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * Connects to the database and load the DB version.
     */
    @Test
    public void testInsertStudent() {

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            DatabaseMetaData dbMetaData = conn.getMetaData();
            String productName = dbMetaData.getDatabaseProductName();
            System.out.println("Database: " + productName);
            String productVersion = dbMetaData.getDatabaseProductVersion();
            System.out.println("Version: " + productVersion);

            conn.setAutoCommit(false);

            // STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "INSERT INTO STUDENTS(LAST_NAME, FIRST_NAME, BIRTH_DATE, PHONE_NUMBER) "
                    + "VALUES ('DOE', 'JOHN', '1978-10-10', '00-0000-0000')";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException sse) {
                sse.printStackTrace();
            }
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    
    /**
     * Connects to the database and load the DB version.
     */
    @Test
    public void testUpdateStudent() {

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            DatabaseMetaData dbMetaData = conn.getMetaData();
            String productName = dbMetaData.getDatabaseProductName();
            System.out.println("Database: " + productName);
            String productVersion = dbMetaData.getDatabaseProductVersion();
            System.out.println("Version: " + productVersion);

            conn.setAutoCommit(false);

            // STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "UPDATE STUDENTS SET PHONE_NUMBER = '00-0000-0001' WHERE PHONE_NUMBER ='00-0000-0000'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException sse) {
                sse.printStackTrace();
            }
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    

    /**
     * Connects to the database and load the DB version.
     */
    @Test
    public void testParametrized() {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            DatabaseMetaData dbMetaData = conn.getMetaData();
            String productName = dbMetaData.getDatabaseProductName();
            System.out.println("Database: " + productName);
            String productVersion = dbMetaData.getDatabaseProductVersion();
            System.out.println("Version: " + productVersion);

            // STEP 4: Execute a query
            System.out.println("Creating statement...");
            
            String sql;
            sql = "SELECT * FROM students WHERE last_name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "Barrett");
            ResultSet rs = stmt.executeQuery();
            
            // STEP 5: Extract data from result set
            while (rs.next()) {
                // Retrieve by column name

                int id = rs.getInt("id");
                Date birthdate = rs.getDate("birth_date");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                // Display values
                System.out.print("ID: " + id);
                System.out.print(", First Name: " + firstname);
                System.out.print(", Last Name: " + lastname);
                System.out.println(", Birthdate: " + birthdate);
            }
            // STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    /**
     * Prepares the database by creating the tables and setting up test data.
     * @param connection to use
     * @throws Exception if anything goes wrong
     */
    private static void initDB(final Connection connection) throws Exception {
        ij.runScript(connection,
                AbstractDBTest.class.getResourceAsStream(mDDLFileName),
                "UTF-8", System.out, "UTF-8");

        // Load the test datasets in the database
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(
                connection);
        IDataSet dataset = new FlatXmlDataSetBuilder().build(Thread
                .currentThread().getContextClassLoader()
                .getResourceAsStream("students-datasets.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataset);
    }
}
