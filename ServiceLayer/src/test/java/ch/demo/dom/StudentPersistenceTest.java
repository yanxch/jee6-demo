package ch.demo.dom;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import junit.framework.Assert;

import org.junit.Test;


/**
 * Test the mapping of the Student entity.
 * @author hostettler
 * 
 */

public class StudentPersistenceTest extends AbstractEntityTest {


    /**
     * Creates a new student and then check the number of actual
     * students in the database. As we initialize the database with
     * 5 students, we end up with 6 students in the DB.
     * @throws Exception if anything goes wrong.
     */
    @Test
    public void createStudent() throws Exception {
        Student student = new Student("Lion Hearth", "Richard", new Date());
        student.setPhoneNumber(new PhoneNumber(0, 0, 0));
        for (Discipline d : Discipline.values()) {
            Grade g = new Grade(d, 10);
            student.getGrades().add(g);
        }
        
        getTrx().begin();
        getEntityManager().persist(student);
        getTrx().commit();
        
        CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Student> c = qb.createQuery(Student.class);
        Query query = getEntityManager().createQuery(c); 
        Assert.assertEquals(6, query.getResultList().size());

    }
}
