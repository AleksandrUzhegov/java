package com.ertelecom.server;

import com.ertelecom.gwt.common.TaskDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {StatusMapper.class, UserMapper.class})
public interface TaskMapper {
    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);

    Task toTask(TaskDto taskDto);

    @InheritInverseConfiguration
    TaskDto fromTask(Task task);

    List<Task> toTaskList(List<TaskDto> taskDtos);

    List<TaskDto> fromTaskList(List<Task> tasks);
}
