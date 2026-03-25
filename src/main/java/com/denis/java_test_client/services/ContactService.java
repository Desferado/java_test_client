package com.denis.java_test_client.services;

import com.denis.java_test_client.dto.ContactDTO;
import com.denis.java_test_client.mapper.ContactMapper;
import com.denis.java_test_client.models.Contact;
import com.denis.java_test_client.repositories.ContactRepository;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDTO> findAllContact() {
        return contactRepository.findAll().stream()
                .map(ContactMapper.INSTANCE::toContactDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO updateContact(Long id, ContactDTO newContactDTO) {
        Optional<Contact> existingContactOpt = contactRepository.findById(id);
        Contact existingContact = existingContactOpt.orElseThrow();
        existingContact.setPhone(newContactDTO.getPhone());
        existingContact.setEmail(newContactDTO.getEmail());
        contactRepository.save(existingContact);
        return ContactMapper.INSTANCE.toContactDTO(existingContact);
    }
    @Transactional
    public void save(ContactDTO contactDTO) {
        String phone = phoneCheck(contactDTO.getPhone());
        contactDTO.setPhone(phone);
        contactRepository.save(ContactMapper.INSTANCE.ContactDTOToContact(contactDTO));
    }
    public Optional<ContactDTO> findContactById(Long id) {
        return Optional.ofNullable(
                ContactMapper.INSTANCE.toContactDTO(contactRepository.findContactById(id)));
    }

    public Optional<ContactDTO> findContactByPhone(String phone) {
        String phoneContact = phoneCheck(phone);
        return Optional.ofNullable(
                ContactMapper.INSTANCE.toContactDTO(contactRepository.findContactByPhone(phoneContact)));
    }

    public Optional<ContactDTO> findContactByEmail(String email) {
        return Optional.ofNullable(
                ContactMapper.INSTANCE.toContactDTO(contactRepository.findContactByEmail(email)));
    }

    public void deleteContactById (Long id) {
            contactRepository.deleteContactById(id);
    }
    public String phoneCheck (String phone) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String formattedPhoneNumber = null;
        try {
            // Анализируем номер телефона
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phone, "");
            // Проверяем валидность номера
            boolean isValid = phoneUtil.isValidNumber(numberProto);
            System.out.println("Телефонный номер '" + phone + "' валиден: " + isValid);
            // Формируем номер в международном формате
            formattedPhoneNumber = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            System.out.println("Форматированный номер: " + formattedPhoneNumber);
        } catch (NumberParseException e) {
            System.err.println("Ошибка парсинга номера: " + e.toString());
        }
        return formattedPhoneNumber;
    }
}

