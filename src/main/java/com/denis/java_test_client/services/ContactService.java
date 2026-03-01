package com.denis.java_test_client.services;

import com.denis.java_test_client.models.Contact;

import java.util.List;

public interface ContactService {
    Contact createContact (Contact contact);
    List<Contact> findAllContact ();
    Contact updateContact(Contact contact, Contact newContact);
    void deleteContact (Contact contact);
}
