/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.PoliDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.poli_model;

/**
 *
 * @author Ahmad Maulana
 */
@WebServlet(name = "PoliController", urlPatterns = {"/PoliController"})
public class PoliController extends HttpServlet {

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
        PoliDAO dao= new PoliDAO();
        Gson g=new Gson();
        if(page==null)
        {
            List<poli_model> listPoli=dao.getData();
            String jsonPoli=g.toJson(listPoli);
            out.println(jsonPoli);
        }
        else if("insert".equals(page))
        {
            poli_model pm=new poli_model();
            pm.setId_poli(dao.GenerateID());
            pm.setNama_poli(request.getParameter("nama_poli"));
            dao.save(pm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Added");
        }
        else if("update".equals(page))
        {
            poli_model pm=new poli_model();
            pm.setId_poli(request.getParameter("id_poli"));
            pm.setNama_poli(request.getParameter("nama_poli"));
            dao.save(pm, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Updated");
        }
        else if("byid".equals(page))
        {
            String json=g.toJson(dao.getPoliById(request.getParameter("id_poli")));
            response.setContentType("application/json");
            out.println(json);
        }
        else if("delete".equals(page))
        {
            dao.deleteData(request.getParameter("id_poli"));
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
