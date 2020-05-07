package se.lexicon.patrik.data;
import se.lexicon.patrik.model.Person;
import java.util.Arrays;

import static se.lexicon.patrik.data.PersonSequencer.nextPersonId;


public class People {
    private static Person[] personArray = new Person[0];

    public int size(){
        return personArray.length;
    }

    public Person[] findAll(){
        return personArray;
    }
    public Person findById(int personId){
        Person result = new Person();
        for (Person person : personArray) {
            if (personId == person.getPersonId()) {
                result = person;
                break;
            }
        }
        return result;
    }
    public Person newPerson(final String firstName, final String lastName){
        Person newPerson = new Person(nextPersonId(), firstName, lastName);
        Person[] newPersonArray = Arrays.copyOf(personArray, personArray.length + 1);
        newPersonArray[newPersonArray.length -1] = newPerson;
        personArray = newPersonArray;
        return newPerson;
    }
    public void clear(){
        personArray = new Person[0];
    }

}
