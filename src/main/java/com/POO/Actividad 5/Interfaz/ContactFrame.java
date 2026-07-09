package com.poo.actividadgrupal5.interfaz;

import com.poo.actividadgrupal5.model.Contact;
import com.poo.actividadgrupal5.service.ContactService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class ContactFrame extends JFrame {

    private final ContactService service;
    private final DefaultTableModel tableModel;
    private final JTable tabla;
    private final JTextField campoNombre = new JTextField(15);
    private final JTextField campoNumero = new JTextField(15);

    public ContactFrame(ContactService service) {
        this.service = service;

        setTitle("Agenda de Contactos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Id", "Nombre", "Número"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(tableModel);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                copiarSeleccionAlFormulario();
            }
        });
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel formulario = new JPanel(new FlowLayout());
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Número:"));
        formulario.add(campoNumero);

        JPanel botones = new JPanel(new FlowLayout());
        JButton botonCrear = new JButton("Crear");
        JButton botonActualizar = new JButton("Actualizar");
        JButton botonEliminar = new JButton("Eliminar");
        JButton botonLimpiar = new JButton("Limpiar");
        botonCrear.addActionListener(e -> crear());
        botonActualizar.addActionListener(e -> actualizar());
        botonEliminar.addActionListener(e -> eliminar());
        botonLimpiar.addActionListener(e -> limpiar());
        botones.add(botonCrear);
        botones.add(botonActualizar);
        botones.add(botonEliminar);
        botones.add(botonLimpiar);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(formulario, BorderLayout.NORTH);
        panelSur.add(botones, BorderLayout.SOUTH);
        add(panelSur, BorderLayout.SOUTH);

        cargarContactos();

        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    private void cargarContactos() {
        tableModel.setRowCount(0);
        for (Contact contacto : service.listar()) {
            tableModel.addRow(new Object[]{contacto.getId(), contacto.getNombre(), contacto.getNumero()});
        }
    }

    private void copiarSeleccionAlFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            campoNombre.setText(tableModel.getValueAt(fila, 1).toString());
            campoNumero.setText(tableModel.getValueAt(fila, 2).toString());
        }
    }

    private void crear() {
        try {
            service.crear(campoNombre.getText(), campoNumero.getText());
            cargarContactos();
            limpiar();
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            mostrarError("Selecciona un contacto de la tabla para actualizar");
            return;
        }
        int id = (int) tableModel.getValueAt(fila, 0);
        try {
            service.actualizar(id, campoNombre.getText(), campoNumero.getText());
            cargarContactos();
            limpiar();
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            mostrarError("Selecciona un contacto de la tabla para eliminar");
            return;
        }
        int id = (int) tableModel.getValueAt(fila, 0);
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres eliminar el contacto con id " + id + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            service.eliminar(id);
            cargarContactos();
            limpiar();
        }
    }

    private void limpiar() {
        campoNombre.setText("");
        campoNumero.setText("");
        tabla.clearSelection();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
