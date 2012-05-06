package ch.demo.dom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents a grade for a given discipline.
 * 
 * @author hostettler
 */
@Entity
@Table(name = "GRADES")
public class Grade implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = -1369317473509616839L;

    /** The unique id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mId;

    /** The discipline of this grade. */
    @Column(name = "DISCIPLINE", nullable = false)
    private Discipline mDiscipline;

    /** The actual grade. */
    @Column(name = "GRADE", nullable = true)
    private Integer mGrade;

    /** The student that has obtained this grade. */
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", nullable = true)
    private Student mStudent;

    /**
     * Constructor that builds a grade for a given discipline.
     * 
     * @param discipline
     *            to grade.
     */
    public Grade(final Discipline discipline) {
        this.mDiscipline = discipline;
    }

    /**
     * Constructor that builds a grade for a given discipline.
     * 
     * @param discipline
     *            to grade.
     * @param grade
     *            the actual grade.
     */
    public Grade(final Discipline discipline, final Integer grade) {
        this.mDiscipline = discipline;
        this.mGrade = grade;
    }

    /**
     * Constructor that builds a grade for a given discipline.
     * 
     */
    public Grade() {
    }

    /**
     * @return the discipline
     */
    public final Discipline getDiscipline() {
        return mDiscipline;
    }

    /**
     * @param discipline
     *            to set
     */
    public final void setDiscipline(final Discipline discipline) {
        mDiscipline = discipline;
    }

    /**
     * @return the grade
     */
    public final Integer getGrade() {
        return mGrade;
    }

    /**
     * @param grade
     *            the grade to set
     */
    public final void setGrade(final Integer grade) {
        mGrade = grade;
    }

    /**
     * @return the id
     */
    public final long getId() {
        return mId;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return mStudent;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(final Student student) {
        mStudent = student;
    }

}
