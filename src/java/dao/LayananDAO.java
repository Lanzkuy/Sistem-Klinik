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
import model.layanan_model;

/**
 *
 * @author Cahyo
 */
public class LayananDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public LayananDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<layanan_model> getData() {
        ArrayList<layanan_model> listlayanan=new ArrayList<>();
        try{
            System.out.println("-GET-");
            String query="SELECT *FROM layanan ORDER BY id_layanan";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                layanan_model lm = new layanan_model();
                if (!rs.getString("id_layanan").equals("")) {
                    lm.setId_layanan(rs.getString("id_layanan"));
                }
                if (!rs.getString("des_layanan").equals("")) {
                    lm.setDes_layanan(rs.getString("des_layanan"));
                }
                listlayanan.add(lm);
            }
        }
        
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listlayanan;
    }
    
    public String GenerateId(){
        String query = "SELECT id_layanan FROM layanan ORDER BY id_layanan DESC LIMIT 1";
        String id = "L01";
        try{
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString("id_layanan");
                String zero = "";
                String strId = lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId = Integer.valueOf(strId)+1;
                int idLength = String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero += "0";
                }
                id = String.valueOf(strId);
                id = "L"+zero+numId;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return id;
    }
    
    public void save(layanan_model lm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="update layanan set des_layanan=? where id_layanan=?";
        }
        else if(page.equals("insert"))
        {
            query="insert into layanan (des_layanan, id_layanan ) values (?,?)";
        }
        try
        {   
            ps=conn.prepareStatement(query);
            ps.setString(1, lm.getDes_layanan());
            ps.setString(2, lm.getId_layanan());
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
    
    public void delete(String id_layanan){
        try{
            String sqlHapus = "DELETE FROM layanan WHERE id_layanan=?";
            ps = conn.prepareStatement(sqlHapus);
            ps.setString(1, id_layanan);
            ps.executeUpdate();
            System.out.println("Delete Data Succes");
        }
        catch(SQLException e){
            System.out.println("Delete Data Error: " + e);
        }
    }
    
    public layanan_model getLayananById(String id_layanan)
    {
        System.out.println("-BY ID-");
        layanan_model lm=new layanan_model();
        String query="SELECT *FROM layanan WHERE id_layanan=?";
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, id_layanan);
            rs=ps.executeQuery();
            if(rs.next())
            {
                if (!rs.getString("id_layanan").equals("")) {
                    lm.setId_layanan(rs.getString("id_layanan"));
                }
                if (!rs.getString("des_layanan").equals("")) {
                    lm.setDes_layanan(rs.getString("des_layanan"));
                }
            }
            System.out.println("Get Data Success");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return lm;
    }
    
    
    public static void main(String[] args) {
        LayananDAO ld =new LayananDAO();
        layanan_model lm = new layanan_model();
        lm.setDes_layanan("test");
        lm.setId_layanan(ld.GenerateId());
        //ld.save(lm,"insert");
        System.out.println(ld.getLayananById("L01"));
        System.out.println(ld.getData());
    }
}
