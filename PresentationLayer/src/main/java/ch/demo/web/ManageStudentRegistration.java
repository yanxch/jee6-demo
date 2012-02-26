package ch.demo.web;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;
import org.slf4j.Logger;

import ch.demo.business.service.JPAImpl;
import ch.demo.business.service.StudentService;
import ch.demo.dom.Student;

/**
 * Controller for the Student registration process.
 * @author hostettler
 * 
 */

/** The name of the instance of this object used in the JSF. */
@Named
/** Only one instance per flow. */
@ConversationScoped
public class ManageStudentRegistration implements Serializable {

	/** The serial-id. */
	private static final long serialVersionUID = 2123342218792192804L;

	/** The logger for the class. */
	@Inject
	private transient Logger mLogger;
	
	/** The number of partitions of the domain of the grades. */
	private static final int PARTS = 4;

	/** Work instance. */
	private Student mStudent;

	/** Inject the current conversation. */
	@Inject
	private Conversation mConversation;

	/** The service that provides the business logic for the student registration process. */
	@Inject @JPAImpl
	private StudentService mService;

	/**
	 * Action that adds the current student using the Student service.
	 * 
	 * @return the next action to perform (see faces-config)
	 */
	public String add() {
		this.mStudent.validate();
		this.mService.add(this.mStudent);
		this.mConversation.end();
		return "success";
	}

	/**
	 * Action that forwards to the registration page.
	 * 
	 * @return the next action to perform (see faces-config)
	 */
	public String toRegistration() {
		mLogger.debug("registration");
		this.mStudent = new Student();
		// Reset the flow
		if (!this.mConversation.isTransient()) {
			this.mConversation.end();
		}
		this.mConversation.begin();
		return "register";
	}

	/**
	 * Action that forwards to the data list.
	 * 
	 * @return the next action to perform (see faces-config)
	 */
	public String toList() {
		mLogger.debug("list");
		// Ends the flow
		this.mConversation.end();
		return "list";
	}

	/**
	 * Selects a given student.
	 * 
	 * @param student
	 *            to select
	 */
	public void setSelectedStudent(final Student student) {
		this.mStudent = student;
	}

	/**
	 * @return the selected student
	 */
	public Student getSelectedStudent() {
		return this.getStudent();
	}

	/**
	 * @return the current instance of student
	 */
	public Student getStudent() {
		return this.mStudent;
	}

	/**
	 * @return the student service
	 */
	public StudentService getService() {
		return mService;
	}

	/**
	 * Builds a pie chart based on the information taken from the student service. It splits the students in 4
	 * categories according to their grade.
	 * 
	 * @return a pie chart
	 */
	public PieChartModel getPieModel() {
		PieChartModel pie = new PieChartModel();

		int[] grades = this.getService().getDistribution(PARTS);
		int min = 0;
		int max = StudentService.TOTAL / PARTS;
		for (int g : grades) {
			pie.set(min + "< note < " + max, g);
			min = max;
			max = max + StudentService.TOTAL / PARTS;
		}

		return pie;
	}
}
