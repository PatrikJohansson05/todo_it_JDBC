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
    private People testObject;

    @Before
    public void setUp() {
        testObject = new People();
        testPerson = testObject.newPerson("Test", "Testsson"); //also tests newPerson method
        testObject.newPerson("Test2", "Testsson2");
        testObject.newPerson("Test3", "Testsson3");
    }

    @Test
    public void test_size_length_is_3(){
        assertEquals(3,testObject.size());
    }

    @Test
    public void test_findAll(){
        assertEquals(3,testObject.findAll().length);
    }

    @Test
    public void test_findById(){
        assertEquals("Test", testObject.findById(1).getFirstName());
    }

    @Test
    public void test_removePerson(){
        testObject.removePeople("Test", "Testsson");
        assertEquals(2, testObject.size() );
    }

    @After
    public void tearDown(){
        testObject.clear();
        PersonSequencer.reset();
    }
}
