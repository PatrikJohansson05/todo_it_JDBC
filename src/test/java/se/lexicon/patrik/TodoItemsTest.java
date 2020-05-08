package se.lexicon.patrik;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.lexicon.patrik.data.People;
import se.lexicon.patrik.data.TodoItems;
import se.lexicon.patrik.data.TodoSequencer;
import se.lexicon.patrik.model.Person;
import se.lexicon.patrik.model.Todo;

import static org.junit.Assert.assertEquals;

public class TodoItemsTest {
    TodoItems testObject = new TodoItems();
    People people = new People();
    private int todoId;

    @Before
    public void setUp(){
        testObject.clear();
        testObject.newTodo("Test"); //also tests newTodo
        testObject.newTodo("Test2");
        testObject.newTodo("Test3");
        people.newPerson("Test", "Testsson");
    }
    @Test
    public void test_todo_size_length_is_3(){
        assertEquals(3,testObject.size());
    }
    @Test
    public void test_todo_findAll(){
        assertEquals(3,testObject.findAll().length);
    }
    @Test
    public void test_todo_findById(){
        assertEquals("Test3", testObject.findById(3).getDescription());
    }
    @Test
    public void test_todo_findByDoneStatus(){
        testObject.findById(1).setDone(true);
        testObject.findById(2).setDone(true);
        assertEquals(2, testObject.findByDoneStatus(true).length);
    }
    @Test
    public void test_todo_findByAssignee(){
        testObject.findById(1).setAssignee(people.findById(1));
        testObject.findById(2).setAssignee(people.findById(1));
        assertEquals(2, testObject.findByAssignee(1).length);
    }
    @Test
    public void test_todo_findByAssignee_Name(){
        testObject.findById(1).setAssignee(people.findById(1));
        testObject.findById(2).setAssignee(people.findById(1));
        assertEquals(2, testObject.findByAssignee(people.findById(1)).length);
    }
    @Test
    public void test_todo_findUnassignedTodoItems(){
        testObject.findById(1).setAssignee(people.findById(1));
        assertEquals(2, testObject.findUnassignedTodoItems().length);
    }
    @After
    public void tearDown(){
        testObject.clear();
        TodoSequencer.reset();
    }

}
