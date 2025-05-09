package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Plato;

public class PlatoDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    // Registrar un nuevo plato
    public boolean platoRegistrar(Plato plato) {
        String sql = "INSERT INTO plato(nombre, precio, fecha, activo) VALUES (?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, plato.getNombre());
            ps.setDouble(2, plato.getPrecio());
            ps.setString(3, plato.getFecha());
            ps.setBoolean(4, plato.getActivo()); // CORREGIDO
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    // Obtener lista de platos por fecha
    public List<Plato> platoListarPorFecha(String valor, String fecha) {
        List<Plato> p_lista = new ArrayList<>();
        String sql = "SELECT * FROM plato WHERE fecha = ? AND activo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setBoolean(2, Boolean.parseBoolean(valor)); // CORREGIDO
            rs = ps.executeQuery();
            while (rs.next()) {
                Plato plato = new Plato();
                plato.setId(rs.getInt("id"));
                plato.setNombre(rs.getString("nombre"));
                plato.setPrecio(rs.getDouble("precio"));
                plato.setFecha(rs.getString("fecha"));
                plato.setActivo(rs.getBoolean("activo")); // CORREGIDO
                p_lista.add(plato);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return p_lista;
    }

    // Obtener lista de platos activos
    public List<Plato> platoListarActivo() {
        List<Plato> p_lista = new ArrayList<>();
        String sql = "SELECT * FROM plato WHERE activo = 1";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Plato plato = new Plato();
                plato.setId(rs.getInt("id"));
                plato.setNombre(rs.getString("nombre"));
                plato.setPrecio(rs.getDouble("precio"));
                plato.setFecha(rs.getString("fecha"));
                plato.setActivo(rs.getBoolean("activo")); // CORREGIDO
                p_lista.add(plato);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return p_lista;
    }

    // Eliminar un plato
    public boolean platoEliminar(int id) {
        String sql = "DELETE FROM plato WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    // Modificar un plato
    public boolean platoModificar(Plato plato) {
        String sql = "UPDATE plato SET nombre = ?, precio = ?, fecha = ?, activo = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, plato.getNombre());
            ps.setDouble(2, plato.getPrecio());
            ps.setString(3, plato.getFecha());
            ps.setBoolean(4, plato.getActivo()); // CORREGIDO
            ps.setInt(5, plato.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}