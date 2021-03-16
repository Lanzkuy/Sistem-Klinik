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
import java.util.Date;
import model.pendaftaran_model;

/**
 *
 * @author M.Yazid
 */
public class PendaftaranDao {
        private final Connection koneksi;
        private PreparedStatement preSmt;
        private ResultSet rs;
        // tanggal
        private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        public PendaftaranDao(){
            koneksi = DatabaseConnection.connection();
        }

        public ArrayList<pendaftaran_model> getAll(){
            ArrayList<pendaftaran_model> list = new ArrayList<>();
            try{
                String sql = "CALL GET_PENDAFTARAN()";
                preSmt = koneksi.prepareStatement(sql);
                rs = preSmt.executeQuery();
                System.out.println("---- getting data ----");
                while(rs.next()){
                    pendaftaran_model model = new pendaftaran_model();        
                    model.setNo_antrian(rs.getString("no_antrian")); 
                    model.setId_pasien(rs.getString("id_pasien"));
                    model.setId_poli(rs.getString("id_poli"));
                    model.setTgl_daftar(rs.getString("tgl_daftar"));
                    model.setKeterangan(rs.getString("keterangan"));
                    model.setUser_id(rs.getString("user_id"));          
                    list.add(model);
                    
                    System.out.println("     id : "+rs.getString("no_antrian"));
                }
            }
            catch(SQLException e){
                System.out.println("Kesalahan mengambil data : " + e);
            }
            return list;
        }

        public pendaftaran_model getRecordById(String no, String poli, String tgl){
            pendaftaran_model model = new pendaftaran_model();
            String sqlSearch = "SELECT * FROM pendaftaran WHERE no_antrian=? AND id_poli=? AND tgl_daftar=?";
            try {
                preSmt = koneksi.prepareStatement(sqlSearch);
                preSmt.setString(1, no);
                preSmt.setString(2, poli);
                preSmt.setString(3, tgl);
                rs = preSmt.executeQuery();
                if (rs.next()){  
                    model.setNo_antrian(rs.getString("no_antrian")); 
                    model.setId_pasien(rs.getString("id_pasien"));
                    model.setId_poli(rs.getString("id_poli"));
                    model.setTgl_daftar(rs.getString("tgl_daftar"));
                    model.setKeterangan(rs.getString("keterangan"));
                    model.setUser_id(rs.getString("user_id"));     
                }
            }
            catch (SQLException se){
                System.out.println("kesalahan pada : " + se);
            }
            return model;
        }
        

        /*public void simpanData(pendaftaran_model model, String page){
            String sqlSimpan = null;
            if (page.equals("edit")){
                sqlSimpan = "CALL UPDATE_PENDAFTARAN(?,?,?,?,?,?,?)";
            }
            else if (page.equals("tambah")){
                sqlSimpan = "CALL INSERT_PENDAFTARAN(?,?,?,?,?,?)";
            }
            System.out.println("---- " + (page == "tambah" ? "adding data" : "updating data") + " ----");
            try {
                String date = java.time.LocalDate.now().toString();
                preSmt = koneksi.prepareStatement(sqlSimpan);

                preSmt.setString(1, id);
                preSmt.setString(2, model.getId_pasien());
                preSmt.setString(3, model.getId_poli());
                preSmt.setString(4, model.getTglDaftar().equals("") ? date  : model.getTglDaftar());
                preSmt.setString(5, model.getKeterangan());
                preSmt.setString(6, model.getUserId());
                if (page == "edit") {
                    preSmt.setInt(7, model.getDeleted());
                }
                preSmt.executeUpdate();
                System.out.println(page == "tambah" ? "success add data" : "success update data");

            }
            catch (SQLException se){
                System.out.println("ada kesalahan : " + se);
            }
        }*/

        public void hapusData(String id){
            String sqlHapus = "CALL DELETE_PENDAFTARAN(?)";
            System.out.println("---- deleting data ----");
            try{
                preSmt = koneksi.prepareStatement(sqlHapus);
                preSmt.setString(1, id);
                preSmt.executeUpdate();
                System.out.println("success delete data, id : "+id);
            }
            catch(SQLException e){
                System.out.println("kesalahan hapus data: " + e);
            }
        }


        public static void main(String[] args) {
            PendaftaranDao dao = new PendaftaranDao();
            pendaftaran_model model = new pendaftaran_model();
            model.setNo_antrian(""); 
            model.setId_pasien("PS001");
            model.setId_poli("PLU");
            model.setTgl_daftar("");
            model.setKeterangan("Sakit Gigi");
            model.setUser_id("US001");
            //dao.simpanData(model,"tambah");
            //dao.hapusData("AA4");
            System.out.println(dao.getAll());
        }
}
