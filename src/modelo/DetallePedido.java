
package modelo;

public class DetallePedido {
    private int id_pedido;
    private int id_plato;         
    private int cantidad;
    private String comentario;
    private String platoNombre;
    private float platoPrecio;

    public DetallePedido(){}

    public DetallePedido(int id_pedido, int id_plato, int cantidad, String comentario) {
        this.id_pedido = id_pedido;
        this.id_plato = id_plato;
        this.cantidad = cantidad;
        this.comentario = comentario;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_plato() {
        return id_plato;
    }

    public void setId_plato(int id_plato) {
        this.id_plato = id_plato;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public String getPlatoNombre() {
        return platoNombre;
    }
    
    public void setPlatoNombre(String platoNombre){
        this.platoNombre = platoNombre;
    }

    public float getPlatoPrecio() {
        return platoPrecio;
    }

    public void setPlatoPrecio(float platoPrecio) {
        this.platoPrecio = platoPrecio;
    }
    
}
