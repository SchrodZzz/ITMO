package app.dao;

import app.model.ToDo;
import app.model.ToDoList;

import java.util.List;

public interface ToDoDao {

    int addTodo(int listId, ToDo todo);

    int addTodoList(ToDoList todoList);

    List<ToDoList> getToDoLists();

    void removeTodoList(int id);

    void changeStatus(int todoId);

}
