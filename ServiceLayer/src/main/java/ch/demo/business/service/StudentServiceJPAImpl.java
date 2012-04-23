package ch.demo.business.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import ch.demo.business.interceptors.Secure;
import ch.demo.dom.Student;
import ch.demo.helpers.jpa.Transactional;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@JPAImpl
@ApplicationScoped
public class StudentServiceJPAImpl implements StudentService {

    /** The logger for the class. */
    @Inject
    private transient Logger mLogger;

    /** The serial-id. */
    private static final long serialVersionUID = 1386508985359072399L;

    /** The entity manager that manages the persistence. */
    //The first line is for compatibility with JEE servers
    @PersistenceContext(unitName = "JEE6Demo-Persistence")
    @Inject
    private EntityManager mEntityManager;

    /** Some post-construtor initializations. */
    @PostConstruct
    void init() {
        mLogger.info("Use the JPA implementation");
    }

    @Override
    @Transactional
    @Secure(roles = { "user" })
    public List<Student> getAll() {
        CriteriaBuilder qb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<Student> c = qb.createQuery(Student.class);
        TypedQuery<Student> query = mEntityManager.createQuery(c);
        return query.getResultList();
    }

    @Override
    @Transactional
    @Secure(roles = { "user" })
    public int getNbStudent() {
        return getAll().size();
    }

    @Override
    @Transactional
    @Secure(roles = { "admin" })
    public void add(final Student student) {
        mEntityManager.persist(student);
    }

    @Override
    @Transactional
    @Secure(roles = { "user" })
    public int[] getDistribution(final int n) {
        int[] grades = new int[n];

        for (Student s : this.getAll()) {
            grades[(s.getAvgGrade().intValue() - 1) / (TOTAL / n)]++;
        }
        return grades;
    }

    @Override
    @Transactional
    @Secure(roles = { "user" })
    public Student getStudentById(final String id) {
        CriteriaBuilder qb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<Student> c = qb.createQuery(Student.class);
        Root<Student> from = c.from(Student.class);
        Predicate condition = qb.equal(from.get("mId"), id);
        c.where(condition);
        TypedQuery<Student> query = mEntityManager.createQuery(c);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    @Secure(roles = { "user" })
    public Student getStudentByLastName(final String lastname) {
        CriteriaBuilder qb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<Student> c = qb.createQuery(Student.class);
        Root<Student> from = c.from(Student.class);
        Predicate condition = qb.equal(from.get("mLastName"), lastname);
        c.where(condition);
        TypedQuery<Student> query = mEntityManager.createQuery(c);
        return query.getSingleResult();
    }

}
