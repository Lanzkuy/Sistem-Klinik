/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.supplier_model;
import dao.SupplierDAO;
import java.util.List;

/**
 *
 * @author Ahmad Maulana
 */
@WebServlet(name = "SupplierController", urlPatterns = {"/SupplierController"})
public class SupplierController extends HttpServlet {

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
        SupplierDAO dao= new SupplierDAO();
        Gson g=new Gson();
        if(page==null)
        {
            List<supplier_model> listSupplier=dao.getData();
            String jsonSupplier=g.toJson(listSupplier);
            out.println(jsonSupplier);
        }
        else if("insert".equals(page))
        {
            supplier_model sm=new supplier_model();
            sm.setId_supplier(dao.GenerateID());
            sm.setNama_supplier(request.getParameter("nama_supplier"));
            sm.setEmail(request.getParameter("email"));
            sm.setAlamat(request.getParameter("alamat"));
            sm.setNo_telepon(request.getParameter("no_telepon"));
            sm.setUser_id(request.getParameter("user_id"));
            dao.save(sm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Added");
        }
        else if("update".equals(page))
        {
            supplier_model sm=new supplier_model();
            sm.setId_supplier(request.getParameter("id_supplier"));
            sm.setNama_supplier(request.getParameter("nama_supplier"));
            sm.setEmail(request.getParameter("email"));
            sm.setAlamat(request.getParameter("alamat"));
            sm.setNo_telepon(request.getParameter("no_telepon"));
            sm.setUser_id(request.getParameter("user_id"));
            dao.save(sm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Updated");
        }
        else if("byid".equals(page))
        {
            String json=g.toJson(dao.getSupplierById(request.getParameter("id_supplier")));
            response.setContentType("application/json");
            out.println(json);
        }
        else if("delete".equals(page))
        {
            dao.deleteData(request.getParameter("id_supplier"));
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
