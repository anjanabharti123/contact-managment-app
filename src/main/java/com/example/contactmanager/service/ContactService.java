package com.example.contactmanager.service;

import com.example.contactmanager.entity.Contact;
import com.example.contactmanager.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    public Contact createContact(Contact contact) {
        if (repository.existsByEmail(contact.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (repository.existsByPhone(contact.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }
        return repository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public Contact getContactById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public Contact updateContact(Long id, Contact contact) {
        Contact existing = getContactById(id);
        existing.setFirstName(contact.getFirstName());
        existing.setLastName(contact.getLastName());
        existing.setAddress(contact.getAddress());
        existing.setEmail(contact.getEmail());
        existing.setPhone(contact.getPhone());
        return repository.save(existing);
    }

    public void deleteContact(Long id) {
        repository.deleteById(id);
    }
}