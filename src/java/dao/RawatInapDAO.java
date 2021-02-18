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
import koneksi.DatabaseConnection;
import model.rawat_inap_model;

/**
 *
 * @author Ajie
 */
public class RawatInapDAO {

    /**
     * @param args the command line arguments
     */
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public RawatInapDAO(){
        conn = DatabaseConnection.connection();
    }
    
    public ArrayList<rawat_inap_model> getData(){
        System.out.println("--GET--");
        ArrayList<rawat_inap_model> listInap = new ArrayList<>();
        try{
            String query = "CALL GET_RAWATINAP";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                rawat_inap_model rim = new rawat_inap_model();
                if(rs.getString("id_rawat").equals(""))
                {
                    rim.setId_rawat(rs.getString("id_rawat"));
                }
                if(rs.getString("id_pasien").equals(""))
                {
                    rim.setId_pasien(rs.getString("id_pasien"));
                }
                if(rs.getString("id_kamar").equals(""))
                {
                    rim.setId_kamar(rs.getString("id_kamar"));
                }
                if(rs.getString("nama_ruang").equals(""))
                {
                    rim.setNama_ruang(rs.getString("nama_ruang"));
                }
                if(rs.getString("tgl_cekin").equals(""))
                {
                    rim.setTgl_checkin(rs.getString("tgl_cekin"));
                }
                if(rs.getString("tgl_cekout").equals(""))
                {
                    rim.setTgl_checkout(rs.getString("tgl_cekout"));
                }
                if(rs.getString("keterangan").equals(""))
                {
                    rim.setKeterangan(rs.getString("keterangan"));
                }
                listInap.add(rim);
            }
            System.out.println("GET data berhasil..");
        }
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listInap;
    }
    
    public String GenerateId(){
        String query = "SELECT id_rawat FROM rawat_inap ORDER BY id_rawat DESC LIMIT 1";
        String id = "RI001";
        try{
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString("id_rawat");
                String zero = "";
                String strId = lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId = Integer.valueOf(strId)+1;
                int idLength = String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero += "0";
                }
                id = String.valueOf(strId);
                id = "RI"+zero+numId;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return id;
    }
    
    public void add(rawat_inap_model rim , String page){
        System.out.println("--ADD/UPDATE--");
        String query = null;
        if (page.equals("update")) {
            query = "CALL UPDATE_RAWATINAP(?,?,?,?,?,?,?)";
        }
        else if (page.equals("add")) {
            query = "CALL INSERT_RAWATINAP(?,?,?,?,?,?,?)";
        }
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1, rim.getId_pasien());
            ps.setString(2, rim.getId_kamar());
            ps.setString(3, rim.getNama_ruang());
            ps.setString(4, rim.getTgl_checkin());
            ps.setString(5, rim.getTgl_checkout());
            ps.setString(6, rim.getKeterangan());
            ps.setString(7, rim.getId_rawat());
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
    
    public void deleteData(String id){
        System.out.println("--DELETE--");
        try{
            String query = "CALL DELETE_RAWATINAP(?)";
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
        RawatInapDAO rid = new RawatInapDAO();
        rawat_inap_model rim = new rawat_inap_model();
        rim.setId_rawat(rid.GenerateId());
        rim.setId_pasien("P002");
        rim.setId_kamar("KMR002");
        rim.setNama_ruang("Kamboja");
        rim.setTgl_checkin("2021-02-05");
        rim.setTgl_checkout("2021-03-05");
        rim.setKeterangan("Tidak Ada");
        rid.add(rim, "add");
        System.out.println(rid.getData());
    }
    
}
