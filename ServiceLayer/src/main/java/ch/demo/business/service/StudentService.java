package ch.demo.business.service;

import java.util.List;

import ch.demo.business.dom.Student;

/**
 * Defines the contract of the student service.
 * 
 * @author hostettler
 * 
 */
public interface StudentService {

    /** The domain of the grades. */
    int TOTAL = 100;

    /**
     * @return all the registered students
     */
    List<Student> getAll();

    /**
     * @return the current number of students
     */
    int getNbStudent();

    /**
     * Adds a student to the list.
     * 
     * @param student
     *            to add
     */
    void add(Student student);

    /**
     * @return an array that contains the distribution of the grades. It
     *         partitions the grades in "n" parts.
     * @param n
     *            : number of parts
     */
    int[] getDistribution(final int n);

    /**
     * @param id
     *            of the student
     * @return the student with the given id.
     */
    Student getStudentById(String id);

}