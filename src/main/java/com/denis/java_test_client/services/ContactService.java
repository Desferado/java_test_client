package com.denis.java_test_client.services;

import com.denis.java_test_client.exception.ClientNotFoundException;
import com.denis.java_test_client.models.Contact;
import com.denis.java_test_client.repositories.ContactRepository;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAllContact() {
        return contactRepository.findAll();
    }

    public Contact updateContact(Long id, Contact newContact) {
        if(contactRepository.findContactById(id).isPresent())
            contactRepository.save(newContact);
        return newContact;
    }
    public boolean existsById(Long id) {
        return contactRepository.existsById(id);
    }
    @Transactional
    public void save(Contact contact) {
        String phone = phoneCheck(contact.getPhone());
        contact.setPhone(phone);
        contactRepository.save(contact);
    }
    public Optional<Contact> findContactById(Long id) {
        return Optional.ofNullable(contactRepository.findContactById(id)
                .orElseThrow(ClientNotFoundException::new));
    }

    public Optional<Contact> findContactByPhone(String phone) {
        String phoneContact = phoneCheck(phone);
        return contactRepository.findContactByPhone(phoneContact);
    }

    public Optional<Contact> findContactByEmail(String email) {
        return contactRepository.findContactByEmail(email);
    }

    public void deleteContactById (Long id) {
       Optional<Contact> deleteContact = contactRepository.findContactById(id);
        if(deleteContact.isPresent()) {
            contactRepository.deleteContactById(deleteContact.get().getId());
        }
        else {
            System.out.println("Contact not found in database");
        }
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

