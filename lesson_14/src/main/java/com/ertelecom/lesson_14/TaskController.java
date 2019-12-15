package com.ertelecom.lesson_14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET http://localhost:8189/app/tasks

    // http://localhost:8189/app/tasks/show?statusId=1&ownerName=

    // список задач
    @GetMapping(value = "/show")
    public String showListTask(Model model
                                , @RequestParam(value = "statusId", required = false) Integer statusId
                                , @RequestParam(value = "ownerName", required = false) String ownerName
                            )
    {
        try {
            List<Task> tasks;
            // фильтры на список
            if (statusId != null) tasks = taskService.getTasksByFilter(statusId);
            else tasks = taskService.getAllTasks();
            if (ownerName != null && ownerName != "") {
                // пока так
                tasks = tasks.stream().filter(t -> (t.getOwnerName() != null && t.getOwnerName().equals(ownerName))).collect(Collectors.toList());
            }
            model.addAttribute("tasks", tasks);

            List<Task.Status> statuses = Arrays.asList(Task.Status.values());
            model.addAttribute("statuses", statuses);
            // перебрасываем фильтр
            model.addAttribute("statusId", statusId);
            model.addAttribute("ownerName", ownerName);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка формирования списка: " + e.getMessage());
        }

        return "list_tasks";
    }

    // добавление задачи
    @GetMapping(value = "/add")
    public String showAddTask(Model model) {
        try {
            Task task = new Task();
             model.addAttribute("task", task);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка добавления задачи: " + e.getMessage() );
        }

        return "add_task";
    }

    // сохранить задачу и перейти на список
    @PostMapping(value = "/save")
    public String addTask(@ModelAttribute("task") Task task)  {
        try {
            if (task.getId() > 0) taskService.updTask(task);   // сохранить
                else taskService.addTask(task);                // добавить
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка сохранения задачи: " + e.getMessage() );
        }

        return "redirect:/tasks/show";
    }

    // редактирование задачи
    @GetMapping(value = "/edit/{id}")
    public String editTask(Model model, @PathVariable (name="id") Long id) {
        try {
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            List<Task.Status> statuses = Arrays.asList(Task.Status.values());
            model.addAttribute("statuses", statuses);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка редактирования задачи: " + e.getMessage());
        }
        return "edit_task";
    }

    // удалить задачу
    @GetMapping(value = "/delete/{id}")
    public String deleteTask(Model model, @PathVariable (name="id") Long id) {
        try {
            taskService.delTaskById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка удаления задачи: " + e.getMessage());
        }

        return "redirect:/tasks/show";
    }


}
