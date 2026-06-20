package com.senla.project.utils.mapper;

import com.senla.project.utils.hash.HashUtil;
import com.senla.project.models.Barber;
import com.senla.project.models.DTO.requests.BarberRequest;
import com.senla.project.models.DTO.responses.BarberResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", imports = {HashUtil.class})
public interface BarberMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "password", expression = "java(HashUtil.encode(barberRequest.getPassword()))")
    Barber toEntity(BarberRequest barberRequest);

    BarberResponse toDto(Barber barber);

    List<BarberResponse> toDtoList(List<Barber> barbers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "password", expression = "java(HashUtil.encode(barberRequest.getPassword()))")
    void updateEntity(BarberRequest barberRequest, @MappingTarget Barber barber);
}
