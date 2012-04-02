/**
 * 
 */
package ch.demo.dom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Models a security badge.
 * 
 * @author hostettler
 */
@Entity
@Table(name = "BADGES")
public class Badge implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = 3064609886266854069L;

    /** The unique id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mId;

    /** The student's security level. */
    @Column(name = "SECURITY_LEVEL")
    private Long mSecurityLevel;

    /** The student that owns this badge. */
    @OneToOne
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    private Student mStudent;

    /**
     * @return the student
     */
    public Student getStudent() {
        return mStudent;
    }

    /**
     * @param student
     *            the student to set
     */
    public void setStudent(final Student student) {
        mStudent = student;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return mId;
    }

    /**
     * @return the securityLevel
     */
    public Long getSecurityLevel() {
        return mSecurityLevel;
    }

    /**
     * @param securityLevel
     *            the securityLevel to set
     */
    public void setSecurityLevel(final Long securityLevel) {
        mSecurityLevel = securityLevel;
    }

}
