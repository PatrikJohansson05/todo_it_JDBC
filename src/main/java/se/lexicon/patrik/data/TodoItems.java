package se.lexicon.patrik.data;

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

}
