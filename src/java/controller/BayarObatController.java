/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.BayarObatDAO;
import dao.DetailBayarObatDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bayar_obat_model;
import model.detail_bayar_obat_model;

/**
 *
 * @author Farhan Bhezni
 */
@WebServlet(name = "BayarObatController", urlPatterns = {"/BayarObatController"})
public class BayarObatController extends HttpServlet{

    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();

        BayarObatDAO dao = new BayarObatDAO();
        DetailBayarObatDAO dao2=new DetailBayarObatDAO();
        Gson gson = new Gson();

        if (page == null) {
            List<bayar_obat_model> ListBayarObat = dao.getData();
            String jsonBayarObat = gson.toJson(ListBayarObat);
            out.println(jsonBayarObat);
        }
        else if ("insert".equals(page)) {
            System.out.println("aaa");
            String id= dao.GenerateID();
            System.out.println(id);
            System.out.println(id);
            bayar_obat_model bom=new bayar_obat_model();
            bom.setId_pembayaran(id);
            bom.setId_pasien(request.getParameter("id_pasien"));
            bom.setId_resep(request.getParameter("id_resep"));
            bom.setJenis_pembayaran(request.getParameter("jenis_pembayaran"));
            bom.setTgl_pembayaran(request.getParameter("tgl_pembayaran"));
            bom.setUser_id(request.getParameter("user_id"));
            dao.save(bom, page);
            
            detail_bayar_obat_model dbom=new detail_bayar_obat_model();
            dbom.setId_pembayaran(id);
            dbom.setId_obat(request.getParameter("id_obat"));
            dbom.setHarga(Double.parseDouble(request.getParameter("harga")));
            dbom.setJumlah(Integer.parseInt(request.getParameter("jumlah")));
            dao2.save(dbom, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Added");
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
