package ch.demo.business.dom;

/**
 * Describes the discipline, the student studies.
 * 
 * @author hostettler
 */
public enum Discipline {
	/** Maths. */
	MATHEMATICS("Mathématique"),
	/** Biology. */
	BIOLOGY("Biologie"),
	/** French. */
	FRENCH("Français"),
	/** English. */
	ENGLISH("Anglais"),
	/** German. */
	GERMAN("Allemand"),
	/** History. */

	HISTORY("Histoire"),
	/** Geography. */
	GEOGRAPHY("Geographie");

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            of the discipline.
	 */
	private Discipline(final String name) {
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
