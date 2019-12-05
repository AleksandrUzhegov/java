package com.ertelecom.lesson_15;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    private StatusRepository statusRepository;

    @Autowired
    public void setStatusRepository(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<Status> getAll() {
        return (List<Status>)statusRepository.findAll();
    }
}