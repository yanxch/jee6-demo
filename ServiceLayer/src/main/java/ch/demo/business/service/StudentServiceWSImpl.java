package ch.demo.business.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import ch.demo.business.service.mock.StudentServiceMockImpl;
import ch.demo.dom.Student;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@WebServiceImpl
@ApplicationScoped
public class StudentServiceWSImpl implements StudentService {

    /** The logger for the class. */
    @Inject
    private transient Logger mLogger;

    /** The serial-id. */
    private static final long serialVersionUID = 1386507985359072399L;

    /** 
     * TODO : remove delegation.
     */
    private StudentService mDeleguate  = new StudentServiceMockImpl();

    /** Some post-construtor initializations.   */
    @PostConstruct
    void init() {
        mLogger.info("Use the Web Service implementation");
        mLogger.warn("This pseudo-implementation deleguates to the mock");
    }
    
    @Override
    public List<Student> getAll() {
        // TODO Auto-generated method stub
        return mDeleguate.getAll();
    }

    @Override
    public int getNbStudent() {
        // TODO Auto-generated method stub
        return mDeleguate.getNbStudent();
    }

    @Override
    public void add(final Student student) {
        // TODO Auto-generated method stub
        mDeleguate.add(student);
    }

    @Override
    public int[] getDistribution(final int n) {
        // TODO Auto-generated method stub
        return mDeleguate.getDistribution(n);
    }

    @Override
    public Student getStudentById(final String id) {
        // TODO Auto-generated method stub
        return mDeleguate.getStudentById(id);
    }

    @Override
    public Student getStudentByLastName(final String lastname) {
        // TODO Auto-generated method stub
        return mDeleguate.getStudentByLastName(lastname);
    }
}
