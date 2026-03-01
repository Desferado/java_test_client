package com.denis.java_test_client.services;

import com.denis.java_test_client.models.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Contact createContact (Contact contact);
    List<Contact> findAllContact();
    Contact updateContact(Integer id, Contact newContact);
    Optional<Contact> findContactById (Integer id);

    Optional<Contact> findContactByPhone(String phone);

    Optional<Contact> deleteContactById (Integer id);
}
