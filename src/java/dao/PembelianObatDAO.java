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
import model.pembelian_obat_model;
/**
 *
 * @author Farhan Bhezni
 */
public class PembelianObatDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public PembelianObatDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<pembelian_obat_model> getData() {
        ArrayList<pembelian_obat_model> listpembelianobat=new ArrayList<>();
        try{
            System.out.println("-GET-");
            String query="SELECT p.*,o.nama_obat, s.nama_supplier FROM pembelian_obat p "
                    + "INNER JOIN obat o on p.id_obat=o.id_obat INNER JOIN supplier s on p.id_supplier=s.id_supplier "
                    + "WHERE p.deleted_at is null ORDER BY id_trans";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                pembelian_obat_model pom = new pembelian_obat_model();
                if (!rs.getString("id_trans").equals("")) {
                    pom.setId_trans(rs.getString("id_trans"));
                }
                if (!rs.getString("id_supplier").equals("")) {
                    pom.setId_supplier(rs.getString("id_supplier"));
                }
                if (!rs.getString("no_faktur").equals("")) {
                    pom.setNo_faktur(rs.getString("no_faktur"));
                }
                if (!rs.getString("tgl_faktur").equals("")) {
                    pom.setTgl_faktur(rs.getString("tgl_faktur"));
                }
                if (!rs.getString("id_obat").equals("")) {
                    pom.setId_obat(rs.getString("id_obat"));
                }
                if (!rs.getString("harga_beli").equals("")) {
                    pom.setHarga_beli(rs.getDouble("harga_beli"));
                }
                if (!rs.getString("jumlah").equals("")) {
                    pom.setJumlah(rs.getDouble("jumlah"));
                }
                if (!rs.getString("keterangan").equals("")) {
                    pom.setKeterangan(rs.getString("keterangan"));
                }
                if (!rs.getString("tgl_expired").equals("")) {
                    pom.setTgl_expired(rs.getString("tgl_expired"));
                }
                if (!rs.getString("id_user").equals("")) {
                    pom.setId_user(rs.getString("id_user"));
                }
                if (!rs.getString("nama_obat").equals("")) {
                    pom.setNama_obat(rs.getString("nama_obat"));
                }
                if (!rs.getString("nama_supplier").equals("")) {
                    pom.setNama_supplier(rs.getString("nama_supplier"));
                }
                listpembelianobat.add(pom);
            }
            System.out.println("Get Data Success");
        }

        catch (SQLException e)
        {
            System.out.println(e);
        }
        return listpembelianobat;

    }

    public void save(pembelian_obat_model pom, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="update pembelian_obat set id_supplier=?, no_faktur=?, tgl_faktur=?, id_obat=?, harga_beli=?, jumlah =?, keterangan=?, tgl_expired=?,id_user=? where id_trans=?";
        }
        else if(page.equals("insert"))
        {
            query="insert into pembelian_obat (id_supplier, no_faktur, tgl_faktur, id_obat, harga_beli, jumlah, keterangan, tgl_expired, id_user, id_trans) values (?,?,?,?,?,?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, pom.getId_supplier());
            ps.setString(2, pom.getNo_faktur());
            ps.setString(3, pom.getTgl_faktur());
            ps.setString(4, pom.getId_obat());
            ps.setDouble(5, pom.getHarga_beli());
            ps.setDouble(6, pom.getJumlah());
            ps.setString(7, pom.getKeterangan());
            ps.setString(8, pom.getTgl_expired());
            ps.setString(9, pom.getId_user());
            ps.setString(10, pom.getId_trans());
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
    
    public String GenerateID()
    {
        String query = "SELECT id_trans FROM pembelian_obat ORDER BY id_trans DESC LIMIT 1";
        String id="TR0001";
        try
        {
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String lastId=rs.getString("id_trans");
                String zero="";
                String strId=lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId=Integer.valueOf(strId)+1;
                int idLength=String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero+="0";
                }
                id=String.valueOf(strId);
                id="TR"+zero+numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    public void delete(String id_trans){
        System.out.println("-DELETE-");
        try{
            String sqlHapus = "UPDATE pembelian_obat SET deleted_at=CURRENT_TIMESTAMP WHERE id_trans=?";
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id_trans);
            ps.executeUpdate();
            System.out.println("Delete Data Success");
        }
        catch(SQLException e){
            System.out.println("Delete Data Error: " + e);
        }
    }

    public static void main(String[] args) {
        PembelianObatDAO pod = new PembelianObatDAO();
        pembelian_obat_model pom = new pembelian_obat_model();
        pom.setId_trans("TR23022101");
        pom.setHarga_beli(3500);
        pom.setId_obat("OBT0001");
        pom.setId_supplier("SP0001");
        pom.setId_user("US0001");
        pom.setJumlah(30);
        pom.setKeterangan("Test123");
        pom.setNo_faktur("231232");
        pom.setTgl_expired("30-07-23");
        pom.setTgl_faktur("23-02-21");
        pod.save(pom, "update");
        //pod.delete("TR23022101");
        System.out.println(pod.getData());
    }

}
