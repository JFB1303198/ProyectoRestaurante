package modelo;

public class Pedido {
    private String nombreCliente;
    private String plato;
    private double precio;

    // Constructor con los parámetros correctos
    public Pedido(String nombreCliente, String plato, double precio) {
        this.nombreCliente = nombreCliente;
        this.plato = plato;
        this.precio = precio;
    }

    // Getters y Setters
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getPlato() {
        return plato;
    }

    public void setPlato(String plato) {
        this.plato = plato;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}