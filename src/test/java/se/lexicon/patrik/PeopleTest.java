package se.lexicon.patrik;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.lexicon.patrik.data.People;
import se.lexicon.patrik.data.PersonSequencer;
import se.lexicon.patrik.model.Person;

public class PeopleTest {
    private Person[] testPerson;
    private int personId;
    private People testObject;

    @Before
    public void setUp() {
        testObject.clear();
        PersonSequencer.reset();
        testPerson = new Person[0];
        testObject = new People();
        testPerson = testObject.newPerson("Test", "Testsson");
        personId = testPerson.getPersonId();
    }

    @Test
    public void test_size_length_is_1(){
        Assert.assertEquals(1,testObject.size());
    }

    @Test
    public void test_findAll(){
        Assert.assertEquals(1,testObject.findAll().length);
    }

    @Test
    public void test_findById(){

    }
}
