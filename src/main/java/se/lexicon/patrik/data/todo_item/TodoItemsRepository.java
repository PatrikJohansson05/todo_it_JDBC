package se.lexicon.patrik.data.todo_item;

import se.lexicon.patrik.data.DataSource;
import se.lexicon.patrik.data.person.People;
import se.lexicon.patrik.data.person.PeopleRepository;
import se.lexicon.patrik.model.Person;
import se.lexicon.patrik.model.Todo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class TodoItemsRepository implements TodoItems {

    public static final String INSERT_WITH_ASSIGNEE = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_WITHOUT_ASSIGNEE = "INSERT INTO todo_item (title, description, deadline, done) VALUES (?, ?, ?, ?)";

    public Todo createWithNoAssignee(Todo newTodo){

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;
        try{
            connection = DataSource.getConnection();
            statement = connection.prepareStatement(INSERT_WITHOUT_ASSIGNEE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newTodo.getTitle());
            statement.setString(2, newTodo.getDescription());
            statement.setObject(3, newTodo.getDeadLine());
            statement.setBoolean(4, newTodo.isDone());
            statement.execute();

            keySet = statement.getGeneratedKeys();
            while(keySet.next()){
                newTodo = new Todo(
                        keySet.getInt(1),
                        newTodo.getTitle(),
                        newTodo.getDescription(),
                        newTodo.getDeadLine(),
                        newTodo.isDone(),
                        newTodo.getAssignee()
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try{
                if(keySet != null){
                    keySet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }

            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return newTodo;
    }


    @Override
    public Todo create(Todo newTodo){
        if(newTodo.getTodoId() != null) {
            throw new IllegalArgumentException();
        }
        if(newTodo.getAssignee() == null){
            return createWithNoAssignee(newTodo);
        }

        if(newTodo.getAssignee().getPersonId() == null){
            People People = new PeopleRepository();
            Person persisted = People.create(newTodo.getAssignee());
            newTodo.setAssignee(persisted);
        }

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;
        try{
            connection = DataSource.getConnection();
            statement = connection.prepareStatement(INSERT_WITH_ASSIGNEE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newTodo.getTitle());
            statement.setString(2, newTodo.getDescription());
            statement.setObject(3, newTodo.getDeadLine());
            statement.setBoolean(4, newTodo.isDone());
            statement.setInt(5, newTodo.getAssignee().getPersonId());
            statement.execute();

            keySet = statement.getGeneratedKeys();
            while(keySet.next()){
                newTodo = new Todo(
                    keySet.getInt(1),
                    newTodo.getTitle(),
                    newTodo.getDescription(),
                    newTodo.getDeadLine(),
                    newTodo.isDone(),
                    newTodo.getAssignee()
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try{
                if(keySet != null){
                    keySet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }

            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return newTodo;
    }

    @Override
    public Collection<Todo> findAll() {
        Collection<Todo> result = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo_item");
        ResultSet resultSet = statement.executeQuery()){
            while(resultSet.next()){
                result.add(createTodoItemFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Todo> findById(int todoId){
        Optional<Todo> result = Optional.empty();

        try(
                Connection connection = DataSource.getConnection();
                PreparedStatement statement = createFindByIdStatement(connection, "SELECT * FROM todo_item WHERE todo_id = ?", todoId);
                ResultSet resultSet = statement.executeQuery()
                ){
            while(resultSet.next()){
                Todo todo = createTodoItemFromResultSet(resultSet);
                result = Optional.of(todo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private Todo createTodoItemFromResultSet(ResultSet resultSet) throws SQLException {
        Todo todo = new Todo(
                resultSet.getInt("todo_id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getObject("deadline", LocalDate.class),
                resultSet.getBoolean("done"),
                null
        );
        People people = new PeopleRepository();
        Optional<Person> optionalPerson = people.findById(resultSet.getInt("assignee_id"));
        optionalPerson.ifPresent(todo::setAssignee);
        return todo;
    }

    private PreparedStatement createFindByIdStatement(Connection connection, String sql, int todoId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, todoId);
        return statement;
    }

    private PreparedStatement createFindByDoneStatusStatement(Connection connection, String findByDoneStatus, boolean status) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(findByDoneStatus);
        statement.setBoolean(1, status);
        return statement;
    }

    @Override
    public Collection<Todo> findByDoneStatus(boolean status){
        Collection<Todo> result = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = createFindByDoneStatusStatement(connection, "SELECT * FROM todo_item WHERE done = ?", status);
        ResultSet resultSet = statement.executeQuery()){
            while(resultSet.next()){
                result.add(createTodoItemFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    private PreparedStatement createFindByAssigneeStatement(Connection connection, String findByAssignee, int assigneeId) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(findByAssignee);
        statement.setInt(1, assigneeId);
        return statement;
    }

    @Override
    public Collection<Todo> findByAssignee(int assigneeId){
        Collection<Todo> result = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = createFindByAssigneeStatement(connection, "SELECT * FROM todo_item WHERE assignee_id = ?", assigneeId);
        ResultSet resultSet = statement.executeQuery()){
            while(resultSet.next()){
                result.add(createTodoItemFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    private PreparedStatement createFindByAssigneeStatement(Connection connection, String findByAssignee, Person person) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(findByAssignee);
        statement.setInt(1, person.getPersonId());
        return statement;
    }

    @Override
    public Collection<Todo> findByAssignee(Person person){
        Collection<Todo> result = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = createFindByAssigneeStatement(connection, "SELECT * FROM todo_item WHERE assignee_id = ?", person);
        ResultSet resultSet = statement.executeQuery()){
            while(resultSet.next()){
                result.add(createTodoItemFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Todo> findByUnassignedTodoItems(){
        Collection<Todo> result = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo_item WHERE assignee_id = 0");
        ResultSet resultSet = statement.executeQuery()){
            while(resultSet.next()){
                result.add(createTodoItemFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteById(int todoId){
        boolean delete = false;
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM todo_item WHERE todo_id = ?")){
            statement.setInt(1, todoId);
            statement.executeUpdate();
            delete = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return delete;
    }
}