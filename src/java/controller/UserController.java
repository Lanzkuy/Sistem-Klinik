/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.UserDAO;
import model.user_model;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ahmad Maulana
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

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
        UserDAO dao= new UserDAO();
        Gson g=new Gson();
        if(page==null)  
        {
            List<user_model> listUser=dao.getData();
            String jsonUser=g.toJson(listUser);
            out.println(jsonUser);
        }
        else if("insert".equals(page))
        {
            user_model um=new user_model();
            String id=dao.GenerateID();
            um.setId_user(id);
            um.setNama_user(request.getParameter("nama_user"));
            um.setNo_ktp(request.getParameter("no_ktp"));
            um.setNo_hp(request.getParameter("no_hp"));
            um.setPassword(request.getParameter("password"));
            um.setAlamat(request.getParameter("alamat"));
            um.setId_role(request.getParameter("id_role"));
            dao.save(um, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print(id);
        }
        else if("update".equals(page))
        {
            user_model um=new user_model();
            um.setId_user(request.getParameter("id_user"));
            um.setNama_user(request.getParameter("nama_user"));
            um.setNo_ktp(request.getParameter("no_ktp"));
            um.setNo_hp(request.getParameter("no_hp"));
            um.setPassword(request.getParameter("password"));
            um.setAlamat(request.getParameter("alamat"));
            um.setId_role(request.getParameter("id_role"));
            dao.save(um, page);
            
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Updated");
        }
        else if("byid".equals(page))
        {
            String json=g.toJson(dao.getUserById(request.getParameter("id_user")));
            response.setContentType("application/json");
            out.println(json);
        }
        else if("delete".equals(page))
        {
            dao.deleteData(request.getParameter("id_user"));
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data Deleted");
        }
        else if("login".equals(page))
        {
            String json=g.toJson(dao.login(request.getParameter("id_user"),request.getParameter("password")));
            response.setContentType("application/json");
            out.println(json);
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
