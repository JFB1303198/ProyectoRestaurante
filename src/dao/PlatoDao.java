
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
    
    public boolean platoRegistrar(Plato plato) {
        String sql = "INSERT INTO plato (nombre, precio, fecha, activo) VALUES (?,?,?,1)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, plato.getNombre());
            ps.setDouble(2, plato.getPrecio());
            ps.setString(3, plato.getFecha());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public List platoListarPorFecha(String valor, String fecha) {
        List<Plato> p_lista = new ArrayList();
        String sql = "SELECT * FROM plato WHERE fecha = ?";
        String consulta = "SELECT * FROM plato WHERE nombre LIKE '%"+valor+"%' AND fecha = ?";
        try {
            con = cn.getConnection();
            if(valor.equalsIgnoreCase("")){
                ps = con.prepareStatement(sql);
            }else{
                ps = con.prepareStatement(consulta);
            }
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            while (rs.next()) {
                Plato pl = new Plato();
                pl.setId(rs.getInt("id"));
                pl.setNombre(rs.getString("nombre"));
                pl.setPrecio(rs.getDouble("precio"));
                p_lista.add(pl);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return p_lista;
    }

    public List platoListarActivo() {
        List<Plato> p_lista = new ArrayList();
        String sql = "SELECT * FROM plato WHERE activo = 1";        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);            
            rs = ps.executeQuery();
            while (rs.next()) {
                Plato pl = new Plato();
                pl.setId(rs.getInt("id"));
                pl.setNombre(rs.getString("nombre"));
                pl.setPrecio(rs.getDouble("precio"));
                pl.setActivo(rs.getInt("activo"));
                p_lista.add(pl);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return p_lista;
    }
    
    public boolean platoEliminar(int id) {
        String sql = "DELETE FROM plato WHERE id = ?";
        try {
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
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public boolean platoModificar(Plato plato) {
        String sql = "UPDATE plato SET nombre=?, precio=?, activo=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, plato.getNombre());
            ps.setDouble(2, plato.getPrecio());
            ps.setInt(3, plato.getId());
            ps.setInt(4, plato.getActivo());
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
