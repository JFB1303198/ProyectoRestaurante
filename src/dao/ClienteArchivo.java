package dao;

import java.io.*;
import modelo.Cliente;

public class ClienteArchivo {
    private final String archivoClientes = "clientes.txt";  // Ruta del archivo de clientes

    // Método para guardar un cliente en el archivo
    public void guardarCliente(Cliente cliente) {
        try {
            File file = new File(archivoClientes);
            // Si el archivo no existe, lo creamos
            if (!file.exists()) {
                System.out.println("El archivo de clientes no existe, se creará uno nuevo.");
                file.createNewFile();
            }

            // Guardar cliente en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoClientes, true))) {
                writer.write(cliente.getId() + "," + cliente.getNombre() + "," + cliente.getCorreo() + "," + cliente.getTelefono());
                writer.newLine();  // Añadimos un salto de línea después de cada cliente
                System.out.println("Cliente agregado correctamente: " + cliente.getNombre());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
        }
    }

    // Método para leer los clientes desde el archivo
    public void leerClientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoClientes))) {
            String linea;
            System.out.println("\n========= LISTA DE CLIENTES =========");
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");  // Separamos los datos por comas

                // Verificamos que la línea tenga 4 campos (ID, Nombre, Correo, Teléfono)
                if (datos.length == 4) {
                    try {
                        int id = Integer.parseInt(datos[0].trim());  // Parseamos el ID como número
                        String nombre = datos[1].trim();
                        String correo = datos[2].trim();
                        String telefono = datos[3].trim();

                        // Mostramos los detalles del cliente
                        System.out.println("Cliente " + id + ":");
                        System.out.println("ID: " + id + " - Nombre: " + nombre + " - Correo: " + correo + " - Teléfono: " + telefono);
                        System.out.println("-------------------------------");

                    } catch (NumberFormatException e) {
                        // Si no se puede convertir el ID a número, mostramos un error
                        System.out.println("❌ Línea con formato incorrecto (ID no válido): " + linea);
                    }
                } else {
                    // Si la línea no tiene 4 campos, mostramos un mensaje de error
                    System.out.println("❌ Línea con formato incorrecto: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer los clientes: " + e.getMessage());
        }
    }
}