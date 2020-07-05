package se.lexicon.patrik.data.todo_item;

import se.lexicon.patrik.model.Person;
import se.lexicon.patrik.model.Todo;

import java.util.Collection;
import java.util.Optional;

public interface TodoItems {
    Todo create (Todo todo);
    Collection<Todo> findAll();
    Optional<Todo> findById(int todoId);
    Collection<Todo> findByDoneStatus(boolean status);
    Collection<Todo> findByAssignee(int assigneeId);
    Collection<Todo> findByAssignee(Person person);
    Collection<Todo> findByUnassignedTodoItems();
    boolean deleteById(int todoId);
}
