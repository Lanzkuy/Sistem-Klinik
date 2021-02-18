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
                if (rs.getString("id_layanan").equals("")) {
                    lm.setId_layanan(rs.getString("id_layanan"));
                }
                if (rs.getString("des_layanan").equals("")) {
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
//    public layanan_model getRecordByid_layanan(String id_layanan){
//            layanan_model lm = new layanan_model();
//            String sqlSearch = "select * from layanan where id_layanan=?";
//            try {
//                ps = conn.prepareStatement(sqlSearch);
//                ps.setString(1, id_layanan);
//                rs = ps.executeQuery();
//                if (rs.next()){
//                    lm.setId_layanan(rs.getString("id_layanan"));
//                    lm.setDes_layanan(rs.getString("des_layanan"));
//                }
//            }
//            catch (SQLException se){
//                System.out.println("kesalahan pada : " + se);
//            }
//            return lm;
//        }
    
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
            String sqlHapus = "DELETE FROM layanan WHERE id_layanan=?";
            try{
                ps = conn.prepareStatement(sqlHapus);
                ps.setString(1, id_layanan);
                ps.executeUpdate();
            }
            catch(SQLException e){
                System.out.println("kesalahan hapus data: " + e);
            }
        }
    
    
    public static void main(String[] args) {
        LayananDAO ld =new LayananDAO();
        layanan_model lm = new layanan_model();
//        System.out.println(ld.getData());
        
        lm.setDes_layanan("desku");
        lm.setId_layanan("Z01");
        ld.save(lm,"update");
        System.out.println(ld.getData());
    }
}
