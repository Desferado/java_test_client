package com.denis.java_test_client.services;

import com.denis.java_test_client.utils.MethodLog;
import com.denis.java_test_client.models.Contact;
import com.denis.java_test_client.repositories.ContactRepository;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService{
    private final ContactRepository contactRepository;
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact createContact(Contact contact) {
        String phone = phoneCheck(contact.getPhone());
        if (contactRepository.findContactByPhone(phone).isEmpty()){
            contact.setPhone(phone);
            contactRepository.save(contact);
        }
        else {
            System.out.println("This is client already added");
        }
        return contact;
    }

    @Override
    public List<Contact> findAllContact() {
        return contactRepository.findAll();
    }

    @Override
    public Contact updateContact(Integer id, Contact newContact) {
        if(contactRepository.findContactById(id).isPresent())
            contactRepository.save(newContact);
        return newContact;
    }

    @Override
    public Optional<Contact> findContactById(Integer id) {
        return contactRepository.findContactById(id);
    }
    @Override
    public Optional<Contact> findContactByPhone(String phone) {
        String phoneContact = phoneCheck(phone);
        return contactRepository.findContactByPhone(phoneContact);
    }

    @Override
    public Optional<Contact> deleteContactById(Integer id) {
       Optional<Contact> deleteContact = contactRepository.findContactById(id);
        if(deleteContact.isPresent()) {
            contactRepository.deleteContactById(deleteContact.get().getId());
        }
        else {
            System.out.println("Contact not found in database");
        }
        return deleteContact;
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

