package app.dao;

import app.model.ToDo;
import app.model.ToDoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ToDoInMemoryDao implements ToDoDao {

    private final AtomicInteger todoLastId = new AtomicInteger(0);
    private final AtomicInteger todoListLastId = new AtomicInteger(0);
    private final List<ToDoList> toDoLists = new CopyOnWriteArrayList<>();
    private final Map<Integer, ToDoList> todoListById = new ConcurrentHashMap<>();
    private final Map<Integer, ToDo> todoById = new ConcurrentHashMap<>();

    @Override
    public int addTodo(int listId, ToDo todo) {
        int id = todoLastId.getAndIncrement();
        todo.setId(id);
        todoListById.get(listId).addTodo(todo);
        todoById.put(id, todo);
        return id;
    }

    @Override
    public int addTodoList(ToDoList todoList) {
        int id = todoListLastId.getAndIncrement();
        todoList.setId(id);
        toDoLists.add(todoList);
        todoListById.put(id, todoList);
        return id;
    }

    @Override
    public List<ToDoList> getToDoLists() {
        return new ArrayList<>(toDoLists);
    }

    @Override
    public void removeTodoList(int id) {
        toDoLists.removeIf(toDoList -> toDoList.getId() == id);
        todoListById.remove(id);
    }

    @Override
    public void changeStatus(int todoId) {
        todoById.get(todoId).changeStatus();
    }

}