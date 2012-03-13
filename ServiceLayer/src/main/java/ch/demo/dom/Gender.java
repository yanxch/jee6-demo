/**
 * 
 */
package ch.demo.dom;

/**
 * Describes the gender of a person.
 * @author hostettler
 * 
 */
public enum Gender {
    /** Male. */
    MALE("M"), 
    /** Female. */
    FEMALE("F");

    /**
     * Constructor.
     * 
     * @param name
     *            of the discipline.
     */
    private Gender(final String name) {
        this.mName = name;
    }

    /** The actual name of the discipline. */
    private String mName;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return this.mName;
    }

}
