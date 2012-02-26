package ch.demo.business.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

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

    /** Some post-construtor initializations.   */
    @PostConstruct
    void init() {
        mLogger.info("Use the Web Service implementation");
    }
    
    @Override
    public List<Student> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getNbStudent() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void add(final Student student) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int[] getDistribution(final int n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Student getStudentById(final String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
