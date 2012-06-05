/**
 * 
 */
package ch.demo.dom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hostettler
 * 
 */
@Embeddable
@XmlRootElement(name = "address", namespace = "http://ch.demo.app")
@XmlAccessorType(XmlAccessType.FIELD)
public class Address implements Serializable {

    /** The serial id. */
    private static final long serialVersionUID = 1197893493017932784L;

    /** house number. */
    @Column(name = "NUMBER")
    private String mNumber;

    /** the name of the street. */
    @Column(name = "STREET")
    private String mStreet;

    /** the city name. */
    @Column(name = "CITY")
    private String mCity;

    /** the postal code. */
    @Column(name = "POSTAL_CODE")
    private String mPostalCode;

    /**
     * @return the number
     */
    protected String getNumber() {
        return mNumber;
    }

    /**
     * @param number
     *            the number to set
     */
    protected void setNumber(final String number) {
        mNumber = number;
    }

    /**
     * @return the street
     */
    protected String getStreet() {
        return mStreet;
    }

    /**
     * @param street
     *            the street to set
     */
    protected void setStreet(final String street) {
        mStreet = street;
    }

    /**
     * @return the city
     */
    protected String getCity() {
        return mCity;
    }

    /**
     * @param city
     *            the city to set
     */
    protected void setCity(final String city) {
        mCity = city;
    }

    /**
     * @return the postalCode
     */
    protected String getPostalCode() {
        return mPostalCode;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    protected void setPostalCode(final String postalCode) {
        mPostalCode = postalCode;
    }

}
