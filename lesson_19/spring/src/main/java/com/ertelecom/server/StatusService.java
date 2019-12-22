package com.ertelecom.server;

import com.ertelecom.gwt.common.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusService {
    private StatusRepository statusRepository;

    @Autowired
    public void setStatusRepository(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<StatusDto> getAll() {
        List<Status> statuses = (List<Status>)statusRepository.findAll();
        return statuses.stream().map(status -> status.getStatusDto()).collect(Collectors.toList());
    }

}