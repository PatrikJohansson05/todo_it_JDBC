package se.lexicon.patrik.model;

public class Todo {
    private final int todoId;
    private String description;
    private boolean done;
    private Person assignee;

    public Todo(int todoId, String description){
        this.todoId = todoId;
        this.description = description;
    }


    /*
    Getters and Setters
     */

    public int getTodoId() {
        return todoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getAssignee() {
        return assignee;
    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee;
    }
}
