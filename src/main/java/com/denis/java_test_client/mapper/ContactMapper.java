package com.denis.java_test_client.mapper;

import com.denis.java_test_client.dto.ContactDTO;
import com.denis.java_test_client.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);
    @Mapping(target = "id", ignore = true)
    Contact ContactDTOToContact(ContactDTO contactDTO);

    @Mapping(target = "id", source = "id")
    ContactDTO toContactDTO(Contact contact);
}
