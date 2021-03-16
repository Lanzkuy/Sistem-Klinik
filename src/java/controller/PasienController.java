/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.PasienDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pasien_model;

/**
 *
 * @author Ahmad Maulana
 */
@WebServlet(name = "PasienController", urlPatterns = {"/PasienController"})
public class PasienController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String page=request.getParameter("page");
        PrintWriter out=response.getWriter();
        PasienDAO dao= new PasienDAO();
        Gson g=new Gson();
        if(page==null)
        {
            List<pasien_model> listPasien=dao.getData();
            String jsonPasien=g.toJson(listPasien);
            out.println(jsonPasien);
        }
        else if("insert".equals(page))
        {
            pasien_model pm=new pasien_model();
            pm.setId_pasien(dao.GenerateID());
            pm.setNama_pasien(request.getParameter("nama_pasien"));
            pm.setNo_hp(request.getParameter("no_hp"));
            pm.setNo_ktp(request.getParameter("no_ktp"));
            pm.setUser_id(request.getParameter("user_id"));
            pm.setAlamat(request.getParameter("alamat"));
            pm.setTgl_lahir(request.getParameter("tgl_lahir"));
            pm.setGol_darah(request.getParameter("gol_darah"));
            pm.setJenis_kelamin(request.getParameter("jenis_kelamin"));
            pm.setPassword(request.getParameter("password"));
            dao.save(pm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Added");
        }
        else if("update".equals(page))
        {
            pasien_model pm=new pasien_model();
            pm.setId_pasien(request.getParameter("id_pasien"));
            pm.setNama_pasien(request.getParameter("nama_pasien"));
            pm.setNo_hp(request.getParameter("no_hp"));
            pm.setNo_ktp(request.getParameter("no_ktp"));
            pm.setUser_id(request.getParameter("user_id"));
            pm.setAlamat(request.getParameter("alamat"));
            pm.setTgl_lahir(request.getParameter("tgl_lahir"));
            pm.setGol_darah(request.getParameter("gol_darah"));
            pm.setJenis_kelamin(request.getParameter("jenis_kelamin"));
            pm.setPassword(request.getParameter("password"));
            dao.save(pm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Updated");
        }
        else if("byid".equals(page))
        {
            String json=g.toJson(dao.getPasienById(request.getParameter("id_pasien")));
            response.setContentType("application/json");
            out.println(json);
        }
        else if("delete".equals(page))
        {
            dao.deleteData(request.getParameter("id_pasien"));
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Deleted");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
