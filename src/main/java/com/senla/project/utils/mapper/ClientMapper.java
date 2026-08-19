package com.senla.project.utils.mapper;

import com.senla.project.models.DTO.requests.ClientRequest;
import com.senla.project.utils.hash.HashUtil;
import com.senla.project.models.DTO.responses.ClientResponse;
import com.senla.project.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", imports = {HashUtil.class})
public interface ClientMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "password", expression = "java(HashUtil.encode(clientRequest.getPassword()))")
    Client toEntity(ClientRequest clientRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "password", expression = "java(HashUtil.encode(clientRequest.getPassword()))")
    /*po povodu expression*/
    void updateEntity(ClientRequest clientRequest, @MappingTarget Client client);

    ClientResponse toDto(Client client);

    List<ClientResponse> toDtoList(List<Client> clients);
}
