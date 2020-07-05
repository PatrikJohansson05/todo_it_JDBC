package se.lexicon.patrik.data.person;

import se.lexicon.patrik.data.DataSource;
import se.lexicon.patrik.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PeopleRepository implements People {

    @Override
    public Person create(Person person){
        if (person.getPersonId() !=0)
            throw new IllegalArgumentException("Invalid Entry");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;
        try{
            connection = DataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO person (firstName, lastName) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.execute();

            keySet = statement.getGeneratedKeys();
            while(keySet.next()){
                person = new Person(
                        keySet.getInt(1),
                        person.getFirstName(),
                        person.getLastName()
                );
            }
        }catch (SQLException exception){
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
        return person;
    }

    private Person createPersonFromResultSet(ResultSet resultSet) throws SQLException{
        Person person = new Person(
                resultSet.getInt("person_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return person;
    }

    @Override
    public Collection<Person> findAll() {
        Collection<Person> result = new ArrayList<>();
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM person");
        ResultSet resultSet = statement.executeQuery()){
            while(resultSet.next()){
                result.add(createPersonFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Person> findById(int personId){
        Optional<Person> result = Optional.empty();

        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = createFindByIdStatement(connection, "SELECT * FROM person WHERE person_id = ?", personId);
            ResultSet resultSet = statement.executeQuery()){
            while(resultSet.next()){
                Person person = createPersonFromResultSet(resultSet);
                result = Optional.of(person);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private PreparedStatement createFindByIdStatement(Connection connection, String sql, int personId) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, personId);
        return statement;
    }

    private PreparedStatement createFindByNameStatement(Connection connection, String sql, String name) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%".concat(name).concat("%"));
        return statement;
    }
    @Override
    public Collection<Person> findByName(String name) {
        Collection<Person> result = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = createFindByNameStatement(connection, "SELECT * FROM person WHERE first_name LIKE ?", name);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Person person = createPersonFromResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Person update(Person person){
        if (person.getPersonId() == 0)
            throw new IllegalArgumentException("Invalid Entry");
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE person SET first_name = ?, last_name = ? WHERE person_id =?")){
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getPersonId());
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public boolean deleteById(int personId){
        boolean delete = false;
        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE person_id = ?")){
            statement.setInt(1, personId);
            statement.execute();
            delete = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return delete;
    }

}