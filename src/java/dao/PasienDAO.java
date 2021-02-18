package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.pasien_model;
import model.supplier_model;

public class PasienDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
    public PasienDAO()
    {
        conn=DatabaseConnection.connection();
    }
    
    public ArrayList<pasien_model> getData()
    {
        System.out.println("-GET-");
        ArrayList<pasien_model> listPasien=new ArrayList<>();
        try 
        {
            String query="CALL GET_PASIEN";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                pasien_model sm=new pasien_model(); 
                if(rs.getString("id_pasien").equals(""))
                {
                    sm.setId_pasien(rs.getString("id_pasien"));
                }
                if(rs.getString("nama_pasien").equals(""))
                {
                    sm.setNama_pasien(rs.getString("nama_pasien"));
                }
                if(rs.getString("tgl_lahir").equals(""))
                {
                    sm.setTgl_lahir(rs.getString("tgl_lahir"));
                }
                if(rs.getString("jenis_kelamin").equals(""))
                {
                    sm.setJenis_kelamin(rs.getString("jenis_kelamin"));
                }
                if(rs.getString("no_ktp").equals(""))
                {
                    sm.setNo_ktp(rs.getString("no_ktp"));
                }
                if(rs.getString("alamat").equals(""))
                {
                    sm.setAlamat(rs.getString("alamat"));
                }
                if(rs.getString("no_hp").equals(""))
                {
                    sm.setNo_hp(rs.getString("no_hp"));
                }
                if(rs.getString("gol_darah").equals(""))
                {
                    sm.setGol_darah(rs.getString("gol_darah"));
                }
                if(rs.getString("password").equals(""))
                {
                    sm.setPassword(rs.getString("password"));
                }
                if(rs.getString("user_id").equals(""))
                {
                    sm.setUser_id(rs.getString("user_id"));
                }
                listPasien.add(sm);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listPasien;
    }
    
    public String GenerateID()
    {
        String query = "SELECT id_pasien FROM pasien ORDER BY id_pasien DESC LIMIT 1";
        String id="PS0001";
        try
        {
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String lastId=rs.getString("id_pasien");
                String zero="";
                String strId=lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId=Integer.valueOf(strId)+1;
                int idLength=String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero+="0";
                }
                id="PS"+zero+numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    public void save(pasien_model pm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="CALL UPDATE_PASIEN(?,?,?,?,?,?,?,?,?,?)";
        }
        else if(page.equals("insert"))
        {
            query="CALL INSERT_PASIEN(?,?,?,?,?,?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, pm.getNama_pasien());
            ps.setString(2, pm.getTgl_lahir());
            ps.setString(3, pm.getJenis_kelamin());
            ps.setString(4, pm.getNo_ktp());
            ps.setString(5, pm.getAlamat());
            ps.setString(6, pm.getNo_hp());
            ps.setString(7, pm.getGol_darah());
            ps.setString(8, pm.getPassword());
            ps.setString(9, pm.getUser_id());
            ps.setString(10, pm.getId_pasien());
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
            String query="CALL DELETE_PASIEN(?)";
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
    
    public static void main(String[] args) {
        PasienDAO pd=new PasienDAO();
        pasien_model pm=new pasien_model();
        pm.setId_pasien(pd.GenerateID());
        pm.setNama_pasien("Leon Kennedy");
        pm.setTgl_lahir("03-05-13");
        pm.setJenis_kelamin("L");
        pm.setNo_ktp("24718223");
        pm.setAlamat("Jakarta");
        pm.setNo_hp("082613312");
        pm.setGol_darah("A");
        pm.setPassword("whoa1234");
        pm.setUser_id("US0004");
        pd.save(pm, "update");
        pd.deleteData("PS0002");
        System.out.println(pd.getData());
    }
}
