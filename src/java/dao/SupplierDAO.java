package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.supplier_model;

public class SupplierDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
    public SupplierDAO()
    {
        conn=DatabaseConnection.connection();
    }
    
    public ArrayList<supplier_model> getData()
    {
        System.out.println("-GET-");
        ArrayList<supplier_model> listSupplier=new ArrayList<>();
        try 
        {
            String query="SELECT *FROM supplier ORDER BY id_supplier";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                supplier_model sm=new supplier_model(); 
                if(rs.getString("id_supplier").equals(""))
                {
                    sm.setId_supplier(rs.getString("id_supplier"));
                }
                if(rs.getString("nama_supplier").equals(""))
                {
                    sm.setNama_supplier(rs.getString("nama_supplier"));
                }
                if(rs.getString("alamat").equals(""))
                {
                    sm.setAlamat(rs.getString("alamat"));
                }
                if(rs.getString("no_telepon").equals(""))
                {
                    sm.setNo_telepon(rs.getString("no_telepon"));
                }
                if(rs.getString("email").equals(""))
                {
                    sm.setEmail(rs.getString("email"));
                }
                if(rs.getString("user_id").equals(""))
                {
                    sm.setUser_id(rs.getString("user_id"));
                }
                listSupplier.add(sm);
            }
            System.out.println("Get Data Success");
        } 
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listSupplier;
    }
    
    public String GenerateID()
    {
        String query = "SELECT id_supplier FROM supplier ORDER BY id_supplier DESC LIMIT 1";
        String id="SP0001";
        try
        {
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String lastId=rs.getString("id_supplier");
                String zero="";
                String strId=lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId=Integer.valueOf(strId)+1;
                int idLength=String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero+="0";
                }
                id="SP"+zero+numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    public void save(supplier_model sm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="CALL UPDATE_SUPPLIER(?,?,?,?,?,?)";
        }
        else if(page.equals("insert"))
        {
            query="CALL INSERT_SUPPLIER(?,?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, sm.getNama_supplier());
            ps.setString(2, sm.getAlamat());
            ps.setString(3, sm.getNo_telepon());
            ps.setString(4, sm.getEmail());
            ps.setString(5, sm.getUser_id());
            ps.setString(6, sm.getId_supplier());
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
        try
        {
            String query="CALL DELETE_SUPPLIER(?)";
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
    
    public supplier_model getSupplierById(String id_supplier)
    {
        System.out.println("-BY ID-");
        supplier_model sm=new supplier_model();
        String query="CALL BYID_SUPPLIER(?)";
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, id_supplier);
            rs=ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("id_supplier").equals(""))
                {
                    sm.setId_supplier(rs.getString("id_supplier"));
                }
                if(rs.getString("nama_supplier").equals(""))
                {
                    sm.setNama_supplier(rs.getString("nama_supplier"));
                }
                if(rs.getString("alamat").equals(""))
                {
                    sm.setAlamat(rs.getString("alamat"));
                }
                if(rs.getString("no_telepon").equals(""))
                {
                    sm.setNo_telepon(rs.getString("no_telepon"));
                }
                if(rs.getString("email").equals(""))
                {
                    sm.setEmail(rs.getString("email"));
                }
                if(rs.getString("user_id").equals(""))
                {
                    sm.setUser_id(rs.getString("user_id"));
                }
            }
            System.out.println("Get Data Success");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return sm;
    }
    
    public static void main(String[] args) {
        SupplierDAO sd=new SupplierDAO();
        supplier_model sm=new supplier_model();
        sm.setId_supplier("SP0002");
        sm.setNama_supplier("HIKARI");
        sm.setAlamat("Jakarta");
        sm.setNo_telepon("091273213");
        sm.setEmail("hikarimart@gmail.com");
        sm.setUser_id("US0001");
        //sd.save(sm, "update");
        //sd.deleteData("SP0001");
        System.out.println(sd.getSupplierById("SP0001"));
        System.out.println(sd.getData());
    }
}
