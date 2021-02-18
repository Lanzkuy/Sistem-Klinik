package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.dokter_model;

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
    
    public String GenerateID()
    {
        String query = "SELECT id_dokter FROM dokter ORDER BY id_dokter DESC LIMIT 1";
        String id="DK0001";
        try
        {
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String lastId=rs.getString("id_dokter");
                String zero="";
                String strId=lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId=Integer.valueOf(strId)+1;
                int idLength=String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero+="0";
                }
                id="DK"+zero+numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    public void save(dokter_model dm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="CALL UPDATE_DOKTER(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }
        else if(page.equals("insert"))
        {
            query="CALL INSERT_DOKTER(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, dm.getNama_dokter());
            ps.setString(2, dm.getTgl_lahir());
            ps.setString(3, dm.getId_poli());
            ps.setString(4, dm.getJenis_kelamin());
            ps.setString(5, dm.getAlamat());
            ps.setString(6, dm.getNo_hp());
            ps.setString(7, dm.getNo_ktp());
            ps.setString(8, dm.getSpecialis());
            ps.setString(9, dm.getPassword());
            ps.setString(10, dm.getEmail());
            ps.setString(11, dm.getNo_npwp());
            ps.setString(12, dm.getUser_id());
            ps.setString(13, dm.getId_dokter());
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
        DokterDAO dd=new DokterDAO();
        dokter_model um=new dokter_model();
        um.setId_dokter("DK0003");
        um.setNama_dokter("Husky");
        um.setTgl_lahir("2020-09-12");
        um.setId_poli("P1");
        um.setJenis_kelamin("L");
        um.setAlamat("Bandung");
        um.setNo_hp("08123412");
        um.setNo_ktp("1241303");
        um.setSpecialis("Telinga");
        um.setPassword("whoa1234");
        um.setEmail("whoas@gmail.com");
        um.setNo_npwp("80231233");
        um.setUser_id("US0001");
        dd.save(um,"update");
        //dd.deleteData("DK0002");
        System.out.println(dd.getData());
    }
}
