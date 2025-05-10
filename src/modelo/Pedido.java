
package modelo;

public class Pedido {
    private int id;
    private int id_usuario;
    private int id_sala;
    private String id_cliente;
    private int num_mesa;
    private String fecha;
    private double total;
    private String estado;
    private String nombreSala;

    public Pedido() {
    }

    public Pedido(int id, int id_usuario, int id_sala, String id_cliente, int num_mesa, String fecha, double total, String estado) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_sala = id_sala;
        this.id_cliente = id_cliente;
        this.num_mesa = num_mesa;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getNum_mesa() {
        return num_mesa;
    }

    public void setNum_mesa(int num_mesa) {
        this.num_mesa = num_mesa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }
    
}
