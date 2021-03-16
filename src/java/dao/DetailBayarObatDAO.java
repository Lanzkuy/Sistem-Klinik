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
import model.detail_bayar_obat_model;
/**
 *
 * @author Farhan Bhezni
 */
public class DetailBayarObatDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public DetailBayarObatDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<detail_bayar_obat_model> getData() {
        ArrayList<detail_bayar_obat_model> listdetailbayarobat=new ArrayList<>();
        try{
            System.out.println("-GET-");
            String query="SELECT *FROM detail_bayar_obat ORDER BY id_pembayaran";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                detail_bayar_obat_model dbom = new detail_bayar_obat_model();
                if (!rs.getString("id_pembayaran").equals("")) {
                    dbom.setId_pembayaran(rs.getString("id_pembayaran"));
                }
                if (!rs.getString("id_obat").equals("")) {
                    dbom.setId_obat(rs.getString("id_obat"));
                }
                if (!rs.getString("harga").equals("")) {
                    dbom.setHarga(rs.getDouble("harga"));
                }
                if (!rs.getString("jumlah").equals("")) {
                    dbom.setJumlah(rs.getDouble("jumlah"));
                }
                listdetailbayarobat.add(dbom);
            }
            System.out.println("Get Data Success");
            System.out.println("--------------------------");
        }

        catch (SQLException e)
        {
            System.out.println(e);
        }
        return listdetailbayarobat;

    }

    public void save(detail_bayar_obat_model dbom, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="update pembelian_obat set id_obat=?, harga=?, jumlah=? where id_pembayaran=?";
        }
        else if(page.equals("insert"))
        {
            query="insert into pembelian_obat (id_obat, harga, jumlah) values (?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, dbom.getId_obat());
            ps.setString(2, dbom.getId_pembayaran());
            ps.setDouble(3, dbom.getJumlah());
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
    public void delete(String id_pembayaran){
            String sqlHapus = "DELETE FROM detail_bayar_obat WHERE id_pembayaran=?";
            try{
                ps = conn.prepareStatement(sqlHapus);
                ps.setString(1, id_pembayaran);
                ps.executeUpdate();
                System.out.println("Data Berhasil Dihapus");
            }
            catch(SQLException e){
                System.out.println("kesalahan hapus data: " + e);
            }
        }

    public static void main(String[] args) {
        DetailBayarObatDAO dbod = new DetailBayarObatDAO();
        detail_bayar_obat_model dbom = new detail_bayar_obat_model();
        System.out.println(dbod.getData());
    }

}
