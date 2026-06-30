package com.senla.project.utils.mapper;

import com.senla.project.models.Appointment;
import com.senla.project.models.DTO.requests.AppointmentRequest;
import com.senla.project.models.DTO.responses.AppointmentResponse;
import com.senla.project.models.Service;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BarberMapper.class, ServiceMapper.class})
public interface AppointmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "review", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "barber.id", source = "barberId")
    @Mapping(target = "services", ignore = true)
    Appointment toEntity(AppointmentRequest appointmentRequest);

    @Mapping(target = "barberInfoResponse", source = "barber")
    @Mapping(target = "serviceResponses", source = "services")
    @Mapping(target = "totalPrice", ignore = true)
    AppointmentResponse toDto(Appointment appointment);

    List<AppointmentResponse> toDtoList(List<Appointment> appointments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "review", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "barber.id", source = "barberId")
    @Mapping(target = "services", ignore = true)
    void updateEntity(AppointmentRequest appointmentRequest, @MappingTarget Appointment appointment);

    @AfterMapping
    default void calculateTotalPrice(Appointment appointment, @MappingTarget AppointmentResponse response) {
        double[] total = {0};
        if (!appointment.getServices().isEmpty()) {
            appointment.getServices().stream().forEach(service -> total[0] += service.getPrice());
        }
        response.setTotalPrice(total[0]);
    }
}