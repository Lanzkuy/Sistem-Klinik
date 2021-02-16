package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.supplier_model;
import model.user_model;

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
        } 
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listSupplier;
    }
    
    public static void main(String[] args) {
        SupplierDAO ud=new SupplierDAO();
        System.out.println(ud.getData());
    }
}
