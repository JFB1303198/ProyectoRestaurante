
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Restaurante;

public class RestauranteDao {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public boolean restauranteModificarDatos(Restaurante rest){
        String sql = "UPDATE restaurante SET ruc=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, rest.getRuc());
            ps.setString(2, rest.getNombre());
            ps.setString(3, rest.getTelefono());
            ps.setString(4, rest.getDireccion());
            ps.setString(5, rest.getMensaje());
            ps.setInt(6, rest.getId());
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
    
    public Restaurante restauranteConsultarDatos(){
        Restaurante rest = new Restaurante();
        String sql = "SELECT * FROM restaurante";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs= ps.executeQuery();
            if (rs.next()) {
                rest.setId(rs.getInt("id"));
                rest.setRuc(rs.getString("ruc"));
                rest.setNombre(rs.getString("nombre"));
                rest.setTelefono(rs.getString("telefono"));
                rest.setDireccion(rs.getString("direccion"));
                rest.setMensaje(rs.getString("mensaje"));                
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return rest;
    }
}
