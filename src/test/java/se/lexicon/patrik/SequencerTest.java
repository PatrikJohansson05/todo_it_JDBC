package se.lexicon.patrik;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static se.lexicon.patrik.data.PersonSequencer.nextPersonId;
import static se.lexicon.patrik.data.TodoSequencer.nextTodoId;

public class SequencerTest {
    @Test
    public void nextPersonId_should_return_1(){
        int expected = 1;
        int actual = nextPersonId();
        assertEquals(expected, actual);
    }

    @Test
    public void nextTodoId_should_return_1(){
        int expected = 1;
        int actual = nextTodoId();
        assertEquals(expected, actual);
    }
}
