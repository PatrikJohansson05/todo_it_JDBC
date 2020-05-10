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
    public int getIndex(Person[] personArray, String firstName, String lastName) {
        int index = -1;
        for (int i = 0; i < personArray.length; i++){
            if (personArray[i].getFirstName().equals(firstName) && personArray[i].getLastName().equals(lastName)){
                index = i;
                break;
            }
        }
        return index;
    }
    public Person[] removeByIndex(final Person[] personArray, final int index){
        Person[] a = Arrays.copyOfRange(personArray, 0, index);
        Person[] b = Arrays.copyOfRange(personArray, index+1, personArray.length);
        Person[] result = Arrays.copyOf(a, a.length + b.length);
        for(int i= a.length, j=0; j < b.length; i++, j++){
            result[i] = b[i];
        }
        return result;
    }
    public boolean removePeople(String firstName, String lastName){
        int index = getIndex(personArray, firstName, lastName);
        if (index < 0){
            return false; //null or not existing
        }
        personArray = removeByIndex(personArray, index);
        return true;
    }
    public void clear(){
        personArray = new Person[0];
    }

}
