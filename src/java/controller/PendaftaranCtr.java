/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.PendaftaranDao;
import dao.PendaftaranDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author M.Yazid
 */
@WebServlet(name = "PendaftaranCtr", urlPatterns = {"/PendaftaranCtr"})
public class PendaftaranCtr extends HttpServlet {

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
        PendaftaranDao dao = new PendaftaranDao();
        Gson gson       = new Gson();
        
        page = "edit";
        
        /*if (page == null) {
            List<Pendaftaran> list = dao.getAll();
            
            String jsonKaryawan = gson.toJson(list);
            out.println(jsonKaryawan);
            System.out.println("berhasil get all data : "+jsonKaryawan);
        }
        else if ("tambah".equals(page)){
            String id = request.getParameter("nik");
            String no = "PLG001";
            String poli = "PL001";
            String tgl = "2021-09-09";
            String nama = request.getParameter("nama");
            id = "KR0011";
            if (dao.getRecordById(no, poli, tgl).getNoAntrian() != null) {
                response.setContentType("text/html;charset=UTF-8");
                out.print("ID : " + id + " sudah terpakai");
            }
            else{
                model.setNoAntrian("PLG001");
                model.setIdPasien("PS0002");
                model.setIdPoli("PLG");
                model.setTglDaftar("2021-09-09");
                model.setNoAntrian("PLG0001");
                model.setKeterangan("no comment");
                model.setUserId("US001");
                model.setWaktu("2021-02-21 23:22:22");
                model.setDeleted(0);

                dao.simpanData(model, page);

                response.setContentType("text/html;charset=UTF-8");
                out.print("Data Berhasil disimpan");
            }
        }
        else if("tampil".equals(page)){
            String json = gson.toJson(dao.getRecordById("PLG002", "PLG", "2021-02-26"));
            response.setContentType("application/json");
            out.println(json);
        }      
        else if ("edit".equals(page)) {
                model.setNoAntrian("PLG001");
                model.setIdPasien("PS0002");
                model.setIdPoli("PLG");
                model.setTglDaftar("2021-02-21");
                model.setNoAntrian("PLG0001");
                model.setKeterangan("Keterangan baruu");
                model.setUserId("US001");
                model.setWaktu("2021-02-21 23:22:22");
                model.setDeleted(0);
                dao.simpanData(model, page);
                
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data Berhasil diupdate");
        }
//        else if ("hapus".equals(page)) {
//            dao.hapusData("KR0002");
//            
//            response.setContentType("text/html;charset=UTF-8");
//            out.print("Data Berhasil dihapus");
//        }*/
   
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
