package dao;
import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.resep_model;

public class ResepDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;   
    
    public ResepDAO()
    {
        conn=DatabaseConnection.connection();
    }
    public ArrayList<resep_model> getData() {
        System.out.println("-GET-");
        ArrayList<resep_model> listResep=new ArrayList<>();
        try{
            String query="CALL GET_RESEP";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                resep_model rm = new resep_model();
                if (!rs.getString("id_resep").equals("")) {
                    rm.setId_resep(rs.getString("id_resep"));
                }
                if (!rs.getString("id_dokter").equals("")) {
                    rm.setId_dokter(rs.getString("id_dokter"));
                }
                if (!rs.getString("tgl_resep").equals("")) {
                    rm.setTgl_resep(rs.getString("tgl_resep"));
                }
                if (!rs.getString("id_poli").equals("")) {
                    rm.setId_poli(rs.getString("id_poli"));
                }
                if (!rs.getString("user_id").equals("")) {
                    rm.setUser_id(rs.getString("user_id"));
                }   
                listResep.add(rm);
            }
            System.out.println("Get Data Success");
        }
        
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listResep;
    }
    
    public String GenerateID()
    {
        String query = "SELECT id_resep FROM resep ORDER BY id_resep DESC LIMIT 1";
        String id="RS0001";
        try
        {
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String lastId=rs.getString("id_resep");
                String zero="";
                String strId=lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId=Integer.valueOf(strId)+1;
                int idLength=String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero+="0";
                }
                id="RS"+zero+numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    public resep_model getResepById(String id_resep)
    {
        System.out.println("-BY ID-");
        resep_model rm=new resep_model();
        String query="CALL BYID_RESEP(?)";
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, id_resep);
            rs=ps.executeQuery();
            if(rs.next())
            {
                if (!rs.getString("id_resep").equals("")) {
                    rm.setId_resep(rs.getString("id_resep"));
                }
                if (!rs.getString("id_dokter").equals("")) {
                    rm.setId_dokter(rs.getString("id_dokter"));
                }
                if (!rs.getString("tgl_resep").equals("")) {
                    rm.setTgl_resep(rs.getString("tgl_resep"));
                }
                if (!rs.getString("id_poli").equals("")) {
                    rm.setId_poli(rs.getString("id_poli"));
                }
                if (!rs.getString("user_id").equals("")) {
                    rm.setUser_id(rs.getString("user_id"));
                }   
            }
            System.out.println("Get Data Success");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return rm;
    }
    
    public void save(resep_model rm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="CALL UPDATE_RESEP(?,?,?,?,?)";
        }
        else if(page.equals("insert"))
        {
            query="CALL INSERT_RESEP(?,?,?,?,?)";
        }
        try
        {   
            ps=conn.prepareStatement(query);
            ps.setString(1, rm.getId_dokter());
            ps.setString(2, rm.getTgl_resep());
            ps.setString(3, rm.getId_poli());
            ps.setString(4, rm.getUser_id());
            ps.setString(5, rm.getId_resep());
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
            System.out.println("-DELETE-");
            try{
                String query = "CALL DELETE_RESEP(?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, id_resep);
                ps.executeUpdate();
                System.out.println("Delete Data Success");
            }
            catch(SQLException e){
                System.out.println("Delete Data Error : " + e);
            }
        }
    
    public static void main(String[] args) {
        ResepDAO rd =new ResepDAO();
        resep_model rm = new resep_model();
        rm.setId_resep(rd.GenerateID());
        rm.setId_dokter("DK0001");
        rm.setId_poli("P2");
        rm.setTgl_resep("21-08-21");
        rm.setUser_id("US0002");
        //rd.save(rm, "insert");
        //rd.delete("RS0001");
        System.out.println(rd.GenerateID());
        System.out.println(rd.getData());
    }
}