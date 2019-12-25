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

    public List<TaskDto> getAll(Specification<Task> spec) {
        return TaskMapper.MAPPER.fromTaskList(taskRepository.findAll(spec));
    }

    public TaskDto save(TaskDto taskDto) {
        Task task = TaskMapper.MAPPER.toTask(taskDto);
        task = taskRepository.save(task);
        return TaskMapper.MAPPER.fromTask(task);
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }

    // формирование фильтра
    public Specification<Task> getFilter(Long statusId, String ownerName, Long executorId) {
        Specification<Task> spec = Specification.where(null);
        if (statusId != null) {
            spec = spec.and(TaskSpecifications.statusContains(statusId));
        }
        if (ownerName != null && ownerName != "") {
            spec = spec.and(TaskSpecifications.ownerNameContains(ownerName));
        }
        if (executorId != null) {
            spec = spec.and(TaskSpecifications.executorIdContains(executorId));
        }

        return spec;
    }
}
