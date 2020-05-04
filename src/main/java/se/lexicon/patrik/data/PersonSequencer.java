package se.lexicon.patrik.data;

public class PersonSequencer {
    private static int personId;

    public static int nextPersonId(){
        return ++personId;
    }

    public static void reset(){
        personId = 0;
    }
}
