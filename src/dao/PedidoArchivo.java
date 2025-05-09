package dao;

import java.io.*;
import modelo.Pedido;

public class PedidoArchivo {
    private final String archivoPedidos = "pedidos.txt";

    public void guardarPedido(Pedido pedido) {
        try {
            File file = new File(archivoPedidos);
            // Si el archivo no existe, lo creamos
            if (!file.exists()) {
                System.out.println("El archivo de pedidos no existe, se creará uno nuevo.");
                file.createNewFile();
            }

            // Guardar pedido en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoPedidos, true))) {
                writer.write("Cliente: " + pedido.getNombreCliente() + " - Plato: " + pedido.getPlato());
                writer.newLine(); // Añadimos un salto de línea después de cada pedido
                System.out.println("Pedido agregado correctamente: " + pedido.getNombreCliente() + " - Plato: " + pedido.getPlato());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
        }
    }

    public void leerPedidos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoPedidos))) {
            String linea;
            System.out.println("\n>>> Pedidos registrados:");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer los pedidos: " + e.getMessage());
        }
    }
}