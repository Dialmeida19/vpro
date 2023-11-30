package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private final Connection connection;

    public Inventario() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://bd-vpro.cbtoxkznreoj.us-east-1.rds.amazonaws.com/inventario",
                "admin",
                "EdersonJaimes");
    }

    public void iniciar() {
        try {
            // Ejemplo: Mostrar productos
            mostrarProductos();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostrarProductos() throws SQLException {
        // Lógica para mostrar productos en la consola
        List<Producto> productos = getProductos();

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    public void agregarProducto(String nombre, String marca, String clase, String descripcion, double precio) throws SQLException {
        String sql = "INSERT INTO productos (nombre, marca, clase, descripcion, precio) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, marca);
            statement.setString(3, clase);
            statement.setString(4, descripcion);
            statement.setDouble(5, precio);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto agregado correctamente.");
            } else {
                System.out.println("No se pudo agregar el producto.");
            }
        }
    }

    public List<Producto> getProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                try {
                    int codigo = resultSet.getInt("codigo");
                    String nombre = resultSet.getString("nombre");
                    String marca = resultSet.getString("marca");
                    String clase = resultSet.getString("clase");
                    String descripcion = resultSet.getString("descripcion");
                    double precio = resultSet.getDouble("precio");

                    Producto producto = new Producto(codigo, nombre, marca, clase, descripcion, precio);
                    productos.add(producto);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("Error al obtener datos de la columna en el ResultSet.");
                }
            }
        }

        return productos;
    }


    public void actualizarProducto(int codigo, String nombre, String marca, String clase, String descripcion, double precio) throws SQLException {
        String sql = "UPDATE productos SET nombre=?, marca=?, clase=?, descripcion=?, precio=? WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, marca);
            statement.setString(3, clase);
            statement.setString(4, descripcion);
            statement.setDouble(5, precio);
            statement.setInt(6, codigo);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el producto. Verifica el ID.");
            }
        }
    }

    public void eliminarProducto(int codigo) throws SQLException {
        String sql = "DELETE FROM productos WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codigo);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el producto. Verifica el ID.");
            }
        }
    }

    // Clase Producto para representar los datos del producto
    public static class Producto {
        private final int codigo;
        private final String nombre;
        private final String marca;
        private final String clase;
        private final String descripcion;
        private final double precio;

        public Producto(int codigo, String nombre, String marca, String clase, String descripcion, double precio) {
            this. codigo = codigo;
            this.nombre = nombre;
            this.marca = marca;
            this.clase = clase;
            this.descripcion = descripcion;
            this.precio = precio;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public String getMarca() {
            return marca;
        }

        public String getClase() {
            return clase;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public double getPrecio() {
            return precio;
        }

        @Override
        public String toString() {
            return "Producto{" +
                    "codigo=" + codigo +
                    ", nombre='" + nombre + '\'' +
                    ", marca='" + marca + '\'' +
                    ", clase='" + clase + '\'' +
                    ", descripcion='" + descripcion + '\'' +
                    ", precio=" + precio +
                    '}';
        }
    }
}
