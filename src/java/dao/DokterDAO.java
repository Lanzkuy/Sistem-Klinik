package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.dokter_model;
import model.user_model;

public class DokterDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
    public DokterDAO()
    {
        conn=DatabaseConnection.connection();
    }   
    
    public ArrayList<dokter_model> getData()
    {
        System.out.println("-GET-");
        ArrayList<dokter_model> listDokter=new ArrayList<>();
        try 
        {
            String query="SELECT *FROM dokter ORDER BY id_dokter";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                dokter_model dm=new dokter_model();
                if(rs.getString("id_dokter").equals(""))
                {
                    dm.setId_dokter(rs.getString("id_dokter"));
                }
                if(rs.getString("nama_dokter").equals(""))
                {
                    dm.setNama_dokter(rs.getString("tmplahir"));
                }
                if(rs.getDate("tgl_lahir").equals(""))
                {
                    dm.setTgl_lahir((sdf.format(rs.getDate("tgl_lahir")).toString()));
                }
                if(rs.getString("id_poli").equals(""))
                {
                    dm.setId_poli(rs.getString("id_poli"));
                }
                if(rs.getString("jenis_kelamin").equals(""))
                {
                    dm.setJenis_kelamin(rs.getString("jenis_kelamin"));
                }
                if(rs.getString("no_hp").equals(""))
                {
                    dm.setNo_hp(rs.getString("no_hp"));
                }
                if(rs.getString("spesialis").equals(""))
                {
                    dm.setSpecialis(rs.getString("spesialis"));
                }
                if(rs.getString("password").equals(""))
                {
                    dm.setPassword(rs.getString("password"));
                }
                if(rs.getString("email").equals(""))
                {
                    dm.setEmail(rs.getString("email"));
                }
                if(rs.getString("no_npwp").equals(""))
                {
                    dm.setNo_npwp(rs.getString("no_npwp"));
                }
                if(rs.getString("user_id").equals(""))
                {
                    dm.setUser_id(rs.getString("user_id"));
                }
                listDokter.add(dm);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listDokter;
    }
    
    public static void main(String[] args) {
        UserDAO ud=new UserDAO();
        System.out.println(ud.getData());
    }
}
