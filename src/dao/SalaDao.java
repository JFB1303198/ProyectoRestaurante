
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Sala;

public class SalaDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean salaRegistrar(Sala sala){
        String sql = "INSERT INTO sala(nombre, mesas) VALUES (?,?)";
        try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setString(1, sala.getNombre());
           ps.setInt(2, sala.getMesas());
           ps.execute();
           return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public List salaListar(){
        List<Sala> s_lista = new ArrayList();
        String sql = "SELECT * FROM sala";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Sala sl = new Sala();
                sl.setId(rs.getInt("id"));
                sl.setNombre(rs.getString("nombre"));
                sl.setMesas(rs.getInt("mesas"));
                s_lista.add(sl);
            }            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return s_lista;
    }
    
    public boolean salaEliminar(int id){
        String sql = "DELETE FROM sala WHERE id = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public boolean salaModificar(Sala sala){
        String sql = "UPDATE sala SET nombre=?, mesas=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sala.getNombre());
            ps.setInt(2, sala.getMesas());
            ps.setInt(3, sala.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
}
