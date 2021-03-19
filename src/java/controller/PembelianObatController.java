/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.PembelianObatDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pembelian_obat_model;

/**
 *
 * @author Farhan Bhezni
 */
@WebServlet(name = "PembelianObatController", urlPatterns = {"/PembelianObatController"})
public class PembelianObatController extends HttpServlet{

    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        PembelianObatDAO dao = new PembelianObatDAO();
        Gson gson = new Gson();

        if (page == null) {
            List<pembelian_obat_model> ListPembelianObat = dao.getData();
            String jsonPembelianObat = gson.toJson(ListPembelianObat);
            out.println(jsonPembelianObat);
        }
        else if ("insert".equals(page)) {
            pembelian_obat_model pom=new pembelian_obat_model();
            pom.setId_trans(dao.GenerateID());
            pom.setId_supplier(request.getParameter("id_supplier"));
            pom.setId_obat(request.getParameter("id_obat"));
            pom.setId_user(request.getParameter("user_id"));
            pom.setJumlah(Integer.parseInt(request.getParameter("jumlah")));
            pom.setKeterangan(request.getParameter("keterangan"));
            pom.setNo_faktur(request.getParameter("no_faktur"));
            pom.setTgl_expired(request.getParameter("tgl_expired"));
            pom.setTgl_faktur(request.getParameter("tgl_faktur"));
            
            dao.save(pom, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Added");
        }
        else if ("delete".equals(page)) {
            dao.delete(request.getParameter("id_trans"));

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
