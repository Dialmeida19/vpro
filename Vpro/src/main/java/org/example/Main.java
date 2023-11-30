package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Inventario inventarioApp = new Inventario();

            // Crear una instancia de la interfaz de usuario de escritorio
            SwingUI swingUI = new SwingUI(inventarioApp);
            swingUI.mostrarVentana();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


