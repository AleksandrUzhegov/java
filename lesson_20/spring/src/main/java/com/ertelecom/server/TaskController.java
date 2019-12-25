package com.ertelecom.server;

import com.ertelecom.gwt.common.ErrorDto;
import com.ertelecom.gwt.common.StatusDto;
import com.ertelecom.gwt.common.TaskDto;
import com.ertelecom.gwt.common.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<TaskDto>> showListTasks( @RequestParam(value = "statusId", required = false) Long statusId
                                                      , @RequestParam(value = "ownerName", required = false) String ownerName
                                    )
    {
        Long executorId = getAuthUserId();  // находим авторизованного пользователя для фильтра
        Specification<Task> spec = taskService.getFilter(statusId, ownerName, executorId);
        return new ResponseEntity<>(taskService.getAll(spec), HttpStatus.OK);
    }

    // создание новой задачи
    @PostMapping("/tasks")
    public ResponseEntity<?> createNewTask(@RequestBody @Valid TaskDto taskDto, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError o : bindingResult.getAllErrors()) {
                errorMessage.append(o.getDefaultMessage()).append(";\n");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskService.save(taskDto), HttpStatus.CREATED);
    }

    // удалить задачу
    @DeleteMapping("/tasks/delete/{id}")
    public ResponseEntity<?> removeTask(@PathVariable Long id, SecurityContextHolderAwareRequestWrapper request)
    {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            return new ResponseEntity<>("Для удаления записи необходимо наличиие роли администратора", HttpStatus.BAD_REQUEST);
        }
        taskService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // получение списка статусов
    @GetMapping(value = "/statuses")
    public ResponseEntity<List<StatusDto>> showListStatuses()
    {
        return new ResponseEntity<>(statusService.getAll(), HttpStatus.OK);
    }

    // получение списка пользователей
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> showListUsers()
    {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
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
