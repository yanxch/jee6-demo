package ch.demo.business.service;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import ch.demo.business.dom.Discipline;
import ch.demo.business.dom.Grade;
import ch.demo.business.dom.PhoneNumber;
import ch.demo.business.dom.Student;

/**
 * Test the student service mock implementation.
 * 
 * @author hostettler
 */
public abstract class AbstractStudentServiceImplTest {

    /**
     * Test the addition a new student to the repository.
     */
    @Test
    public void testAdd() {
        int n = getService().getAll().size();

        Student s = new Student("Hostettler", "Steve", new Date());
        s.setPhoneNumber(new PhoneNumber(0, 0, 0));
        for (Discipline d : Discipline.values()) {
            Grade g = new Grade(d);
            g.setGrade(1);
            s.getGrades().add(g);
        }

        getService().add(s);

        Assert.assertEquals(n + 1, getService().getAll().size());
        Assert.assertEquals("Hostettler",
                getService().getStudentById(s.getKey()).getLastName());
    }

    /**
     * Tests the distribution of the grades among students.
     */
    @Test
    public void testDistribution() {
        int[] distribution = getService().getDistribution(5);
        Assert.assertEquals(this.getService().getNbStudent(), distribution[0]);
        Assert.assertEquals(0, distribution[1]);
        Assert.assertEquals(0, distribution[2]);
        Assert.assertEquals(0, distribution[3]);
        Assert.assertEquals(0, distribution[4]);
    }

    /**
     * Test the behavior when a non-existing student is requested.
     */
    @Test
    public void testNonExistingStudent() {
        Student s = new Student("MyLastName", "MyFirstName", new Date());
        Assert.assertNull(getService().getStudentById(s.getKey()));
    }

    /**
     * @return the actual implementation of the service.
     */
    protected abstract StudentService getService();
}
