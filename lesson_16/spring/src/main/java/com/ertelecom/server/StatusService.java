package com.ertelecom.server;

import com.ertelecom.gwt.common.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService {
    private StatusRepository statusRepository;

    @Autowired
    public void setStatusRepository(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<StatusDto> getAll() {
        List<Status> statuses = (List<Status>)statusRepository.findAll();
        List<StatusDto> statusDtos = new ArrayList();

        for (Status t: statuses) {
            statusDtos.add( t.getStatusDto());
        }

        return statusDtos;
    }

}