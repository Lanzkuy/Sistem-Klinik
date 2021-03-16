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
import model.karyawan_model;

/**
 *
 * @author Ajie
 */
public class KaryawanDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public KaryawanDAO()
    {
        conn = DatabaseConnection.connection();
    }
    
    public ArrayList<karyawan_model>getData(){
        System.out.println("-GET-");
        ArrayList<karyawan_model>listKaryawan = new ArrayList();
        try{
            String query = "SELECT *FROM karyawan ORDER BY id_karyawan";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                karyawan_model km = new karyawan_model();
                if (!rs.getString("id_karyawan").equals("")) {
                    km.setId_karyawan(rs.getString("id_karyawan"));
                }
                if (!rs.getString("nama_karyawan").equals("")) {
                    km.setNama_karyawan(rs.getString("nama_karyawan"));
                }
                if (!rs.getString("tgl_lahir").equals("")) {
                    km.setTgl_lahir(rs.getString("tgl_lahir"));
                }
                if (!rs.getString("bidang_pekerjaan").equals("")) {
                    km.setBidang_pekerjaan(rs.getString("bidang_pekerjaan"));
                }
                if (!rs.getString("jenis_kelamin").equals("")) {
                    km.setJenis_kelamin(rs.getString("jenis_kelamin"));
                }
                if (!rs.getString("alamat").equals("")) {
                    km.setAlamat(rs.getString("alamat"));
                }
                if (!rs.getString("no_hp").equals("")) {
                    km.setNo_hp(rs.getString("no_hp"));
                }
                if (!rs.getString("no_ktp").equals("")) {
                    km.setNo_ktp(rs.getString("no_ktp"));
                }
                if (!rs.getString("email").equals("")) {
                    km.setEmail(rs.getString("email"));
                }
                if (!rs.getString("no_npwp").equals("")) {
                    km.setNo_npwp(rs.getString("no_npwp"));
                }
                if (!rs.getString("user_id").equals("")) {
                    km.setUser_id(rs.getString("user_id"));
                }
                listKaryawan.add(km);
            }
        }
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listKaryawan;
    }
    
    public String GenerateID(){
        String query = "SELECT id_karyawan FROM karyawan ORDER BY id_karyawan DESC LIMIT 1";
        String id = "KR0001";
        try{
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString("id_karyawan");
                String zero = "";
                String strId = lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId = Integer.valueOf(strId) + 1;
                int idLength = String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero += "0";
                }
                id = String.valueOf(strId);
                id = "KR" + zero + numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    public void save(karyawan_model km, String page){
        System.out.println("-ADD/UPDATE-");
        String query = null;
        if (page.equals("update")) {
            query = "CALL UPDATE_KARYAWAN(?,?,?,?,?,?,?,?,?,?,?)";
        }
        else if (page.equals("insert")) {
            query = "CALL INSERT_KARYAWAN(?,?,?,?,?,?,?,?,?,?,?)";
        }
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1, km.getNama_karyawan());
            ps.setString(2, km.getTgl_lahir());
            ps.setString(3, km.getBidang_pekerjaan());
            ps.setString(4, km.getJenis_kelamin());
            ps.setString(5, km.getAlamat());
            ps.setString(6, km.getNo_hp());
            ps.setString(7, km.getNo_ktp());
            ps.setString(8, km.getEmail());
            ps.setString(9, km.getNo_npwp());
            ps.setString(10, km.getUser_id());
            ps.setString(11, km.getId_karyawan());
            ps.executeUpdate();
            System.out.println("Insert/Update Data Success");
        }
        catch (SQLException e) 
        {
            System.out.println("Save Data Error : "+e);
        }
        finally{
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
    
    public void deleteData(String id){
        System.out.println("-DELETE-");
        karyawan_model km = new karyawan_model();
        try{
            String query = "CALL DELETE_KARYAWAN(?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
            System.out.println("Delete Data Success");
        }
        catch(SQLException e)
        {
            System.out.println("Data Delete Error : "+e);
        }
    }
    
    public karyawan_model getKaryawanById(String id_karyawan)
    {
        System.out.println("-BY ID-");
        karyawan_model km=new karyawan_model();
        String query="CALL BYID_KARYAWAN(?)";
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, id_karyawan);
            rs=ps.executeQuery();
            if(rs.next())
            {
                if (!rs.getString("id_karyawan").equals("")) {
                    km.setId_karyawan(rs.getString("id_karyawan"));
                }
                if (!rs.getString("nama_karyawan").equals("")) {
                    km.setNama_karyawan(rs.getString("nama_karyawan"));
                }
                if (!rs.getString("tgl_lahir").equals("")) {
                    km.setTgl_lahir(rs.getString("tgl_lahir"));
                }
                if (!rs.getString("bidang_pekerjaan").equals("")) {
                    km.setBidang_pekerjaan(rs.getString("bidang_pekerjaan"));
                }
                if (!rs.getString("jenis_kelamin").equals("")) {
                    km.setJenis_kelamin(rs.getString("jenis_kelamin"));
                }
                if (!rs.getString("alamat").equals("")) {
                    km.setAlamat(rs.getString("alamat"));
                }
                if (!rs.getString("no_hp").equals("")) {
                    km.setNo_hp(rs.getString("no_hp"));
                }
                if (!rs.getString("no_ktp").equals("")) {
                    km.setNo_ktp(rs.getString("no_ktp"));
                }
                if (!rs.getString("email").equals("")) {
                    km.setEmail(rs.getString("email"));
                }
                if (!rs.getString("no_npwp").equals("")) {
                    km.setNo_npwp(rs.getString("no_npwp"));
                }
                if (!rs.getString("user_id").equals("")) {
                    km.setUser_id(rs.getString("user_id"));
                }
            }
            System.out.println("Get Data Success");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return km;
    }
    
    public static void main(String[] args){
        KaryawanDAO kardao = new KaryawanDAO();
        karyawan_model km = new karyawan_model();
        km.setId_karyawan("KR0001");
        km.setNama_karyawan("Rini Swain");
        km.setTgl_lahir("1994-10-5");
        km.setBidang_pekerjaan("Perawat");
        km.setJenis_kelamin("P");
        km.setAlamat("Cibubur, Jakarta Timur");
        km.setNo_hp("034534534");
        km.setNo_ktp("9834503453");
        km.setEmail("budigaming@gmail.com");
        km.setNo_npwp("093503453");
        km.setUser_id("US0001");
        kardao.save(km, "update");
        kardao.deleteData("KR0001");
        System.out.println(kardao.getKaryawanById("KR0001"));
        System.out.println(kardao.getData());
    }
    
}
