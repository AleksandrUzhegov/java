package com.ertelecom.lesson_15;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAll() {
        return (List<Task>)taskRepository.findAll();
    }

    public List<Task> getAll(Specification<Task> spec) {
        return taskRepository.findAll(spec);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public Task findById(long id) {
        return taskRepository.findById(id).get();
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }

    public Page<Task> getAll(Specification<Task> spec, Pageable pageable) {
        return taskRepository.findAll(spec, pageable);
    }

    // формированаие фильтра
    public Specification<Task> getFilter(Integer statusId, String ownerName) {
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
