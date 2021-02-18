package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.poli_model;
import model.supplier_model;
import model.user_model;

public class PoliDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
    public PoliDAO()
    {
        conn=DatabaseConnection.connection();
    }
    
    public ArrayList<poli_model> getData()
    {
        System.out.println("-GET-");
        ArrayList<poli_model> listPoli=new ArrayList<>();
        try 
        {
            String query="CALL GET_POLI";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                poli_model pm=new poli_model(); 
                if(rs.getString("id_poli").equals(""))
                {
                    pm.setId_poli(rs.getString("id_poli"));
                }
                if(rs.getString("nama_poli").equals(""))
                {
                    pm.setNama_poli(rs.getString("nama_poli"));
                }
                listPoli.add(pm);
            }
            System.out.println("Get Data Success");
        } 
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listPoli;
    }
    
    public String GenerateID()
    {
        String query = "SELECT id_poli FROM poli ORDER BY id_poli DESC LIMIT 1";
        String id="P1";
        try
        {
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String lastId=rs.getString("id_poli");
                String zero="";
                String strId=lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId=Integer.valueOf(strId)+1;
                id="P"+numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    public void save(poli_model pm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="CALL UPDATE_POLI(?,?)";
        }
        else if(page.equals("insert"))
        {
            query="CALL INSERT_POLI(?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, pm.getNama_poli());
            ps.setString(2, pm.getId_poli());
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
    
    public void deleteData(String id)
    {
        System.out.println("-DELETE-");
        user_model um=new user_model();
        try
        {
            String query="CALL DELETE_POLI(?)";
            ps=conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
            System.out.println("Delete Data Success");
        }
        catch(SQLException e)
        {
            System.out.println("Data Delete Error : "+e);
        }
    }
    
    public poli_model getPoliById(String id_poli)
    {
        System.out.println("-BY ID-");
        poli_model pm=new poli_model();
        String query="CALL BYID_POLI(?)";
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, id_poli);
            rs=ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("id_poli").equals(""))
                {
                    pm.setId_poli(rs.getString("id_poli"));
                }
                if(rs.getString("nama_poli").equals(""))
                {
                    pm.setNama_poli(rs.getString("nama_poli"));
                }
            }
            System.out.println("Get Data Success");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return pm;
    }
    
    public static void main(String[] args) {
        PoliDAO pd=new PoliDAO();
        poli_model pm=new poli_model();
        pm.setId_poli(pd.GenerateID());
        pm.setNama_poli("Gigi");
        //pd.save(pm, "insert");
        //pd.deleteData("P3");
        System.out.println(pd.getPoliById("P1"));
        System.out.println(pd.getData());
    }
}
