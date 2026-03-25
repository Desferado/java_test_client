package com.denis.java_test_client.repositories;

import com.denis.java_test_client.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findContactByEmail(String email);

    Contact findContactByPhone(String phone);

    Contact findContactById(Long id);

    void deleteContactById(Long id);

}
