package app.controller;

import app.dao.ToDoDao;
import app.model.ToDo;
import app.model.ToDoList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ToDoListController {

    private final ToDoDao todoDao;

    public ToDoListController(ToDoDao todoDao) {
        this.todoDao = todoDao;
    }

    @RequestMapping(value = "/add-todo-list", method = RequestMethod.POST)
    public String addTodoList(@ModelAttribute("new_todo_list") ToDoList todoList) {
        todoDao.addTodoList(todoList);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/remove-todo-list", method = RequestMethod.POST)
    public String removeTodoList(@RequestParam("list_id") int listId) {
        todoDao.removeTodoList(listId);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(@ModelAttribute("todo") ToDo todo,
                          @RequestParam("list_id") int listId) {
        todoDao.addTodo(listId, todo);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/change-todo-status", method = RequestMethod.POST)
    public String changeTodoStatus(@RequestParam("todo_id") int todoId) {
        todoDao.changeStatus(todoId);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/show-all", method = RequestMethod.GET)
    public String showTodoLists(Model model) {
        prepareModel(model);
        return "index";
    }

    private void prepareModel(Model model) {
        model.addAttribute("todo_lists", todoDao.getToDoLists());
        model.addAttribute("new_todo_list", new ToDoList());
        model.addAttribute("todo_list", new ToDoList());
        model.addAttribute("todo", new ToDo());
    }

}
