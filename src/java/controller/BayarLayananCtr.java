/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.BayarLayananDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bayar_layanan_model;

/**
 *
 * @author M.Yazid
 */
@WebServlet(name = "BayarLayanan", urlPatterns = {"/BayarLayanan"})
public class BayarLayananCtr extends HttpServlet {

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
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();
        BayarLayananDAO dao = new BayarLayananDAO();
        bayar_layanan_model blm  = new bayar_layanan_model();
        Gson g = new Gson();
        
        page = null;
        
        if (page == null) {
            List<bayar_layanan_model> list = dao.getData();
            String jsonKaryawan = g.toJson(list);
            out.println(jsonKaryawan);
        }
        else if ("insert".equals(page)){
            blm.setId_bayar_layanan(request.getParameter("id_bayar_layanan"));
            blm.setId_layanan(request.getParameter("id_layanan"));
            blm.setId_detail_layanan(request.getParameter("id_detail_layanan"));
            blm.setId_pasien(request.getParameter("id_pasien"));
            blm.setTgl_layanan(request.getParameter("tgl_layanan"));
            blm.setKeterangan(request.getParameter("keterangan"));
            dao.save(blm, page);

            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Added");
        }  
        else if ("update".equals(page)) {
            blm.setId_bayar_layanan(request.getParameter("id_bayar_layanan"));
            blm.setId_layanan(request.getParameter("id_layanan"));
            blm.setId_detail_layanan(request.getParameter("id_detail_layanan"));
            blm.setId_pasien(request.getParameter("id_pasien"));
            blm.setTgl_layanan(request.getParameter("tgl_layanan"));
            blm.setKeterangan(request.getParameter("keterangan"));
            dao.save(blm, page);

            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Updated");
        }
        else if("byid".equals(page)){
            String json=g.toJson(dao.getBayarLayananByID(request.getParameter("id_user")));
            response.setContentType("application/json");
            out.println(json);
        }    
        else if ("delete".equals(page)) {
            dao.deleteData(request.getParameter("id_bayar_layanan"));
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
