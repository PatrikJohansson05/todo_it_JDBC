package se.lexicon.patrik;

import se.lexicon.patrik.data.DataSource;
import se.lexicon.patrik.data.person.PeopleRepository;
import se.lexicon.patrik.data.todo_item.TodoItemsRepository;
import se.lexicon.patrik.model.Person;
import se.lexicon.patrik.model.Todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )  throws SQLException{
        PeopleRepository PeopleRepository = new PeopleRepository();
        TodoItemsRepository TodoRepository = new TodoItemsRepository();
        PeopleRepository.create(new Person("Test", "Testsson"));
    }

}
