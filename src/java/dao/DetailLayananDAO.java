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
import model.detail_layanan_model;
/**
 *
 * @author Cahyo
 */
public class DetailLayananDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public DetailLayananDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<detail_layanan_model> getData() {
        System.out.println("-GET-");
        ArrayList<detail_layanan_model> listdetaillayanan=new ArrayList<>();
        try{
            String query="SELECT *FROM detail_layanan ORDER BY id_layanan";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                detail_layanan_model dlm = new detail_layanan_model();
                if (!rs.getString("id_layanan").equals("")) {
                    dlm.setId_layanan(rs.getString("id_layanan"));
                }
                if (!rs.getString("id_detail_layanan").equals("")) {
                    dlm.setId_detail_layanan(rs.getString("id_detail_layanan"));
                }
                if (!rs.getString("des_detail_layanan").equals("")) {
                    dlm.setDes_detail_layanan(rs.getString("des_detail_layanan"));
                }
                if (!rs.getString("keterangan").equals("")) {
                    dlm.setKeterangan(rs.getString("keterangan"));
                }
                if(!rs.getString("biaya_layanan").equals("")){
                    dlm.setBiaya_layanan(rs.getDouble("biaya_layanan"));
                }
                listdetaillayanan.add(dlm);
            }
        }
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listdetaillayanan;
       
    }
    
    public String GenerateId(){
        String query = "SELECT id_detail_layanan FROM detail_layanan ORDER BY id_detail_layanan DESC LIMIT 1";
        String id = "DL01";
        try{
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString("id_detail_layanan");
                String zero = "";
                String strId = lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId = Integer.valueOf(strId)+1;
                int idLength = String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero += "0";
                }
                id = String.valueOf(strId);
                id = "DL"+zero+numId;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return id;
    }
    
    public void save(detail_layanan_model dlm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="update detail_layanan set des_detail_layanan=?, biaya_layanan=?, keterangan=? where id_detail_layanan=? and id_layanan=?";
   
        }
        else if(page.equals("insert"))
        {
            query="insert into detail_layanan( des_detail_layanan, biaya_layanan, keterangan, id_detail_layanan, id_layanan)"+
                    "values (?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            
            ps.setString(1, dlm.getDes_detail_layanan());
            ps.setDouble(2, dlm.getBiaya_layanan());
            ps.setString(3, dlm.getKeterangan());
            ps.setString(4, dlm.getId_detail_layanan());
            ps.setString(5, dlm.getId_layanan());
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
    public void deleteData(String id_layanan, String id_detail_layanan){
        System.out.println("-INSERT/UPDATE-");
        try{
            String sqlHapus = "DELETE FROM obat WHERE id_detail_layanan=? and id_layanan=?";
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id_detail_layanan);
            ps.setString(2, id_layanan);
            ps.executeUpdate();
        System.out.println("Delete Data Success");
        }
        catch(SQLException e){
            System.out.println("Delete Data Error : " + e);
        }
    }
    
    public detail_layanan_model getLayananById(String id_detail_layanan)
    {
        System.out.println("-BY ID-");
        detail_layanan_model dlm=new detail_layanan_model();
        String query="SELECT *FROM detail_layanan WHERE id_detail_layanan=?";
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, id_detail_layanan);
            rs=ps.executeQuery();
            if(rs.next())
            {
                if (!rs.getString("id_layanan").equals("")) {
                    dlm.setId_layanan(rs.getString("id_layanan"));
                }
                if (!rs.getString("id_detail_layanan").equals("")) {
                    dlm.setId_detail_layanan(rs.getString("id_detail_layanan"));
                }
                if (!rs.getString("des_detail_layanan").equals("")) {
                    dlm.setDes_detail_layanan(rs.getString("des_detail_layanan"));
                }
                if (!rs.getString("keterangan").equals("")) {
                    dlm.setKeterangan(rs.getString("keterangan"));
                }
                if(!rs.getString("biaya_layanan").equals("")){
                    dlm.setBiaya_layanan(rs.getDouble("biaya_layanan"));
                }
            }
            System.out.println("Get Data Success");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return dlm;
    }
    
    public static void main(String[] args) {
        DetailLayananDAO dld = new DetailLayananDAO();
        detail_layanan_model dlm = new detail_layanan_model();
        dlm.setDes_detail_layanan("test123");
        dlm.setBiaya_layanan(7000);
        dlm.setKeterangan("test123");
        dlm.setId_detail_layanan(dld.GenerateId());
        dlm.setId_layanan("L01");
        dld.save(dlm, "update");
        System.out.println(dld.getLayananById("DL01"));
        System.out.println(dld.getData());
    }
}
