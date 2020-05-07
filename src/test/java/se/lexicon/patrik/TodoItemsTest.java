package se.lexicon.patrik;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.lexicon.patrik.data.TodoItems;
import se.lexicon.patrik.data.TodoSequencer;
import se.lexicon.patrik.model.Todo;

import static org.junit.Assert.assertEquals;

public class TodoItemsTest {
    private Todo testTodo;
    private int todoId;
    private TodoItems testObject;

    @Before
    public void setUp(){
        testObject = new TodoItems();
        testTodo = testObject.newTodo("Test"); //also tests newTodo
        todoId = testTodo.getTodoId();
    }
    @Test
    public void test_todo_size_length_is_1(){
        assertEquals(1,testObject.size());
    }
    @Test
    public void test_todo_findAll(){
        assertEquals(1,testObject.findAll().length);
    }
    @Test
    public void test_todo_findById(){
        assertEquals("Test", testObject.findById(1).getDescription());
    }
    @After
    public void tearDown(){
        testObject.clear();
        TodoSequencer.reset();
    }
}
