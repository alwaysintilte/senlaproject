package com.senla.project.utils.mapper;

import com.senla.project.models.DTO.requests.*;
import com.senla.project.models.DTO.responses.ReviewResponse;
import com.senla.project.models.Review;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public interface ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "appointment.id", source = "appointmentId")
    Review toEntity(ReviewRequest reviewRequest);

    @Mapping(target = "clientResponse", source = "appointment.client")
    ReviewResponse toDto(Review review);

    List<ReviewResponse> toDtoList(List<Review> reviews);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "appointment.id", source = "appointmentId")
    void updateEntity(ReviewRequest reviewRequest, @MappingTarget Review review);
}
