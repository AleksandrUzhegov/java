package com.ertelecom.server;

import com.ertelecom.gwt.common.ErrorDto;
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
    public List<TaskDto> showListTasks( @RequestParam(value = "statusId", required = false) Long statusId
                                      , @RequestParam(value = "ownerName", required = false) String ownerName
                                    )
    {
        Long executorId = getAuthUserId();  // находим авторизованного пользователя для фильтра
        Specification<Task> spec = taskService.getFilter(statusId, ownerName, executorId);
        return taskService.getAll(spec);
    }

    // создание новой задачи
    @PostMapping("/tasks")
    public TaskDto createNewTask(@RequestBody TaskDto taskDto)
    {
        return taskService.save(taskDto);
    }

    // удалить задачу
    @DeleteMapping("/tasks/delete/{id}")
    public ResponseEntity<String> removeTask(@PathVariable Long id)
    {
        taskService.deleteById(id);
        return new ResponseEntity<String>("Successfully removed", HttpStatus.OK);
    }

    // получение списка статусов
    @GetMapping(value = "/statuses")
    public List<StatusDto> showListStatuses()
    {
        return statusService.getAll();
    }

    // получение списка пользователей
    @GetMapping(value = "/users")
    public List<UserDto> showListUsers()
    {
        return userService.getAll();
    }

    @ExceptionHandler
    public ResponseEntity<?> exceptionInterceptor(ResourceAlreadyExistException exc) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        ResponseEntity<ErrorDto> res = new ResponseEntity<ErrorDto>(errorDto, HttpStatus.BAD_REQUEST);
        return res;
    }

    // вернуть ID авторизованного пользователя
    public Long getAuthUserId() {
        User user = userService.getAuthUser();
        if (user != null) return user.getUserId();
        return null;
    }
}
