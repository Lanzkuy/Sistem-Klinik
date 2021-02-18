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
import model.rekam_medik_model;

/**
 *
 * @author Ajie
 */
public class RekamMedikDAO {

    /**
     * @param args the command line arguments
     */
    
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public RekamMedikDAO(){
        conn = DatabaseConnection.connection();
    }
    
    public ArrayList<rekam_medik_model> getData(){
        System.out.println("--GET--");
        ArrayList<rekam_medik_model> listMedik = new ArrayList<>();
        try{
            String query = "CALL GET_REKAMMEDIK";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                rekam_medik_model rmm = new rekam_medik_model();
                if(rs.getString("id_pasien").equals(""))
                {
                    rmm.setId_pasien(rs.getString("id_pasien"));
                }
                if(rs.getString("tgl_daftar").equals(""))
                {
                    rmm.setTgl_daftar(rs.getString("tgl_daftar"));
                }
                if(rs.getString("id_poli").equals(""))
                {
                    rmm.setId_poli(rs.getString("id_poli"));
                }
                if(rs.getString("tek_darah").equals(""))
                {
                    rmm.setTes_darah(rs.getString("tek_darah"));
                }
                if(rs.getString("berat").equals(""))
                {
                    rmm.setBerat(rs.getDouble("berat"));
                }
                if(rs.getString("tinggi").equals(""))
                {
                    rmm.setTinggi(rs.getDouble("tinggi"));
                }
                if(rs.getString("keluhan").equals(""))
                {
                    rmm.setKeluhan(rs.getString("keluhan"));
                }
                if(rs.getString("tindakan").equals(""))
                {
                    rmm.setTindakan(rs.getString("tindakan"));
                }
                if(rs.getString("saran").equals(""))
                {
                    rmm.setSaran(rs.getString("saran"));
                }
                if(rs.getString("id_dokter").equals(""))
                {
                    rmm.setId_dokter(rs.getString("id_dokter"));
                }
                if(rs.getString("id_resep").equals(""))
                {
                    rmm.setId_resep(rs.getString("id_resep"));
                }
                if(rs.getString("diagnosa").equals(""))
                {
                    rmm.setDiagnosa(rs.getString("diagnosa"));
                }
                if(rs.getString("user_id").equals(""))
                {
                    rmm.setUser(rs.getString("user_id"));
                }
                listMedik.add(rmm);
            }
            System.out.println("GET data berhasil..");
        }
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listMedik;
    }
    
    public void add(rekam_medik_model rmm , String page){
        System.out.println("--ADD/UPDATE--");
        String query = null;
        if (page.equals("update")) {
            query = "CALL UPDATE_REKAMMEDIK(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }
        else if (page.equals("add")) {
            query = "CALL INSERT_REKAMMEDIK(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1, rmm.getTgl_daftar());
            ps.setString(2, rmm.getId_poli());
            ps.setString(3, rmm.getTes_darah());
            ps.setDouble(4, rmm.getBerat());
            ps.setDouble(5, rmm.getTinggi());
            ps.setString(6, rmm.getKeluhan());
            ps.setString(7, rmm.getTindakan());
            ps.setString(8, rmm.getSaran());
            ps.setString(9, rmm.getId_dokter());
            ps.setString(10, rmm.getId_resep());
            ps.setString(11, rmm.getDiagnosa());
            ps.setString(12, rmm.getUser());
            ps.setString(13, rmm.getId_pasien());
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
            String query = "CALL DELETE_REKAMMEDIK(?)";
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
        RekamMedikDAO rmd = new RekamMedikDAO();
        rekam_medik_model rmm = new rekam_medik_model();
        rmm.setId_pasien("P001");
        rmm.setTgl_daftar("2020-10-5");
        rmm.setId_poli("P1");
        rmm.setTes_darah("Tinggi");
        rmm.setBerat(55);
        rmm.setTinggi(170);
        rmm.setKeluhan("Sakit Kepala");
        rmm.setTindakan("Pengecekan");
        rmm.setSaran("Tidak Ada");
        rmm.setId_dokter("DK0001");
        rmm.setId_resep("RS001");
        rmm.setDiagnosa("Vertigo");
        rmm.setUser("98234");
        rmd.add(rmm, "add");
        System.out.println(rmd.getData());
        
    }
    
}
