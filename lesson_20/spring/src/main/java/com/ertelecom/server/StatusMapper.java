package com.ertelecom.server;

import com.ertelecom.gwt.common.StatusDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StatusMapper {
    StatusMapper MAPPER = Mappers.getMapper(StatusMapper.class);

    Status toStatus(StatusDto statusDto);

    @InheritInverseConfiguration
    StatusDto fromStatus(Status status);

    List<Status> toStatusList(List<StatusDto> statusDtos);

    List<StatusDto> fromStatusList(List<Status> statuses);
}
