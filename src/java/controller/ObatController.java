/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.ObatDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.obat_model;

/**
 *
 * @author Ahmad Maulana
 */
@WebServlet(name = "ObatController", urlPatterns = {"/ObatController"})
public class ObatController extends HttpServlet {

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
        ObatDAO dao= new ObatDAO();
        Gson g=new Gson();
        if(page==null)
        {
            List<obat_model> listObat=dao.getData();
            String jsonObat=g.toJson(listObat);
            out.println(jsonObat);
        }
        else if("insert".equals(page))
        {
            obat_model pm=new obat_model();
            pm.setId_obat(dao.GenerateID());
            pm.setNama_obat(request.getParameter("nama_obat"));
            pm.setSatuan(request.getParameter("satuan"));
            pm.setHarga_jual(Double.parseDouble(request.getParameter("harga_jual")));
            pm.setStok(Integer.parseInt(request.getParameter("stok")));
            pm.setNo_faktur(request.getParameter("no_faktur"));
            pm.setUser_id(request.getParameter("user_id"));
            dao.save(pm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Added");
        }
        else if("update".equals(page))
        {
            obat_model pm=new obat_model();
            pm.setId_obat(request.getParameter("id_obat"));
            pm.setNama_obat(request.getParameter("nama_obat"));
            pm.setSatuan(request.getParameter("satuan"));
            pm.setHarga_jual(Double.parseDouble(request.getParameter("harga_jual")));
            pm.setStok(Integer.parseInt(request.getParameter("stok")));
            pm.setNo_faktur(request.getParameter("no_faktur"));
            pm.setUser_id(request.getParameter("user_id"));
            dao.save(pm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Updated");
        }
        else if("byid".equals(page))
        {
            String json=g.toJson(dao.getObatById(request.getParameter("id_obat")));
            response.setContentType("application/json");
            out.println(json);
        }
        else if("delete".equals(page))
        {
            dao.delete(request.getParameter("id_obat"));
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
