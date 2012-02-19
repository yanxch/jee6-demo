package ch.demo.business.dom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Models a student.
 * 
 * @author hostettler
 */
public class Student implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = -6146935825517747043L;
    /** The student last name. */
    private String mLastName;
    /** The student first name. */
    private String mFirstName;
    /** The student birth date. */
    private Date mBirthDate;
    /** The set of grades of the student. */
    private List<Grade> mGrades;
    /** The student phone number. */
    private PhoneNumber mPhoneNumber;

    /**
     * Empty (default) constructor.
     */
    public Student() {
        this.mGrades = new ArrayList<Grade>();
        for (Discipline discipline : Discipline.values()) {
            this.mGrades.add(new Grade(discipline));
        }
    }

    /**
     * @return the phoneNumber
     */
    public final PhoneNumber getPhoneNumber() {
        return mPhoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public final void setPhoneNumber(final PhoneNumber phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    /**
     * 
     * @param lastName
     *            The student last name.
     * @param firstName
     *            The student first name.
     * @param birthDate
     *            The student birth date.
     */
    public Student(final String lastName, final String firstName,
            final Date birthDate) {
        this();
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mBirthDate = birthDate;

    }

    /**
     * @return an unique identifier
     */
    public String getKey() {
        return String.valueOf(hashCode());
    }

    /**
     * @return the lastName
     */
    public final String getLastName() {
        return mLastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public final void setLastName(final String lastName) {
        mLastName = lastName;
    }

    /**
     * @return the firstName
     */
    public final String getFirstName() {
        return mFirstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public final void setFirstName(final String firstName) {
        mFirstName = firstName;
    }

    /**
     * @return the birthDate
     */
    public final Date getBirthDate() {
        return mBirthDate;
    }

    /**
     * @param birthDate
     *            the birthDate to set
     */
    public final void setBirthDate(final Date birthDate) {
        mBirthDate = birthDate;
    }

    /**
     * @return the grade
     */
    public final Float getAvgGrade() {
        Float avg = 0.0f;
        for (Grade grade : this.mGrades) {
            if (grade.getGrade() != null) {
                Float f = grade.getGrade().floatValue();
                avg += f;
            }
        }
        return avg / this.mGrades.size();
    }

    /**
     * @return the grades
     */
    public final List<Grade> getGrades() {
        return mGrades;
    }

    /**
     * @return the actual list of discipline for this student.
     */
    public final Discipline[] getDisciplines() {
        return Discipline.values();
    }

    /**
     * Validates the current state of the student information.
     */
    public final void validate() {
        if (this.getFirstName() == null) {
            throw new IllegalArgumentException(
                    "Le prénom ne doit pas être vide");
        }
        if (this.getLastName() == null) {
            throw new IllegalArgumentException(
                    "Le nom de famille ne doit pas être vide");
        }
        if (this.getBirthDate() == null) {
            throw new IllegalArgumentException(
                    "La date de naissance ne doit pas être vide");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (this.mLastName != null) {
            return this.mLastName.hashCode();
        } else {
            return -1;
        }
    }
}
