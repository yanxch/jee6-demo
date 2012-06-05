/**
 * 
 */
package ch.demo.dom.moxy;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ch.demo.dom.PhoneNumber;

/**
 * Maps a phone number to a XML structure.
 * @author hostettler
 * 
 */
public class PhoneNumberAdapter extends XmlAdapter<String, PhoneNumber> {

    @Override
    public PhoneNumber unmarshal(final String value) throws Exception {
        return PhoneNumber.getAsObject((String) value);
    }

    @Override
    public String marshal(final PhoneNumber value) throws Exception {
        return PhoneNumber.getAsString((PhoneNumber) value);
    }
}