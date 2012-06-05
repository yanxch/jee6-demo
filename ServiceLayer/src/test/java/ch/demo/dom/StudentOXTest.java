/**
 * 
 */
package ch.demo.dom;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;

/**
 * @author hostettler
 * 
 */
public class StudentOXTest {

    /**
     * Test the Object to XML mapping.
     * 
     * @throws Exception
     *             if anything goes wrong
     */
    @Test
    public void testStudentOXMappingWrite() throws Exception {

        Student student = new Student("Lion Hearth", "Richard", new Date());
        Address address = new Address();
        address.setCity("Carouge");
        address.setNumber("7");
        address.setPostalCode("1227");
        address.setStreet("Drize");
        student.setAddress(address);
        Badge b = new Badge();
        b.setSecurityLevel(30L);
        b.setStudent(student);
        student.setBadge(b);
        student.setPhoneNumber(new PhoneNumber(0, 0, 0));
        for (Discipline d : Discipline.values()) {
            Grade g = new Grade(d, 10);
            student.getGrades().add(g);
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(student, System.out);
    }

    /**
     * Test the Object to XML mapping.
     * 
     * @throws Exception
     *             if anything goes wrong
     */
    @Test
    public void testStudentOXMappingRead() throws Exception {
        InputStream stream = StudentOXTest.class
                .getResourceAsStream("/student.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Student student = (Student) unmarshaller.unmarshal(stream);
        System.out.println(student);

        SchemaOutputResolver sor = new SchemaOutputResolver() {
            @Override
            public Result createOutput(final String namespaceUri,
                    final String suggestedFileName) throws IOException {
                Result result = new StreamResult(System.out);
                result.setSystemId("System.out");
                return result;
            }
        };
        jaxbContext.generateSchema(sor);
    }
}
