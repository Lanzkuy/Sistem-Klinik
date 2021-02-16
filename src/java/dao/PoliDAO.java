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
            String query="SELECT *FROM poli ORDER BY id_poli";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                poli_model pm=new poli_model(); 
                if(rs.getString("id_poli").equals(""))
                {
                    pm.setId_poli(rs.getString("id_poli"));
                }
                if(rs.getString("nama_supplier").equals(""))
                {
                    pm.setNama_poli(rs.getString("nama_supplier"));
                }
                listPoli.add(pm);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listPoli;
    }
    
    public static void main(String[] args) {
        PoliDAO pd=new PoliDAO();
        System.out.println(pd.getData());
    }
}
