package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SwingUI {

    private final Inventario inventarioApp;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public SwingUI(Inventario inventarioApp) {
        this.inventarioApp = inventarioApp;

        // Crear el modelo de la tabla con columnas
        String[] columnas = {"Codigo", "Nombre", "Marca", "Clase", "Descripción", "Precio"};
        this.tableModel = new DefaultTableModel(columnas, 0);

        // Crear la tabla con el modelo de datos
        this.table = new JTable(tableModel);
    }

    public void mostrarVentana() {
        JFrame frame = new JFrame("Sistema de Inventario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Crear un panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear un panel de botones
        JPanel buttonPanel = new JPanel();
        JButton mostrarProductosButton = new JButton("Mostrar Productos");
        JButton agregarProductoButton = new JButton("Agregar Producto");
        JButton actualizarProductoButton = new JButton("Actualizar Producto");
        JButton eliminarProductoButton = new JButton("Eliminar Producto");

        // Configurar acción para el botón de mostrar productos
        mostrarProductosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductosEnTabla();
            }
        });

        // Configurar acción para el botón de agregar producto
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        // Configurar acción para el botón de actualizar producto
        actualizarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        // Configurar acción para el botón de eliminar producto
        eliminarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        // Agregar botones al panel de botones
        buttonPanel.add(mostrarProductosButton);
        buttonPanel.add(agregarProductoButton);
        buttonPanel.add(actualizarProductoButton);
        buttonPanel.add(eliminarProductoButton);

        // Agregar la tabla al panel principal
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Agregar el panel de botones al panel principal
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar el panel principal al frame
        frame.getContentPane().add(BorderLayout.CENTER, panel);

        // Hacer visible la ventana
        frame.setVisible(true);
    }

    private void mostrarProductosEnTabla() {
        // Limpiar la tabla antes de mostrar nuevos datos
        tableModel.setRowCount(0);

        // Obtener productos de la base de datos
        try {
            // Llamar al método de la clase Inventario que obtiene los productos
            // y agregar cada producto al modelo de la tabla
            List<Inventario.Producto> productos = inventarioApp.getProductos();
            for (Inventario.Producto producto : productos) {
                Object[] rowData = {
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getMarca(),
                        producto.getClase(),
                        producto.getDescripcion(),
                        producto.getPrecio()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void agregarProducto() {
        // Implementación básica para agregar un producto
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        String marca = JOptionPane.showInputDialog("Ingrese la marca del producto:");
        String clase = JOptionPane.showInputDialog("Ingrese la clase del producto:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción del producto:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto:"));

        try {
            inventarioApp.agregarProducto(nombre, marca, clase, descripcion, precio);
            JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
            mostrarProductosEnTabla(); // Actualizar la tabla después de agregar un producto
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el producto.");
        }
    }

    private void actualizarProducto() {
        // Implementación básica para actualizar un producto
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para actualizar.");
            return;
        }

        int idProducto = (int) tableModel.getValueAt(filaSeleccionada, 0);
        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto:");
        String nuevaMarca = JOptionPane.showInputDialog("Ingrese la nueva marca del producto:");
        String nuevaClase = JOptionPane.showInputDialog("Ingrese la nueva clase del producto:");
        String nuevaDescripcion = JOptionPane.showInputDialog("Ingrese la nueva descripción del producto:");
        double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el nuevo precio del producto:"));

        try {
            inventarioApp.actualizarProducto(idProducto, nuevoNombre, nuevaMarca, nuevaClase, nuevaDescripcion, nuevoPrecio);
            JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
            mostrarProductosEnTabla(); // Actualizar la tabla después de actualizar un producto
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto.");
        }
    }

    private void eliminarProducto() {
        // Implementación básica para eliminar un producto
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.");
            return;
        }

        int idProducto = (int) tableModel.getValueAt(filaSeleccionada, 0);

        try {
            inventarioApp.eliminarProducto(idProducto);
            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
            mostrarProductosEnTabla(); // Actualizar la tabla después de eliminar un producto
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto.");
        }
    }
}
