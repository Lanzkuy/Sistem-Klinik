/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.obat_model;
/**
 *
 * @author Cahyo
 */
public class ObatDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ObatDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<obat_model> getData() {
        ArrayList<obat_model> listobat=new ArrayList<>();
        try{
            System.out.println("-GET-");
            String query="SELECT *FROM obat ORDER BY id_obat";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                obat_model om = new obat_model();
                if (rs.getString("id_obat").equals("")) {
                    om.setId_obat(rs.getString("id_obat"));
                }
                if (rs.getString("nama_obat").equals("")) {
                    om.setNama_obat(rs.getString("nama_obat"));
                }
                if (rs.getString("satuan").equals("")) {
                    om.setSatuan(rs.getString("satuan"));
                }
                if (rs.getString("stok").equals("")) {
                    om.setStok(rs.getDouble("stok"));
                }
                if (rs.getString("harga_jual").equals("")) {
                    om.setHarga_jual(rs.getDouble("harga_jual"));
                }
                if (rs.getString("no_faktur").equals("")) {
                    om.setNo_faktur(rs.getString("no_faktur"));
                }
                if (rs.getString("user_id").equals("")) {
                    om.setUser_id(rs.getString("user_id"));
                }
                listobat.add(om);
            }
            System.out.println("Get Data Success");
            System.out.println("--------------------------");
        }
        
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listobat;
    
    }
    //id_obat, nama_obat, satuan, stok, harga_jual, no_faktur, user_id
    
    public void save(obat_model om, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="update obat set nama_obat=?, satuan=?, stok=?, harga_jual=?, no_faktur=?, user_id=? where id_obat=?";
        }
        else if(page.equals("insert"))
        {
            query="insert into obat (nama_obat, satuan, stok, harga_jual, no_faktur, user_id, id_obat ) values (?,?,?,?,?,?,?)";
        }
        try
        {   
            ps=conn.prepareStatement(query);
            ps.setString(1, om.getNama_obat());
            ps.setString(2, om.getSatuan());
            ps.setDouble(3, om.getStok());
            ps.setDouble(4, om.getHarga_jual());
            ps.setString(5, om.getNo_faktur());
            ps.setString(6, om.getUser_id());
            ps.setString(7, om.getId_obat());
            ps.executeUpdate();
            System.out.println("Insert/Update Data Success");
        } 
        catch (SQLException e) 
        {
            System.out.println("Save Data Error : "+e);
        }
        finally
        {
            try
            {
                ps.close();
            }
            catch (SQLException e)
            {
              e.printStackTrace();
            }
        }
    }
    public void delete(String id_obat){
            String sqlHapus = "DELETE FROM obat WHERE id_obat=?";
            try{
                ps = conn.prepareStatement(sqlHapus);
                ps.setString(1, id_obat);
                ps.executeUpdate();
                System.out.println("Data Berhasil Dihapus");
            }
            catch(SQLException e){
                System.out.println("kesalahan hapus data: " + e);
            }
        }
    
    public static void main(String[] args) {
        ObatDAO od = new ObatDAO();
        obat_model om = new obat_model();
//        om.setNama_obat("Obat Mules");
//        om.setSatuan("12");
//        om.setStok(20);
//        om.setHarga_jual(2000);
//        om.setNo_faktur("F02");
//        om.setUser_id("U02");
//        om.setId_obat("OBH02");
        od.delete("OBH02");
        System.out.println(od.getData());
    }
}
