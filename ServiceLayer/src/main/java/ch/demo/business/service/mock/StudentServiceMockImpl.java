package ch.demo.business.service.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.persistence.NoResultException;

import ch.demo.business.interceptors.Benchmarkable;
import ch.demo.business.interceptors.Secure;
import ch.demo.business.service.StudentService;
import ch.demo.dom.Student;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@Alternative
@ApplicationScoped
public class StudentServiceMockImpl implements StudentService, Serializable {

    /** The default logger for the class. */
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /** The serial-id. */
    private static final long serialVersionUID = 1386508985359072399L;

    /**
     * Internal list for mocking a real database.
     */
    private List<Student> mStudentList;

    /**
     * Empty constructor.
     */
    public StudentServiceMockImpl() {
        LOGGER.info("This is the mock implementation");
        this.mStudentList = new ArrayList<Student>();
        this.mStudentList.add(new Student("Hostettler", "Steve", new Date()));
        LOGGER.info("Creation of a new StudentServiceImpl");
    }

    /** {@inheritDoc} */
    @Override
    @Secure(roles = { "user" })
    public int getNbStudent() {
        return this.mStudentList.size();
    }

    /** {@inheritDoc} */
    @Override
    @Secure(roles = { "user" })
    public List<Student> getAll() {
        return this.mStudentList;
    }

    /** {@inheritDoc} */
    @Override
    @Benchmarkable
    @Secure(roles = { "admin" })
    public void add(final Student student) {
        this.mStudentList.add(student);
    }

    /** {@inheritDoc} */
    @Override
    @Secure(roles = { "user" })
    public int[] getDistribution(final int n) {
        int[] grades = new int[n];

        for (Student s : this.getAll()) {
            grades[(s.getAvgGrade().intValue() - 1) / (TOTAL / n)]++;
        }
        return grades;
    }

    /** {@inheritDoc} */
    @Override
    @Secure(roles = { "user" })
    public Student getStudentById(final String id) {
        for (Student s : this.getAll()) {
            if (s.getKey().equals(id)) {
                return s;
            }
        }
        throw new NoResultException();
    }

    @Override
    @Secure(roles = { "user" })
    public Student getStudentByLastName(final String lastname) {
        for (Student s : this.getAll()) {
            if (s.getLastName().equals(lastname)) {
                return s;
            }
        }
        return null;
    }
}
