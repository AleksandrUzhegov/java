package com.ertelecom.server;

import com.ertelecom.gwt.common.StatusDto;
import com.ertelecom.gwt.common.TaskDto;
import com.ertelecom.gwt.common.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    // список задач
    @GetMapping(value = "/tasks")
    public List<TaskDto> showListTasks(Model model
                                , @RequestParam(value = "statusId", required = false) Long statusId
                                , @RequestParam(value = "ownerName", required = false) String ownerName
                            )
    {
        Specification<Task> spec = taskService.getFilter(statusId, ownerName);
        return taskService.getAll(spec);
    }

    // создание новой задачи
    @PostMapping("/tasks")
    public TaskDto createNewTask(@RequestParam String taskName
                               , @RequestParam String info
                               , @RequestParam Long ownerId
                               , @RequestParam Long executorId
                               , @RequestParam(value = "id", required = false) Long id
                               , @RequestParam(value = "statusId", required = false) Long statusId
                             )
    {
        TaskDto taskDto = new TaskDto(id, taskName, info, statusId, ownerId, executorId);
        return taskService.save(taskDto);
    }

    // удалить задачу
    @GetMapping("/tasks/delete/{id}")
    public ResponseEntity<String> removeTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return new ResponseEntity<String>("Successfully removed", HttpStatus.OK);
    }

    // получение списка статусов
    @GetMapping(value = "/statuses")
    public List<StatusDto> showListStatuses(Model model)
    {
        return statusService.getAll();
    }

    // получение списка пользователей
    @GetMapping(value = "/users")
    public List<UserDto> showListUsers(Model model)
    {
        return userService.getAll();
    }
}
