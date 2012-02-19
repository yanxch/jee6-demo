package ch.demo.business.dom;

import java.io.Serializable;

/**
 * Represents a phone number.
 * 
 * @author hostettler
 * 
 */
public class PhoneNumber implements Serializable {

    /** The serial ID. */
    private static final long serialVersionUID = -238688791921667729L;
    /** Phone country code. For instance 41 for Switzerland. */
    private int mCountryCode;
    /** Area code : 22 for Geneva. */
    private int mAreaCode;
    /** The number itself. */
    private long mNumber;

    /**
     * The constructor.
     * 
     * @param countryCode
     *            Phone country code. For instance 41 for Switzerland.
     * @param areaCode
     *            Area code : 22 for Geneva.
     * @param number
     *            The number itself.
     */
    public PhoneNumber(final int countryCode, final int areaCode,
            final long number) {
        this.setCountryCode(countryCode);
        this.setAreaCode(areaCode);
        this.setNumber(number);
    }

    /**
     * @return the countryCode
     */
    public final int getCountryCode() {
        return mCountryCode;
    }

    /**
     * @param countryCode
     *            the countryCode to set
     */
    public final void setCountryCode(final int countryCode) {
        mCountryCode = countryCode;
    }

    /**
     * @return the areaCode
     */
    public final int getAreaCode() {
        return mAreaCode;
    }

    /**
     * @param areaCode
     *            the areaCode to set
     */
    public final void setAreaCode(final int areaCode) {
        mAreaCode = areaCode;
    }

    /**
     * @return the number
     */
    public final long getNumber() {
        return mNumber;
    }

    /**
     * @param number
     *            the number to set
     */
    public final void setNumber(final long number) {
        mNumber = number;
    }

}