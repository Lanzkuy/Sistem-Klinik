package dao;
import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.detail_resep_model;

public class DetailResepDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public DetailResepDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<detail_resep_model> getData() {
        System.out.println("-GET-");
        ArrayList<detail_resep_model> listdetailresep=new ArrayList<>();
        try{
            String query="SELECT *FROM detail_resep ORDER BY id_resep";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                detail_resep_model drm = new detail_resep_model();
                if (rs.getString("id_resep").equals("")) {
                    drm.setId_resep(rs.getString("id_resep"));
                }      
                listdetailresep.add(drm);
            }
        }
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listdetailresep;
       
    }
    
    /*public void save(detail_resep_model drm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="update detail_resep set des_detail_resep=?, biaya_jumlah=?, keterangan=? where id_detail_resep=? and id_resep=?";
   
        }
        else if(page.equals("insert"))
        {
            query="insert into detail_resep( des_detail_resep, biaya_jumlah, keterangan, id_detail_resep, id_resep)"+
                    "values (?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            
            ps.setString(1, dlm.getDes_detail_resep());
            ps.setDouble(2, dlm.getBiaya_jumlah());
            ps.setString(3, dlm.getKeterangan());
            ps.setString(4, dlm.getId_detail_resep());
            ps.setString(5, dlm.getId_resep());
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
    }*/
    
    public void hapusData(String id_resep, String id_detail_resep){
            String sqlHapus = "DELETE FROM resep WHERE id_detail_resep=? and id_resep=?";
            try{
                ps = conn.prepareStatement(sqlHapus);
                ps.setString(1, id_detail_resep);
                ps.setString(2, id_resep);
                ps.executeUpdate();
            }
            catch(SQLException e){
                System.out.println("kesalahan hapus data: " + e);
            }
        }
    
    public static void main(String[] args) {
        DetailResepDAO drd = new DetailResepDAO();
        detail_resep_model drm = new detail_resep_model();
        System.out.println(drd.getData());
    }
}