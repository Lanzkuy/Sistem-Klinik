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
import model.bayar_obat_model;
/**
 *
 * @author Farhan Bhezni
 */
public class BayarObatDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public BayarObatDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<bayar_obat_model> getData() {
        ArrayList<bayar_obat_model> listbayarobat=new ArrayList<>();
        try{
            System.out.println("-GET-");
            String query="SELECT *FROM pembelian_obat ORDER BY id_pembelian";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                bayar_obat_model bom = new bayar_obat_model();
                if (!rs.getString("id_pembelian").equals("")) {
                    bom.setId_pembayaran(rs.getString("id_)pembayaran"));
                }
                if (!rs.getString("tgl_pembayaran").equals("")) {
                    bom.setTgl_pembayaran(rs.getString("tgl_pembayaran"));
                }
                if (!rs.getString("id_pasien").equals("")) {
                    bom.setId_pasien(rs.getString("id_pasien"));
                }
                if (!rs.getString("id_resep").equals("")) {
                    bom.setId_resep(rs.getString("id_resep"));
                }
                if (!rs.getString("jenis_pembayaran").equals("")) {
                    bom.setJenis_pembayaran(rs.getString("jenis_pembayaran"));
                }
                if (!rs.getString("user_id").equals("")) {
                    bom.setUser_id(rs.getString("user_id"));
                }
                listbayarobat.add(bom);
            }
            System.out.println("Get Data Success");
            System.out.println("--------------------------");
        }

        catch (SQLException e)
        {
            System.out.println(e);
        }
        return listbayarobat;

    }

    public void save(bayar_obat_model bom, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="update pembelian_obat set tgl_pembayaran=?, id_pasien=?, id_resep=?, jenis_pembayaran=?, user_id=? where id_pembelian=?";
        }
        else if(page.equals("insert"))
        {
            query="insert into pembelian_obat (tgl_pembayaran, id_pasien, id_resep, jenis_pembayaran, user_id, created_at, delete_at ) values (?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, bom.getTgl_pembayaran());
            ps.setString(2, bom.getId_pasien());
            ps.setString(3, bom.getId_resep());
            ps.setString(4, bom.getJenis_pembayaran());
            ps.setString(5, bom.getUser_id());
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
    public void delete(String id_pembelian){
            String sqlHapus = "DELETE FROM bayar_obat WHERE id_pembelian=?";
            try{
                ps = conn.prepareStatement(sqlHapus);
                ps.setString(1, id_pembelian);
                ps.executeUpdate();
                System.out.println("Delete Data Success");
            }
            catch(SQLException e){
                System.out.println("Delete Data Error : " + e);
            }
        }


    public static void main(String[] args) {
        BayarObatDAO bod = new BayarObatDAO();
        bayar_obat_model pom = new bayar_obat_model();
        System.out.println(bod.getData());
    }

}
