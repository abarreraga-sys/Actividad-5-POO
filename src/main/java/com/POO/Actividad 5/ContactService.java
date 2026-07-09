package com.poo.actividad5.service;

import com.poo.actividad5.model.Contact;
import com.poo.actividad5.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

public class ContactService {

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public Contact crear(String nombre, String numero) {
        validar(nombre, numero);
        return repository.save(new Contact(nombre.trim(), numero.trim()));
    }

    public List<Contact> listar() {
        return repository.findAll();
    }

    public Optional<Contact> buscarPorId(int id) {
        return repository.findById(id);
    }

    public boolean actualizar(int id, String nombre, String numero) {
        validar(nombre, numero);
        return repository.update(new Contact(id, nombre.trim(), numero.trim()));
    }

    public boolean eliminar(int id) {
        return repository.deleteById(id);
    }

    private void validar(String nombre, String numero) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número no puede estar vacío");
        }
    }
}
