package dao;
import java.io.*;
import java.util.*;

public class PlatoArchivo {
    private final String archivoPlatos = "platos.csv";

    public void exportarPlatos(String nombrePlato, double precioPlato) {
        try {
            File file = new File(archivoPlatos);
            // Si el archivo no existe, lo creamos
            if (!file.exists()) {
                System.out.println("El archivo de platos no existe, se creará uno nuevo.");
                file.createNewFile();
                // Agregar la cabecera al archivo
                try (PrintWriter writer = new PrintWriter(new FileWriter(archivoPlatos))) {
                    writer.println("ID,Nombre,Precio");
                }
            }

            // Agregar el nuevo plato al archivo
            try (PrintWriter writer = new PrintWriter(new FileWriter(archivoPlatos, true))) {
                // Aquí se agrega el nuevo plato con un ID incremental
                int nuevoId = obtenerNuevoId(); // Método para obtener el ID siguiente
                writer.println(nuevoId + "," + nombrePlato + "," + precioPlato);
                System.out.println("Plato agregado correctamente: " + nombrePlato + " - Precio: S/" + precioPlato);
            }
        } catch (IOException e) {
            System.out.println("Error al exportar platos: " + e.getMessage());
        }
    }

    private int obtenerNuevoId() {
        int nuevoId = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoPlatos))) {
            String linea;
            boolean primeraLinea = true; // Variable para omitir la primera línea (cabecera)
            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; // Omitimos la cabecera
                    continue;
                }

                // Asegurarnos de que la línea no esté vacía y tenga el formato correcto
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    try {
                        int id = Integer.parseInt(datos[0]);
                        if (id >= nuevoId) {
                            nuevoId = id + 1;  // Incrementa el ID
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Línea con formato incorrecto: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de platos: " + e.getMessage());
        }
        return nuevoId;
    }

    public void importarPlatos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoPlatos))) {
            String linea;
            boolean primeraLinea = true; // Omitir la cabecera
            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; // Omitir la cabecera
                    continue;
                }
                
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    System.out.println("Plato: " + datos[1] + " - Precio: S/" + datos[2]);
                } else {
                    System.out.println("Línea con formato incorrecto: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al importar platos: " + e.getMessage());
        }
    }
}