package ch.demo.dom;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test the mapping of the Student entity.
 * 
 * @author hostettler
 * 
 */

public class StudentPersistenceTest extends AbstractDBTest {

    /**
     * Creates a new student and then check the number of actual students in the
     * database. As we initialize the database with 5 students, we end up with 6
     * students in the DB.
     * 
     */
    @Test
    public void createStudent() {
        Student student = new Student("Lion Hearth", "Richard", new Date());
        student.setPhoneNumber(new PhoneNumber(0, 0, 0));
        for (Discipline d : Discipline.values()) {
            Grade g = new Grade(d, 10);
            student.getGrades().add(g);
        }
        student.setPicture("test".getBytes());

        CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Student> c = qb.createQuery(Student.class);
        Query query = getEntityManager().createQuery(c);
        LOGGER.info("Number of students in the DB:"
                + query.getResultList().size());

        getTrx().begin();
        getEntityManager().persist(student);
        getTrx().commit();

        query = getEntityManager().createQuery(c);

        LOGGER.info("Number of students in the DB:"
                + query.getResultList().size());
        Assert.assertEquals(6, query.getResultList().size());

    }

    /**
     * Test the picture.
     * 
     */
    @Test
    public void fetchPicture() {
        LOGGER.info("Fetch the pictures");
        CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Student> c = qb.createQuery(Student.class);
        TypedQuery<Student> query = getEntityManager().createQuery(c);
        LOGGER.info("Number of students in the DB:"
                + query.getResultList().size());

        for (Student s : query.getResultList()) {
            System.out.println(s.getLastName() + " " + s.getPicture());
            for (Grade g : s.getGrades()) {
                LOGGER.info("***** Grade : " + g.getDiscipline());
            }

            for (Map.Entry<Discipline, Integer> g : s.getAlternativeGrades()
                    .entrySet()) {
                LOGGER.info("***** Grade : " + g.getKey() + " : "
                        + g.getValue());
            }
        }

        TypedQuery<Student> query2 = getEntityManager().createQuery(
                "SELECT s FROM Student s", Student.class);
        LOGGER.info("Number of students in the DB:"
                + query2.getResultList().size());

        TypedQuery<Student> queryStudentsByFirstName = getEntityManager().createNamedQuery(
                "findAllStudentsByFirstName", Student.class);
        queryStudentsByFirstName.setParameter("firstname", "Steve");
        Collection<Student> students = queryStudentsByFirstName.getResultList();
        LOGGER.info("Number of students in the DB:" + students.size());
        
        
        Query query3 = getEntityManager().createNativeQuery("select FIRST_NAME, LAST_NAME from STUDENTS");
        @SuppressWarnings("unchecked")
        List<String[]> list = query3.getResultList();
        LOGGER.info("Number of students:" + list.size());
    }

    /**
     * Tests that adding and removing the same object results in the same
     * database state.
     * 
     * @throws Exception
     */
    @Test
    public void removeStudent() {
        Student student = new Student("Lion Hearth", "Richard", new Date());
        student.setPhoneNumber(new PhoneNumber(0, 0, 0));
        for (Discipline d : Discipline.values()) {
            Grade g = new Grade(d, 10);
            student.getGrades().add(g);
        }
        student.setPicture(new byte[] { 1, 2, 3 });

        getTrx().begin();
        getEntityManager().persist(student);
        getTrx().commit();

        CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Student> c = qb.createQuery(Student.class);
        Query query = getEntityManager().createQuery(c);

        LOGGER.info("Number of students in the DB:"
                + query.getResultList().size());

        getTrx().begin();
        getEntityManager().remove(student);
        getTrx().commit();
        LOGGER.info("Number of students in the DB:"
                + query.getResultList().size());
        Assert.assertEquals(5, query.getResultList().size());
    }
}
