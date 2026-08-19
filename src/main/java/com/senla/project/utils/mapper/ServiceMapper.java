package com.senla.project.utils.mapper;

import com.senla.project.models.DTO.requests.*;
import com.senla.project.models.DTO.responses.ServiceResponse;
import com.senla.project.models.Service;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Service toEntity(ServiceRequest serviceRequest);

    ServiceResponse toDto(Service service);

    List<ServiceResponse> toDtoList(List<Service> services);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(ServiceRequest serviceRequest, @MappingTarget Service service);
}
