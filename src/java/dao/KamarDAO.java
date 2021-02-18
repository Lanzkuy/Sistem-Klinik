/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connection.DatabaseConnection;
import model.kamar_model;

/**
 *
 * @author Ajie
 */
public class KamarDAO {

    /**
     * @param args the command line arguments
     */
    
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public KamarDAO(){
        conn = DatabaseConnection.connection();
    }
    
    public ArrayList<kamar_model> getData(){
        System.out.println("--GET--");
        ArrayList<kamar_model> listKamar = new ArrayList<>();
        try{
            String query = "CALL GET_KAMAR";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                kamar_model km = new kamar_model();
                if(rs.getString("id_kamar").equals(""))
                {
                    km.setId_kamar(rs.getString("id_kamar"));
                }
                if(rs.getString("nama_ruang").equals(""))
                {
                    km.setNama_kamar(rs.getString("nama_ruang"));
                }
                if(rs.getString("no_kamar").equals(""))
                {
                    km.setNo_kamar(rs.getString("no_kamar"));
                }
                if(rs.getString("kelas").equals(""))
                {
                    km.setKelas(rs.getString("kelas"));
                }
                if(rs.getString("harga_perhari").equals(""))
                {
                    km.setHarga_perhari(rs.getDouble("harga_perhari"));
                }
                if(rs.getString("des_kamar").equals(""))
                {
                    km.setDes_kamar(rs.getString("des_kamar"));
                }
                if(rs.getString("kapasitas").equals(""))
                {
                    km.setKapasitas(rs.getInt("kapasitas"));
                }
                if(rs.getString("terisi").equals(""))
                {
                    km.setTerisi(rs.getInt("terisi"));
                }
                if(rs.getString("status").equals(""))
                {
                    km.setStatus(rs.getString("status"));
                }
                listKamar.add(km);
            }
        }
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listKamar;
    }
    
    public void add(kamar_model km , String page){
        System.out.println("--ADD/UPDATE--");
        String query = null;
        if (page.equals("update")) {
            query = "CALL UPDATE_KAMAR(?,?,?,?,?,?,?,?,?)";
        }
        else if (page.equals("add")) {
            query = "CALL INSERT_KAMAR(?,?,?,?,?,?,?,?,?)";
        }
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1, km.getNama_kamar());
            ps.setString(2, km.getNo_kamar());
            ps.setString(3, km.getKelas());
            ps.setDouble(4, km.getHarga_perhari());
            ps.setString(5, km.getDes_kamar());
            ps.setInt(6, km.getKapasitas());
            ps.setInt(7, km.getTerisi());
            ps.setString(8, km.getStatus());
            ps.setString(9, km.getId_kamar());            
            ps.executeUpdate();
            System.out.println("Add/Update Data Success");
        }
        catch(SQLException e){
            System.out.println("Save Data Error : "+e);
        }
        finally{
            try{
                ps.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public String GenerateId(){
        String query = "SELECT id_kamar FROM kamar ORDER BY id_kamar DESC LIMIT 1";
        String id = "KMR001";
        try{
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString("id_kamar");
                String zero = "";
                String strId = lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId = Integer.valueOf(strId)+1;
                int idLength = String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero += "0";
                }
                id = String.valueOf(strId);
                id = "KMR"+zero+numId;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return id;
    }
    
    public void deleteData(String id){
        System.out.println("--DELETE--");
        try{
            String query = "CALL DELETE_KAMAR(?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
            System.out.println("Berhasil Hapus Data");
        }
        catch(SQLException e){
            System.out.println("Gagal Hapus Data : "+e);
        }
    }
    
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        KamarDAO kd = new KamarDAO();
        kamar_model km = new kamar_model();
        km.setId_kamar(kd.GenerateId());
        km.setNama_kamar("Kamboja");
        km.setNo_kamar("001");
        km.setKelas("VIP");
        km.setHarga_perhari(10000);
        km.setDes_kamar("Tidak Ada");
        km.setKapasitas(2);
        km.setTerisi(1);
        km.setStatus("OK");
        kd.add(km, "add");
        System.out.println(kd.getData());
    }
    
}
