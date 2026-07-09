package com.poo.actividad5.model;

public class Contact {

    private int id;
    private String nombre;
    private String numero;

    public Contact(int id, String nombre, String numero) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
    }

    public Contact(String nombre, String numero) {
        this(0, nombre, numero);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Contact{id=" + id + ", nombre='" + nombre + "', numero='" + numero + "'}";
    }
}
