package ch.demo.business.service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import ch.demo.business.dom.Student;
import ch.demo.business.interceptors.Benchmarkable;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@ApplicationScoped
public class StudentServiceImpl implements StudentService, Serializable {
	
	/** The serial-id. */
	private static final long serialVersionUID = -2768600515985507754L;

	/** The default logger for the class. */
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	/**
	 * Internal list for mocking a real database.
	 */
	private List<Student> mStudentList;

	/**
	 * Empty constructor.
	 */
	public StudentServiceImpl() {
		LOGGER.info("This is the real implementation");
		LOGGER.info("Creation of a new StudentServiceImpl");
	}

	/** {@inheritDoc} */
	@Override
	public int getNbStudent() {
		return this.mStudentList.size();
	}

	/** {@inheritDoc} */
	@Override
	public List<Student> getAll() {
		return this.mStudentList;
	}

	/** {@inheritDoc} */
	@Override
	@Benchmarkable
	public void add(final Student student) {
		this.mStudentList.add(student);
	}

	/** {@inheritDoc} */
	@Override
	public int[] getDistribution(final int n) {
		int[] grades = new int[4];

		for (Student s : this.getAll()) {
			grades[(s.getAvgGrade().intValue() - 1) / (TOTAL / n)]++;
		}
		return grades;
	}

	/** {@inheritDoc} */
	@Override
	public Student getStudentById(final String id) {
		for (Student s : this.getAll()) {
			if (s.getKey().equals(id)) {
				return s;
			}
		}
		return null;
	}
}
