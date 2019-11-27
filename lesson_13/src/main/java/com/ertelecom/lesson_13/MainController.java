package com.ertelecom.lesson_13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET http://localhost:8189/app/

    // главная
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage() {
        return "index";
    }

    // список задач
    @RequestMapping(value = "/list_tasks", method = RequestMethod.GET)
    public String showListTask(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "list_tasks";
    }

    // добавление задачи
    @RequestMapping(value = "/add_task", method = RequestMethod.GET)
    public String showAddTask(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "add_task";
    }

    // сохранить задачу и перейти на список
    @PostMapping(value = "/add_task_save")
    public String addTask(@ModelAttribute("task") Task task) throws SQLException {
        taskService.addTask(task);
        return "redirect:/list_tasks";
    }


}
