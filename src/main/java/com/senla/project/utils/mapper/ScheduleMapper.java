package com.senla.project.utils.mapper;

import com.senla.project.models.DTO.requests.ScheduleRequest;
import com.senla.project.models.DTO.responses.ScheduleResponse;
import com.senla.project.models.Schedule;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "barber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Schedule toEntity(ScheduleRequest scheduleRequest);

    ScheduleResponse toDto(Schedule schedule);

    List<ScheduleResponse> toDtoList(List<Schedule> schedules);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "barber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(ScheduleRequest scheduleRequest, @MappingTarget Schedule schedule);
}
