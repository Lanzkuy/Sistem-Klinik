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
import model.resep_model;

/**
 *
 * @author Alief
 */
public class ResepDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public ResepDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<resep_model> getData() {
        ArrayList<resep_model> listresep=new ArrayList<>();
        try{
            System.out.println("-GET-");
            String query="SELECT *FROM resep ORDER BY id_resep";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                resep_model rm = new resep_model();
                if (rs.getString("id_resep").equals("")) {
                    rm.setId_resep(rs.getString("id_resep"));
                }
                if (rs.getString("des_resep").equals("")) {
                    rm.setDes_layanan(rs.getString("des_resep"));
                }
                listresep.add(rm);
            }
            System.out.println("Get Data Success");
            System.out.println("--------------------------");
        }
        
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listresep;
    }
//    public resep_model getRecordByid_resep(String id_resep){
//            resep_model rm = new resep_model();
//            String sqlSearch = "select * from resep where id_resep=?";
//            try {
//                ps = conn.prepareStatement(sqlSearch);
//                ps.setString(1, id_resep);
//                rs = ps.executeQuery();
//                if (rs.next()){
//                    rm.setId_resep(rs.getString("id_resep"));
//                    rm.setDes_resep(rs.getString("des_resep"));
//                }
//            }
//            catch (SQLException se){
//                System.out.println("kesalahan pada : " + se);
//            }
//            return rm;
//        }
    

    
    public void save(resep_model rm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="update resep set des_resep=? where id_resep=?";
        }
        else if(page.equals("insert"))
        {
            query="insert into resep (des_resep, id_resep ) values (?,?)";
        }
        try
        {   
            ps=conn.prepareStatement(query);
            ps.setString(1, rm.getDes_resep());
            ps.setString(2, rm.getId_resep());
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
    
    public void delete(String id_resep){
            String sqlHapus = "DELETE FROM resep WHERE id_resep=?";
            try{
                ps = conn.prepareStatement(sqlHapus);
                ps.setString(1, id_resep);
                ps.executeUpdate();
            }
            catch(SQLException e){
                System.out.println("kesalahan hapus data: " + e);
            }
        }
    
    
    public static void main(String[] args) {
        ResepDAO rd =new ResepDAO();
        resep_model rm = new resep_model();
//        System.out.println(rd.getData());
        
        rm.setDes_Resep("desku");
        rm.setId_resep("Z01");
        rd.save(rm,"update");
        System.out.println(rd.getData());
    }
}