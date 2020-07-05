package se.lexicon.patrik.data.person;

import se.lexicon.patrik.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface People {
    Person create (Person person);
    Collection<Person> findAll();
    Optional<Person> findById(int personId);
    Collection<Person> findByName(String name);
    Person update(Person person);
    boolean deleteById(int personId);
}
