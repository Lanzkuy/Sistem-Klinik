package dao;

import connection.DatabaseConnection;
import model.user_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public UserDAO()
    {
        conn=DatabaseConnection.connection();
    }   
    
    public ArrayList<user_model> getData()
    {
        System.out.println("-GET-");
        ArrayList<user_model> listUser=new ArrayList<>();
        try 
        {
            String query="CALL GET_USER";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                user_model km=new user_model();
                if(rs.getString("id_user").equals(""))
                {
                    km.setId_user(rs.getString("id_user"));
                }
                if(rs.getString("nama_user").equals(""))
                {
                    km.setNama_user(rs.getString("nama_user"));
                }
                if(rs.getString("password").equals(""))
                {
                    km.setPassword(rs.getString("password"));
                }
                if(rs.getString("no_ktp").equals(""))
                {
                    km.setNo_ktp(rs.getString("no_ktp"));
                }
                if(rs.getString("alamat").equals(""))
                {
                    km.setAlamat(rs.getString("alamat"));
                }
                if(rs.getString("no_hp").equals(""))
                {
                    km.setNo_hp(rs.getString("no_hp"));
                }
                if(rs.getString("id_role").equals(""))
                {
                    km.setId_role(rs.getString("id_role"));
                }
                listUser.add(km);
            }
            System.out.println("Get Data Success");
        } 
        catch (SQLException e) 
        {
            System.out.println(e);
        }
        return listUser;
    }
    
    public void save(user_model um, String page)
    {
        String query=null;
        if(page.equals("update"))
        {
            query="UPDATE user SET nama_user=?,  password=?, no_ktp=?, alamat=?, no_hp=?, id_role=? WHERE id_user=?";
        }
        else if(page.equals("insert"))
        {
            query="INSERT INTO user (nama_user, password, no_ktp, alamat, no_hp, id_role, id_user) VALUES (?,?,?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, um.getNama_user());
            ps.setString(2, um.getPassword());
            ps.setString(3, um.getNo_hp());
            ps.setString(4, um.getAlamat());
            ps.setString(5, um.getNo_hp());
            ps.setString(6, um.getId_role());
            ps.setString(7, um.getId_user());
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
            String query="CALL DELETE_USER";
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
    
    public String login(String id, String password)
     {
         System.out.println("-LOGIN-");
         user_model um=new user_model();
         try
         {
                String query="SELECT *FROM user where id_user=? and password=?";
                ps=conn.prepareStatement(query);
                ps.setString(1, id);
                ps.setString(2, password);
                rs=ps.executeQuery();
                if (rs.next()) 
                { 
                    return "Login Success";
                }
         }  
         catch (SQLException e) 
         {
             System.err.println("Login Error : "+e);
         }
        return "Login Failed (Wrong Username or Password)";
     }
    
    public static void main(String[] args) {
        UserDAO ud=new UserDAO();
        user_model um=new user_model();
        System.out.println(ud.getData());
        System.out.println(ud.login("0001","1234"));
    }
}
