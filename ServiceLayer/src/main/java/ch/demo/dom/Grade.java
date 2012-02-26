package ch.demo.dom;

import java.io.Serializable;

/**
 * Represents a grade for a given discipline.
 * 
 * @author hostettler
 */
public class Grade implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = -1369317473509616839L;

    /** The discipline of this grade. */
    private Discipline mDiscipline;
    /** The actual grade. */
    private Integer mGrade;

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
     * @return the discipline
     */
    public final Discipline getDiscipline() {
        return mDiscipline;
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

}
