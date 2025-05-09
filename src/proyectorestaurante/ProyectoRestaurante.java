package proyectorestaurante;

import dao.ClienteArchivo;
import dao.PedidoArchivo;
import dao.PlatoArchivo;
import modelo.Cliente;
import modelo.Pedido;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProyectoRestaurante {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- REGISTRO DE CLIENTE ---
        System.out.println("===== REGISTRO DE CLIENTE =====");

        int idCliente = 0;
        boolean idValido = false;

        while (!idValido) {
            try {
                System.out.print("Ingrese ID del cliente: ");
                idCliente = Integer.parseInt(scanner.nextLine().trim());
                if (idCliente <= 0) {
                    System.out.println("❌ El ID debe ser un numero positivo.");
                } else {
                    idValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ ID invalido. Debe ser un numero entero.");
            }
        }

        String nombreCliente;
        while (true) {
            System.out.print("Ingrese nombre del cliente: ");
            nombreCliente = scanner.nextLine().trim();
            if (nombreCliente.isEmpty()) {
                System.out.println("❌ El nombre no puede estar vacio.");
            } else {
                break;
            }
        }

        String correoCliente;
        while (true) {
            System.out.print("Ingrese correo del cliente: ");
            correoCliente = scanner.nextLine().trim();
            if (!esCorreoValido(correoCliente)) {
                System.out.println("❌ Correo inválido. Debe ser un correo electronico valido.");
            } else {
                break;
            }
        }

        String telefonoCliente;
        while (true) {
            System.out.print("Ingrese numero de telefono (9 digitos): ");
            telefonoCliente = scanner.nextLine().trim();
            if (!telefonoCliente.matches("\\d{9}")) {
                System.out.println("❌ Telefono invalido. Debe contener exactamente 9 digitos numericos.");
            } else {
                break;
            }
        }

        Cliente cliente = new Cliente(idCliente, nombreCliente, correoCliente, telefonoCliente);
        ClienteArchivo clienteArchivo = new ClienteArchivo();
        clienteArchivo.guardarCliente(cliente);
        clienteArchivo.leerClientes();

        // --- REGISTRO DE PEDIDO ---
        System.out.println("\n===== REGISTRO DE PEDIDO =====");

        // Pedir nombre y precio del plato
        System.out.print("Ingrese el nombre del plato: ");
        String nombrePlato = scanner.nextLine().trim();

        double precioPlato = 0.0;
        boolean precioValido = false;
        while (!precioValido) {
            try {
                System.out.print("Ingrese el precio del plato: ");
                precioPlato = Double.parseDouble(scanner.nextLine().trim());
                if (precioPlato <= 0) {
                    System.out.println("❌ El precio debe ser un numero positivo.");
                } else {
                    precioValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Precio invalido. Debe ser un numero.");
            }
        }

        Pedido pedido = new Pedido(nombreCliente, nombrePlato, precioPlato);  // Guardamos el nombre y precio del plato
        PedidoArchivo pedidoArchivo = new PedidoArchivo();
        pedidoArchivo.guardarPedido(pedido);
        pedidoArchivo.leerPedidos();

        // --- REGISTRO DE PLATOS DEL DÍA ---
        System.out.println("\n===== REGISTRO DE PLATOS DEL DIA =====");
        PlatoArchivo platoArchivo = new PlatoArchivo();
        platoArchivo.exportarPlatos(nombrePlato, precioPlato);  // Guardamos el nuevo plato en el archivo
        platoArchivo.importarPlatos();

        System.out.println("\n>>> TODOS LOS DATOS HAN SIDO PROCESADOS CORRECTAMENTE <<<");
    }

    private static boolean esCorreoValido(String correo) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}