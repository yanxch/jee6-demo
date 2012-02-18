package ch.demo.business.service;

import java.util.Date;

import junit.framework.Assert;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.demo.business.dom.Student;

/**
 * Test the student service mock implementation.
 * 
 * @author hostettler
 */
public class StudentServiceImplTest {

	/** Service retrieved by the Weld container. */
	private StudentService mService;
	/** The weld container. */
	private Weld mBeanContainer;

	/**
	 * Initializes the unit test.
	 */
	@Before
	public void init() {
		//Start a new container
		mBeanContainer = new Weld();
		WeldContainer container = mBeanContainer.initialize();
		//Retrieve the implementation for the class.
		mService = container.instance().select(StudentService.class).get();
		Assert.assertNotNull(mService);
	}

	/**
	 * Shutdowns and cleanups the test.
	 */
	@After
	public void shutdown() {
		this.mBeanContainer.shutdown();
	}

	/**
	 * Test the addition a new student to the repository.
	 */
	@Test
	public void testAdd() {

		int n = mService.getAll().size();

		Student s = new Student("Hostettler", "Steve", new Date());
		mService.add(s);

		Assert.assertEquals(n + 1, mService.getAll().size());
		Assert.assertEquals("Hostettler", mService.getStudentById(s.getKey()).getLastName());
	}

	/**
	 * Test the behavior when a non-existing student is requested.
	 */
	@Test
	public void testNonExistingStudent() {
		Student s = new Student("MyLastName", "MyFirstName", new Date());
		Assert.assertNull(mService.getStudentById(s.getKey()));
	}
}
