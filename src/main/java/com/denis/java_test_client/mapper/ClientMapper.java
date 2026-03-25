package com.denis.java_test_client.mapper;

import com.denis.java_test_client.dto.ClientDTO;
import com.denis.java_test_client.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    @Mapping(target = "id", ignore = true)
    Client ClientDTOToClient(ClientDTO ClientDTO);

    @Mapping(target = "id", source = "client_id")
    ClientDTO toClientDTO(Client Client);
}
