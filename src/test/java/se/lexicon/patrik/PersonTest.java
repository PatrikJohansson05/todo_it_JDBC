package se.lexicon.patrik;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import se.lexicon.patrik.model.Person;


public class PersonTest
{
    private Person testObject;
    @Before
    public void setUp() throws Exception {
        testObject = new Person(1, "Test", "Testsson");
    }

    @Test
    public void testObject_has_correct_fields() {
        assertEquals(1, testObject.getPersonId());
        assertEquals("Test", testObject.getFirstName());
        assertEquals("Testsson", testObject.getLastName());
    }

}
