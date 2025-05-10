
package dao;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.filechooser.FileSystemView;
import modelo.DetallePedido;
import modelo.Pedido;

public class PedidoDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    boolean r;
    
    public int IdPedido(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM pedido";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
    
    public int verificarStado(int mesa, int id_sala){
        int id_pedido = 0;
        String sql = "SELECT id FROM pedido WHERE num_mesa=? AND id_sala=? AND estado = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, mesa);
            ps.setInt(2, id_sala);
            ps.setString(3, "PENDIENTE");
            rs = ps.executeQuery();
            if(rs.next()){
                id_pedido = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id_pedido;
    }
    
    public boolean RegistrarPedido(Pedido ped){
        String sql = "INSERT INTO pedido (id_usuario, id_sala, id_cliente, "
                + "num_mesa, total, estado) VALUES (?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ped.getId_usuario());
            ps.setInt(2, ped.getId_sala());
            ps.setString(3, ped.getId_cliente());
            ps.setInt(4, ped.getNum_mesa());
            ps.setDouble(5, ped.getTotal());
            ps.setString(6, "PENDIENTE");
            r = ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    
    public boolean RegistrarDetalle(DetallePedido det){
       String sql = "INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, comentario) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, det.getId_pedido());
            ps.setInt(2, det.getId_plato());
            ps.setInt(3, det.getCantidad());
            ps.setString(4, det.getComentario());
            r = ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
    }
    
    public List verPedidoDetalle(int id_pedido){
       List<DetallePedido> Lista = new ArrayList();
       String sql = "SELECT d.id_pedido, d.id_plato, pl.nombre, pl.precio, d.cantidad, "
               + "d.comentario FROM pedido p INNER JOIN detalle_pedido d ON p.id = d.id_pedido "
               + "INNER JOIN plato pl on d.id_plato = pl.id  WHERE p.id = ?";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setInt(1, id_pedido);
           rs = ps.executeQuery();
           while (rs.next()) {               
               DetallePedido det = new DetallePedido();
               det.setId_pedido(rs.getInt("id_pedido"));
               det.setId_plato(rs.getInt("id_plato"));
               det.setPlatoNombre(rs.getString("nombre"));
               det.setPlatoPrecio(rs.getFloat("precio"));
               det.setCantidad(rs.getInt("cantidad"));
               det.setComentario(rs.getString("comentario"));
               Lista.add(det);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Lista;
   }
    
    public Pedido verPedido(int id_pedido){
        Pedido ped = new Pedido();
       String sql = "SELECT p.*, s.nombre FROM pedido p INNER JOIN sala s ON p.id_sala = s.id WHERE p.id = ?";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setInt(1, id_pedido);
           rs = ps.executeQuery();
            if (rs.next()) {               
               
               ped.setId(rs.getInt("id"));
               ped.setId_usuario(rs.getInt("id_usuario"));
               ped.setId_sala(rs.getInt("id_sala"));
               ped.setId_cliente(rs.getString("id_cliente"));               
               ped.setNum_mesa(rs.getInt("num_mesa"));
               ped.setFecha(rs.getString("fecha"));               
               ped.setTotal(rs.getDouble("total"));
               ped.setEstado(rs.getString("estado"));
               ped.setNombreSala(rs.getString("nombre"));
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ped;
   }
    
    public boolean finalizarPedido(int id_pedido){
        String sql = "UPDATE pedido set estado = \"FINALIZADO\" WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_pedido);
            r = ps.execute();            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return r;
   }
    
    public void pdfPedido(int id_pedido) {
        String fechaPedido = null, usuario = null, 
                sala = null, num_mesa = null;
        double total = 0;
        try {
            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File salida = new File(url + File.separator + "pedido.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance(getClass().getResource("/Img/logo.png"));
            //Fecha
            String informacion = "SELECT p.id, p.num_mesa, p.fecha, c.nombre as cliente, s.nombre FROM pedido p INNER JOIN sala s ON p.id_sala = s.id INNER JOIN cliente c ON p.id_cliente = c.dni WHERE p.id = ?";
            try {
                ps = con.prepareStatement(informacion);
                ps.setInt(1, id_pedido);
                rs = ps.executeQuery();
                if (rs.next()) {
                    num_mesa = rs.getString("num_mesa");
                    fechaPedido = rs.getString("fecha");
                    usuario = rs.getString("cliente");
                    sala = rs.getString("nombre");
                    //total = rs.getString("total");
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnWidthsEncabezado = new float[]{20f, 20f, 60f, 60f};
            Encabezado.setWidths(columnWidthsEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            Encabezado.addCell(img);
            Encabezado.addCell("");
            //info empresa
            String config = "SELECT * FROM restaurante";
            String mensaje = "";
            try {
                con = cn.getConnection();
                ps = con.prepareStatement(config);
                rs = ps.executeQuery();
                if (rs.next()) {
                    mensaje = rs.getString("mensaje");
                    Encabezado.addCell("Ruc:    " + rs.getString("ruc") 
                            + "\nNombre: " + rs.getString("nombre") 
                            + "\nTeléfono: " + rs.getString("telefono") 
                            + "\nDirección: " + rs.getString("direccion")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            //
            Paragraph info = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            info.add("Atendido: " + usuario 
                    + "\nN° Pedido: " + id_pedido 
                    + "\nFecha: " + fechaPedido
                    + "\nSala: " + sala
                    + "\nN° Mesa: " + num_mesa
            );
            Encabezado.addCell(info);
            
            doc.add(Encabezado);
            doc.add(Chunk.NEWLINE);
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
            tabla.setWidths(columnWidths);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell c1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell c2 = new PdfPCell(new Phrase("Plato.", negrita));
            PdfPCell c3 = new PdfPCell(new Phrase("P. unt.", negrita));
            PdfPCell c4 = new PdfPCell(new Phrase("P. Total", negrita));
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);
            String product = "SELECT d.id_pedido, d.id_plato, pl.nombre, pl.precio, d.cantidad, d.comentario FROM pedido p INNER JOIN detalle_pedido d ON p.id = d.id_pedido INNER JOIN plato pl on d.id_plato = pl.id  WHERE p.id = ?";
            try {
                ps = con.prepareStatement(product);
                ps.setInt(1, id_pedido);
                rs = ps.executeQuery();
                while (rs.next()) {
                    double subTotal = rs.getInt("cantidad") * rs.getDouble("precio");
                    tabla.addCell(rs.getString("cantidad"));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("precio"));
                    tabla.addCell(String.valueOf(subTotal));
                    total += subTotal;
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(tabla);
            Paragraph agra = new Paragraph();
            agra.add(Chunk.NEWLINE);
            agra.add("Total S/: " + total);
            agra.setAlignment(Element.ALIGN_RIGHT);
            doc.add(agra);
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelacion \n\n");
            firma.add("------------------------------------\n");
            firma.add("Firma \n");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);
            Paragraph gr = new Paragraph();
            gr.add(Chunk.NEWLINE);
            gr.add(mensaje);
            gr.setAlignment(Element.ALIGN_CENTER);
            doc.add(gr);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public boolean actualizarEstado (int id_pedido){
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "FINALIZADO");
            ps.setInt(2, id_pedido);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List listarPedidos(){
       List<Pedido> Lista = new ArrayList();
       String sql = "SELECT p.*, s.nombre FROM pedido p INNER JOIN sala s ON p.id_sala = s.id ORDER BY p.fecha DESC";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Pedido ped = new Pedido();
               ped.setId(rs.getInt("id"));
               ped.setNombreSala(rs.getString("nombre"));
               ped.setNum_mesa(rs.getInt("num_mesa"));
               ped.setFecha(rs.getString("fecha"));
               ped.setTotal(rs.getDouble("total"));
               ped.setId_usuario(rs.getInt("id_usuario"));
               ped.setEstado(rs.getString("estado"));
               Lista.add(ped);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Lista;
   }
}
