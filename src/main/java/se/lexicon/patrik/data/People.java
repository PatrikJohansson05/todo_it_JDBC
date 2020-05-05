package se.lexicon.patrik.data;
import se.lexicon.patrik.model.Person;
import java.util.Arrays;


public class People {
    private static Person[] personArray = new Person[0];


    public int size(){
        int size = personArray.length;
        return size;
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
}
