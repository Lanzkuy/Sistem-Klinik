/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.DokterDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dokter_model;

/**
 *
 * @author Ahmad Maulana
 */
@WebServlet(name = "DokterController", urlPatterns = {"/DokterController"})
public class DokterController extends HttpServlet {

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
        DokterDAO dao= new DokterDAO();
        Gson g=new Gson();
        if(page==null)
        {
            List<dokter_model> listDokter=dao.getData();
            String jsonDokter=g.toJson(listDokter);
            out.println(jsonDokter);
        }
        else if("insert".equals(page))
        {
            dokter_model dm=new dokter_model();
            dm.setId_dokter(dao.GenerateID());
            dm.setNama_dokter(request.getParameter("nama_dokter"));
            dm.setTgl_lahir(request.getParameter("tgl_lahir"));
            dm.setNo_hp(request.getParameter("no_hp"));
            dm.setNo_ktp(request.getParameter("no_ktp"));
            dm.setNo_npwp(request.getParameter("no_npwp"));
            dm.setEmail(request.getParameter("email"));
            dm.setPassword(request.getParameter("password"));
            dm.setUser_id(request.getParameter("user_id"));
            dm.setId_poli(request.getParameter("id_poli"));
            dm.setAlamat(request.getParameter("alamat"));
            dm.setSpecialis(request.getParameter("specialis"));
            dm.setJenis_kelamin(request.getParameter("jenis_kelamin"));
            dao.save(dm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Added");
        }
        else if("update".equals(page))
        {
            dokter_model dm=new dokter_model();
            dm.setId_dokter(request.getParameter("id_dokter"));
            dm.setNama_dokter(request.getParameter("nama_dokter"));
            dm.setTgl_lahir(request.getParameter("tgl_lahir"));
            dm.setNo_hp(request.getParameter("no_hp"));
            dm.setNo_ktp(request.getParameter("no_ktp"));
            dm.setNo_npwp(request.getParameter("no_npwp"));
            dm.setEmail(request.getParameter("email"));
            dm.setPassword(request.getParameter("password"));
            dm.setUser_id(request.getParameter("user_id"));
            dm.setId_poli(request.getParameter("id_poli"));
            dm.setAlamat(request.getParameter("alamat"));
            dm.setSpecialis(request.getParameter("specialis"));
            dm.setJenis_kelamin(request.getParameter("jenis_kelamin"));
            System.out.println(dm);
            dao.save(dm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Updated");
        }
        else if("byid".equals(page))
        {
            String json=g.toJson(dao.getDokterById(request.getParameter("id_dokter")));
            response.setContentType("application/json");
            out.println(json);
        }
        else if("delete".equals(page))
        {
            dao.deleteData(request.getParameter("id_dokter"));
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
