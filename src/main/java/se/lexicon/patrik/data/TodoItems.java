package se.lexicon.patrik.data;

import se.lexicon.patrik.model.Person;
import se.lexicon.patrik.model.Todo;

import java.util.Arrays;

import static se.lexicon.patrik.data.TodoSequencer.nextTodoId;

public class TodoItems {
    private static Todo[] todoArray = new Todo[0];

    public int size(){
        return todoArray.length;
    }

    public Todo[] findAll(){
        return todoArray;
    }

    public Todo findById(int todoId){
        Todo result = new Todo();
        for (Todo todo : todoArray){
            if (todoId == todo.getTodoId()){
                result = todo;
                break;
            }
        }
        return result;
    }
    public Todo newTodo(final String description){
        Todo newTodo = new Todo(nextTodoId(), description);
        Todo[] newTodoArray = Arrays.copyOf(todoArray, todoArray.length + 1);
        newTodoArray[newTodoArray.length -1] = newTodo;
        todoArray = newTodoArray;
        return newTodo;
    }

    public Todo[] findByDoneStatus(boolean doneStatus){
        Todo[] resultDone = new Todo[0];
        for (Todo todo : todoArray) {
            if (doneStatus == todo.isDone()) {
                resultDone = Arrays.copyOf(resultDone, resultDone.length + 1);
                resultDone[resultDone.length - 1] = todo;
            }
        }
        return resultDone;
    }
    public Todo[] findByAssignee(int personId){
        Todo[] resultAssignee = new Todo[0];
        for (Todo todo : todoArray) {
            if (todo.getAssignee() != null) {
                if (personId == todo.getAssignee().getPersonId()) {
                    resultAssignee = Arrays.copyOf(resultAssignee, resultAssignee.length + 1);
                    resultAssignee[resultAssignee.length - 1] = todo;
                }
            }
        }
        return resultAssignee;
    }
    public Todo[] findByAssignee(Person assignee){
        Todo[] result = new Todo[0];
        for (Todo todo : todoArray) {
            if (todo.getAssignee() != null) {
                if (assignee == todo.getAssignee()) {
                    result = Arrays.copyOf(result, result.length + 1);
                    result[result.length - 1] = todo;
                }
            }
        }
        return result;
    }
    public Todo[] findUnassignedTodoItems(){
        Todo[] result = new Todo[0];
        for (Todo todo : todoArray) {
            if (todo.getAssignee() == null) {
                result = Arrays.copyOf(result, result.length + 1);
                result[result.length - 1] = todo;
            }
        }
        return result;
    }
    public int getIndexTodo(Todo[] todoArray, String description) {
        int index = -1;
        for (int i = 0; i < todoArray.length; i++){
            if (todoArray[i].getDescription().equals(description)){
                index = i;
                break;
            }
        }
        return index;
    }
    public Todo[] removeByIndexTodo(final Todo[] todoArray, final int index){
        Todo[] a = Arrays.copyOfRange(todoArray, 0, index);
        Todo[] b = Arrays.copyOfRange(todoArray, index+1, todoArray.length);
        Todo[] result = Arrays.copyOf(a, a.length + b.length);
        for(int i= a.length, j=0; j < b.length; i++, j++){
            result[i] = b[i];
        }
        return result;
    }
    public boolean removeTodo(String description){
        int index = getIndexTodo(todoArray, description);
        if (index < 0){
            return false; //null or not existing
        }
        todoArray = removeByIndexTodo(todoArray, index);
        return true;
    }
    public void clear(){
        todoArray = new Todo[0];
    }
}
