package com.ertelecom.server;

import com.ertelecom.gwt.common.StatusDto;
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

    public List<StatusDto> getAll() {
        return StatusMapper.MAPPER.fromStatusList((List<Status>)statusRepository.findAll());
    }

}