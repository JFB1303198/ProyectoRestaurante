
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Cliente;

public class ClienteDao {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public Cliente clienteBuscarPorId(String dni){
        Cliente cli = new Cliente();
        String sql = "SELECT * FROM cliente WHERE dni = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            rs= ps.executeQuery();
            if (rs.next()) {
                cli.setId(rs.getString("dni"));
                cli.setNombre(rs.getString("nombre"));                
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cli;
    }
    
    public boolean clienteRegistrar(Cliente reg){
        String sql = "INSERT INTO cliente (dni, nombre) VALUES (?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getId());
            ps.setString(2, reg.getNombre());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
