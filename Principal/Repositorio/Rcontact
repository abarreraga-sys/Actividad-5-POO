package com.poo.actividad5.repository;

import com.poo.actividad5.model.Contact;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TxtContactRepository implements ContactRepository {

    private static final String SEPARADOR = ";";

    private final Path archivo;

    public TxtContactRepository(String rutaArchivo) {
        this.archivo = Path.of(rutaArchivo);
    }

    @Override
    public Contact save(Contact contact) {
        List<Contact> contactos = leerArchivo();
        int nuevoId = contactos.stream().mapToInt(Contact::getId).max().orElse(0) + 1;
        contact.setId(nuevoId);
        contactos.add(contact);
        escribirArchivo(contactos);
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        return leerArchivo();
    }

    @Override
    public Optional<Contact> findById(int id) {
        return leerArchivo().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public boolean update(Contact contact) {
        List<Contact> contactos = leerArchivo();
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getId() == contact.getId()) {
                contactos.set(i, contact);
                escribirArchivo(contactos);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        List<Contact> contactos = leerArchivo();
        boolean eliminado = contactos.removeIf(c -> c.getId() == id);
        if (eliminado) {
            escribirArchivo(contactos);
        }
        return eliminado;
    }

    private List<Contact> leerArchivo() {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }
        try {
            List<Contact> contactos = new ArrayList<>();
            for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
                if (!linea.isBlank()) {
                    contactos.add(parsearLinea(linea));
                }
            }
            return contactos;
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer el archivo " + archivo, e);
        }
    }

    private void escribirArchivo(List<Contact> contactos) {
        try {
            if (archivo.getParent() != null) {
                Files.createDirectories(archivo.getParent());
            }
            List<String> lineas = contactos.stream().map(this::formatearLinea).toList();
            Files.write(archivo, lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escribir el archivo " + archivo, e);
        }
    }

    private Contact parsearLinea(String linea) {
        String[] partes = linea.split(SEPARADOR, 3);
        return new Contact(Integer.parseInt(partes[0]), partes[1], partes[2]);
    }

    private String formatearLinea(Contact contact) {
        return contact.getId() + SEPARADOR + contact.getNombre() + SEPARADOR + contact.getNumero();
    }
}
