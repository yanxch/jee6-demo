package ch.demo.dom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

import ch.demo.dom.jpa.JPAPhoneNumberConverter;

/**
 * Models a student.
 * 
 * @author hostettler
 */
@Entity
@Table(name = "STUDENTS")
public class Student implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = -6146935825517747043L;

    /** The unique id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mId;

    /** The student last name. */
    @Column(name = "LAST_NAME", length = 35)
    private String mLastName;

    /** The student first name. */
    @Column(name = "FIRST_NAME", nullable = false, length = 35)
    private String mFirstName;

    /** The student birth date. */
    @Column(name = "BIRTH_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date mBirthDate;

    /** The student phone number. */
    @Column(name = "PHONE_NUMBER")
    @Converter(name = "phoneConverter", converterClass = JPAPhoneNumberConverter.class)
    @Convert("phoneConverter")
    private PhoneNumber mPhoneNumber;

    /** The set of grades of the student. */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "STUDENTS_ID", nullable = true)
    private List<Grade> mGrades;

    /**
     * Empty (default) constructor.
     */
    public Student() {
        // this.mGrades = new HashMap<Discipline, Grade>();
        this.mGrades = new ArrayList<Grade>();
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
        validate();
    }

    /**
     * @return an unique identifier
     */
    public String getKey() {
        if (this.mId != null) {
            return String.valueOf(this.mId);
        } else {
            return String.valueOf(this.hashCode());    
        }
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
            throw new IllegalArgumentException("Firstname is mandatory");
        }
        if (this.getLastName() == null) {
            throw new IllegalArgumentException("Lastname is mandatory");
        }
        if (this.getBirthDate() == null) {
            throw new IllegalArgumentException("Birthdate is mandatory");
        }
    }

    @Override
    public int hashCode() {
        if (this.mLastName == null) {
            return -1;
        } else {
            return this.mLastName.hashCode() ^ this.mFirstName.hashCode()
                    ^ this.mBirthDate.hashCode();
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Student) {
            if (this.mLastName.equals(((Student) obj).mLastName)
                    && this.mFirstName.equals(((Student) obj).mFirstName)
                    && this.mBirthDate.equals(((Student) obj).mBirthDate)) {
                return true;
            }
        }
        return false;
    }
}
