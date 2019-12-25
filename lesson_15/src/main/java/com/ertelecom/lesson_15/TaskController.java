package com.ertelecom.lesson_15;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    private StatusService statusService;
    private UserService userService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setStatusService(StatusService statusService) {
        this.statusService = statusService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // GET http://localhost:8189/app/tasks

    // http://localhost:8189/app/tasks/show?statusId=1&ownerName=

    // список задач
    @GetMapping(value = "/show")
    public String showListTask(Model model
                                , @RequestParam(defaultValue = "1") Long pageNumber
                                , @RequestParam(value = "statusId", required = false) Integer statusId
                                , @RequestParam(value = "ownerName", required = false) String ownerName
                            )
    {
        try {
            int tasksPerPage = 15;
            if (pageNumber < 1L) pageNumber = 1L;

            Specification<Task> spec = taskService.getFilter(statusId, ownerName);
            Page<Task> tasksPage = taskService.getAll(spec, PageRequest.of(pageNumber.intValue() - 1, tasksPerPage, Sort.Direction.ASC, "id"));
            model.addAttribute("tasksPage", tasksPage);

            List<Status> statuses = statusService.getAll();
            model.addAttribute("statuses", statuses);
            List<User> users = userService.getAll();
            model.addAttribute("users", users);

            // перебрасываем фильтр
            model.addAttribute("statusId", statusId);       // на странице не получилось в циклах прочитать param.*
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
            List<User> users = userService.getAll();
            model.addAttribute("users", users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка добавления задачи: " + e.getMessage() );
        }

        return "add_task";
    }

    // сохранить задачу и перейти на список
    @PostMapping(value = "/save")
    public String addTask(@ModelAttribute("task") Task task)  {
        try {
            taskService.save(task);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка сохранения задачи: " + e.getMessage() );
        }

        return "redirect:/tasks/show";
    }

    // редактирование задачи
    @GetMapping(value = "/edit/{id}")
    public String editTask(Model model, @PathVariable (name="id") Long id) {
        try {
            Task task = taskService.findById(id);
            model.addAttribute("task", task);
            List<Status> statuses = statusService.getAll();
            model.addAttribute("statuses", statuses);
            List<User> users = userService.getAll();
            model.addAttribute("users", users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка редактирования задачи: " + e.getMessage());
        }
        return "edit_task";
    }

    // удалить задачу
    @GetMapping(value = "/delete/{id}")
    public String deleteTask(Model model, @PathVariable (name="id") Long id) {
        try {
            taskService.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ошибка удаления задачи: " + e.getMessage());
        }

        return "redirect:/tasks/show";
    }


}
