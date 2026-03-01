package com.denis.java_test_client.repositories;

import com.denis.java_test_client.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findContactByEmail(String email);

    Optional<Contact> findContactByPhone(String phone);

    Optional<Contact> findContactById(Integer id);

    void deleteContactById(Integer id);

}
