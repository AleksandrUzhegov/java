package com.ertelecom.server;

import com.ertelecom.gwt.common.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // преобразование списка Task в TaskDto
    private List<TaskDto> getListTaskDto(List<Task> tasks) {
        return tasks.stream().map(task -> task.getTaskDto()).collect(Collectors.toList());
    }

    public List<TaskDto> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return getListTaskDto(tasks);
    }

    public List<TaskDto> getAll(Specification<Task> spec) {
        List<Task> tasks = taskRepository.findAll(spec);
        return getListTaskDto(tasks);
    }

    public TaskDto save(TaskDto taskDto) {
        taskRepository.save(new Task(taskDto));
        return taskDto;
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }

    // формирование фильтра
    public Specification<Task> getFilter(Long statusId, String ownerName) {
        Specification<Task> spec = Specification.where(null);
        if (statusId != null) {
            spec = spec.and(TaskSpecifications.statusContains(statusId));
        }
        if (ownerName != null && ownerName != "") {
            spec = spec.and(TaskSpecifications.ownerNameContains(ownerName));
        }
        return spec;
    }



}
