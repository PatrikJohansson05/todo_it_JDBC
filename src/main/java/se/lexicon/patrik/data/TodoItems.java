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
    public void clear(){
        todoArray = new Todo[0];
    }
    public Todo[] findByDoneStatus(boolean doneStatus){
        Todo[] resultDone = new Todo[0];
        for (int i = 0; i < todoArray.length; i++){
            if (doneStatus == todoArray[i].isDone()){
                resultDone = Arrays.copyOf(resultDone, resultDone.length+1);
                resultDone[resultDone.length - 1] = todoArray[i];
            }
        }
        return resultDone;
    }
    public Todo[] findByAssignee(int personId){
        Todo[] resultAssignee = new Todo[0];
        for (int i = 0; i < todoArray.length; i++){
            if (todoArray[i].getAssignee() != null){
                if (personId == todoArray[i].getAssignee().getPersonId()) {
                    resultAssignee = Arrays.copyOf(resultAssignee, resultAssignee.length + 1);
                    resultAssignee[resultAssignee.length - 1] = todoArray[i];
                }
            }
        }
        return resultAssignee;
    }
    public Todo[] findByAssignee(Person assignee){
        Todo[] result = new Todo[0];
        for (int i = 0; i < todoArray.length; i++){
            if (todoArray[i].getAssignee() != null){
                if (assignee == todoArray[i].getAssignee()) {
                    result = Arrays.copyOf(result, result.length + 1);
                    result[result.length - 1] = todoArray[i];
                }
            }
        }
        return result;
    }
    public Todo[] findUnassignedTodoItems(){
        Todo[] result = new Todo[0];
        for (int i = 0; i < todoArray.length; i++){
            if (todoArray[i].getAssignee() == null){
                result = Arrays.copyOf(result, result.length+1);
                result[result.length-1] = todoArray[i];
            }
        }
        return result;
    }

}
