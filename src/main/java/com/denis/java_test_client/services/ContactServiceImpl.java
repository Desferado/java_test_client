package com.denis.java_test_client.services;

import com.denis.java_test_client.models.Contact;
import com.denis.java_test_client.repositories.ContactRepository;
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
        if (contactRepository.findContactByPhone(contact.getPhone()).isEmpty()){
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
    public Contact updateContact(Contact contact, Contact newContact) {
        if(contactRepository.findContactByPhone(contact.getPhone()).isPresent())
            contactRepository.save(newContact);
        return newContact;
    }

    @Override
    public void deleteContact(Contact contact) {
       Optional<Contact> deleteContact = contactRepository.findContactByPhone(contact.getPhone());
        if(deleteContact.isPresent()) {
            contactRepository.deleteContactById(deleteContact.get().getId());
        }
        else {
            System.out.println("Contact not found in database");
        }
    }
}
