package se.lexicon.patrik;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.lexicon.patrik.data.People;
import se.lexicon.patrik.data.PersonSequencer;
import se.lexicon.patrik.model.Person;

import static org.junit.Assert.assertEquals;

public class PeopleTest {
    private Person testPerson;
    private int personId;
    private People testObject;

    @Before
    public void setUp() {
        testObject = new People();
        testPerson = testObject.newPerson("Test", "Testsson"); //also tests newPerson method
        personId = testPerson.getPersonId();
    }

    @Test
    public void test_size_length_is_1(){
        assertEquals(1,testObject.size());
    }

    @Test
    public void test_findAll(){
        assertEquals(1,testObject.findAll().length);
    }

    @Test
    public void test_findById(){
        assertEquals("Test", testObject.findById(1).getFirstName());
    }


    @After
    public void tearDown(){
        testObject.clear();
        PersonSequencer.reset();
    }
}
