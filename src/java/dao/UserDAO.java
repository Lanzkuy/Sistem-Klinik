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
                if(!rs.getString("id_user").equals(""))
                {
                    km.setId_user(rs.getString("id_user"));
                }
                if(!rs.getString("nama_user").equals(""))
                {
                    km.setNama_user(rs.getString("nama_user"));
                }
                if(!rs.getString("password").equals(""))
                {
                    km.setPassword(rs.getString("password"));
                }
                if(!rs.getString("no_ktp").equals(""))
                {
                    km.setNo_ktp(rs.getString("no_ktp"));
                }
                if(!rs.getString("alamat").equals(""))
                {
                    km.setAlamat(rs.getString("alamat"));
                }
                if(!rs.getString("no_hp").equals(""))
                {
                    km.setNo_hp(rs.getString("no_hp"));
                }
                if(!rs.getString("id_role").equals(""))
                {
                    km.setId_role(rs.getString("id_role"));
                }
                if(!rs.getString("des_role").equals(""))
                {
                    km.setNama_role(rs.getString("des_role"));
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
    
    public String GenerateID()
    {
        String query = "SELECT id_user FROM user ORDER BY id_user DESC LIMIT 1";
        String id="US0001";
        try
        {
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String lastId=rs.getString("id_user");
                String zero="";
                String strId=lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId=Integer.valueOf(strId)+1;
                int idLength=String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero+="0";
                }
                id=String.valueOf(strId);
                id="US"+zero+numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }
    
    public void save(user_model um, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="CALL UPDATE_USER(?,?,?,?,?,?,?)";
        }
        else if(page.equals("insert"))
        {
            query="CALL INSERT_USER(?,?,?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, um.getNama_user());
            ps.setString(2, um.getPassword());
            ps.setString(3, um.getNo_ktp());
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
        try
        {
            String query="CALL DELETE_USER(?)";
            ps=conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
            System.out.println("Delete Data Success");
        }
        catch(SQLException e)
        {
            System.out.println("Delete Data Error : "+e);
        }
    }
    
    public user_model login(String id, String password)
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
                   um.setId_user(rs.getString("id_user"));
                   um.setPassword(rs.getString("password"));
                   um.setNama_user(rs.getString("nama_user"));
                   um.setNo_hp(rs.getString("no_hp"));
                   um.setNo_ktp(rs.getString("no_ktp"));
                   um.setAlamat(rs.getString("alamat"));
                   um.setId_role(rs.getString("id_role"));
               }
        }  
        catch (SQLException e) 
        {
            System.err.println("Login Error : "+e);
        }
        return um;
    }
    
    public user_model getUserById(String id_user)
    {
        System.out.println("-BY ID-");
        user_model km=new user_model();
        String query="CALL BYID_USER(?)";
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, id_user);
            rs=ps.executeQuery();
            if(rs.next())
            {
                if(!rs.getString("id_user").equals(""))
                {
                    km.setId_user(rs.getString("id_user"));
                }
                if(!rs.getString("nama_user").equals(""))
                {
                    km.setNama_user(rs.getString("nama_user"));
                }
                if(!rs.getString("password").equals(""))
                {
                    km.setPassword(rs.getString("password"));
                }
                if(!rs.getString("no_ktp").equals(""))
                {
                    km.setNo_ktp(rs.getString("no_ktp"));
                }
                if(!rs.getString("alamat").equals(""))
                {
                    km.setAlamat(rs.getString("alamat"));
                }
                if(!rs.getString("no_hp").equals(""))
                {
                    km.setNo_hp(rs.getString("no_hp"));
                }
                if(!rs.getString("id_role").equals(""))
                {
                    km.setId_role(rs.getString("id_role"));
                }
            }
            System.out.println("Get Data Success");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return km;
    }
    
    public static void main(String[] args) {
        UserDAO ud=new UserDAO();
        user_model um=new user_model();
        um.setId_user("US0003");
        um.setNama_user("Husky Siberia");
        um.setPassword("whoa123");
        um.setNo_hp("081253123");
        um.setAlamat("Alaskaaa");
        um.setId_role("3");
        um.setNo_ktp("412313123");
        //ud.save(um,"update");
       // ud.deleteData("US0003");
        System.out.println(ud.getUserById("US0001"));
        System.out.println(ud.getData());
        System.out.println(ud.login("US0001","whoa1234"));
    }
}
