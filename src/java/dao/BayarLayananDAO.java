/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.bayar_layanan_model;

/**
 *
 * @author Ahmad Maulana
 */
public class BayarLayananDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BayarLayananDAO(){
        conn = DatabaseConnection.connection();
    }

    public ArrayList<bayar_layanan_model> getData()
    {
        System.out.println("-GET-");
        ArrayList<bayar_layanan_model> listUser=new ArrayList<>();
        try 
        {
            String query="CALL GET_BAYARLAYANAN";
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                bayar_layanan_model blm=new bayar_layanan_model();
                if(!rs.getString("id_bayar_layanan").equals(""))
                {
                    blm.setId_bayar_layanan(rs.getString("id_bayar_layanan"));
                }
                if(!rs.getString("id_layanan").equals(""))
                {
                    blm.setId_layanan(rs.getString("id_layanan"));
                }
                if(!rs.getString("id_detail_layanan").equals(""))
                {
                    blm.setId_detail_layanan(rs.getString("id_detail_layanan"));
                }
                if(!rs.getString("id_pasien").equals(""))
                {
                    blm.setId_pasien(rs.getString("id_pasien"));
                }
                if(!rs.getString("tgl_layanan").equals(""))
                {
                    blm.setTgl_layanan(rs.getString("tgl_layanan"));
                }
                if(!rs.getString("keterangan").equals(""))
                {
                    blm.setKeterangan(rs.getString("keterangan"));
                }
                listUser.add(blm);
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
        String query = "SELECT id_bayar_layanan FROM bayar_layanan ORDER BY id_bayar_layanan DESC LIMIT 1";
        String id="BL0001";
        try
        {
            ps=conn.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String lastId=rs.getString("id_bayar_layanan");
                String zero="";
                String strId=lastId.substring(lastId.replaceAll("[^a-zA-Z]", "").length());
                int numId=Integer.valueOf(strId)+1;
                int idLength=String.valueOf(numId).length();
                for (int i = 0; i < strId.length()-idLength; i++) {
                    zero+="0";
                }
                id=String.valueOf(strId);
                id="BL"+zero+numId;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return id;
    }

    public bayar_layanan_model getBayarLayananByID(String id_user)
    {
        System.out.println("-BY ID-");
        bayar_layanan_model blm=new bayar_layanan_model();
        String query="CALL BYID_BAYARLAYANAN(?)";
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, id_user);
            rs=ps.executeQuery();
            if(rs.next())
            {
                if(!rs.getString("id_bayar_layanan").equals(""))
                {
                    blm.setId_bayar_layanan(rs.getString("id_bayar_layanan"));
                }
                if(!rs.getString("id_layanan").equals(""))
                {
                    blm.setId_layanan(rs.getString("id_layanan"));
                }
                if(!rs.getString("id_detail_layanan").equals(""))
                {
                    blm.setId_detail_layanan(rs.getString("id_detail_layanan"));
                }
                if(!rs.getString("id_pasien").equals(""))
                {
                    blm.setId_pasien(rs.getString("id_pasien"));
                }
                if(!rs.getString("tgl_layanan").equals(""))
                {
                    blm.setTgl_layanan(rs.getString("tgl_layanan"));
                }
                if(!rs.getString("keterangan").equals(""))
                {
                    blm.setKeterangan(rs.getString("keterangan"));
                }
            }
            System.out.println("Get Data Success");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return blm;
    }

    public void save(bayar_layanan_model blm, String page)
    {
        System.out.println("-INSERT/UPDATE-");
        String query=null;
        if(page.equals("update"))
        {
            query="CALL UPDATE_BAYARLAYANAN(?,?,?,?,?,?,?)";
        }
        else if(page.equals("insert"))
        {
            query="CALL INSERT_BAYARLAYANAN(?,?,?,?,?,?,?)";
        }
        try
        {
            ps=conn.prepareStatement(query);
            ps.setString(1, blm.getId_layanan());
            ps.setString(2, blm.getId_detail_layanan());
            ps.setString(3, blm.getId_pasien());
            ps.setString(4, blm.getTgl_layanan());
            ps.setString(5, blm.getKeterangan());
            ps.setString(6, blm.getId_bayar_layanan());
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
            String query="CALL DELETE_BAYARLAYANAN(?)";
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


    public static void main(String[] args) {
        BayarLayananDAO bld = new BayarLayananDAO();
        bayar_layanan_model model = new bayar_layanan_model();
        model.setId_bayar_layanan(bld.GenerateID());
        model.setId_layanan("002");
        model.setId_detail_layanan("001");
        model.setId_pasien("P0001");
        model.setTgl_layanan("2002-02-02");
        model.setKeterangan("lunas");
        bld.save(model, "insert");
        //bld.deleteData("KR0010");
        System.out.println(bld.getBayarLayananByID("BL0001"));
        System.out.println(bld.getData());
    }
}
