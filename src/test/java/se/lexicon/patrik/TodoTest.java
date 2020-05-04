package se.lexicon.patrik;

import org.junit.Before;
import org.junit.Test;
import se.lexicon.patrik.model.Todo;

import static org.junit.Assert.assertEquals;

public class TodoTest {
    private Todo testObject;
    @Before
    public void setUp() throws Exception {
        testObject = new Todo(1, "Test");
    }

    @Test
    public void testObject_has_correct_fields() {
        assertEquals(1, testObject.getTodoId());
        assertEquals("Test", testObject.getDescription());
    }

}