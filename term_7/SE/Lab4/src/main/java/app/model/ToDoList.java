package app.model;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

    private int id;
    private String name;
    private List<ToDo> todoList;

    public ToDoList() {
        todoList = new ArrayList<>();
    }

    public ToDoList(String name) {
        this();
        setName(name);
    }

    public ToDoList(int id, String name, List<ToDo> todoList) {
        this.id = id;
        this.name = name;
        this.todoList = todoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ToDo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<ToDo> todoList) {
        this.todoList = todoList;
    }

    public void addTodo(ToDo todo) {
        todoList.add(todo);
    }

}
