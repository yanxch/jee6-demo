package ch.demo.example.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import ch.demo.dom.PhoneNumber;
import ch.demo.dom.Student;

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
    public static final String DB_URL = "jdbc:mysql://localhost:3306/Students_DB";
    // Database credentials
    /** User name. */
    public static final String USER = "root";
    /** Password. */
    public static final String PASS = "";

    /**
     * Connects to the database. Warning : exception and connection management
     * is bad is this example
     * 
     * @throws Exception
     *             if anything goes wrong
     */
    @Test
    public void testConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Assert.assertNotNull(conn);
    }

    /**
     * Connects to the database and load the DB version.
     */
    @Test
    public void testUpdateStudent() {

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
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
            String sql = "select LAST_NAME, FIRST_NAME, PHONE_NUMBER, BIRTH_DATE from STUDENTS";
            System.out.println(sql);
            ResultSet r = stmt.executeQuery(sql);

            List<Student> studentList = new ArrayList<Student>();
            while (r.next()) {
                Student s = new Student();
                s.setLastName(r.getString(1));
                s.setFirstName(r.getString(2));

                String phoneString = r.getString(3);
                System.out.println(phoneString);
 
                PhoneNumber phone = PhoneNumber.getAsObject(phoneString);

                s.setPhoneNumber(phone);
                s.setBirthDate(r.getDate(4));

                studentList.add(s);
            }

            if (r != null) {
                r.close();
            }

            studentList.get(2).setFirstName("Paul");
            
            StringBuilder sqlString = new StringBuilder("UPDATE Students SET ");
            sqlString.append("LAST_NAME = ?    , ");
            sqlString.append("FIRST_NAME = ?   , ");
            sqlString.append("BIRTH_DATE = ?   ,");
            sqlString.append("PHONE_NUMBER = ?  ");
            sqlString.append("WHERE FIRST_NAME = ?");
            
            System.out.println(sqlString.toString());
            
            PreparedStatement stmt2 = conn.prepareStatement(sqlString.toString());
            stmt2.setString(1, studentList.get(2).getLastName());
            stmt2.setString(2, studentList.get(2).getFirstName());
            stmt2.setDate(3, new java.sql.Date(studentList.get(2).getBirthDate().getTime()));
            stmt2.setString(4, PhoneNumber.getAsString(studentList.get(2).getPhoneNumber()));
            stmt2.setString(5, studentList.get(2).getKey());
                
            stmt2.execute();
            
            
            
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

}
