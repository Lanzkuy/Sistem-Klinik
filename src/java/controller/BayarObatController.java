/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.BayarObatDAO;
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
        Gson gson = new Gson();

        if (page == null) {
            List<bayar_obat_model> ListBayarObat = dao.getData();
            String jsonBayarObat = gson.toJson(ListBayarObat);
            out.println(jsonBayarObat);
            System.out.println("Berhasil Get Data : "+jsonBayarObat);
        }

        else if ("insert".equals(page)) {
            
        }
        else if ("show".equals(page)) {
            
        }

        else if ("update".equals(page)) {
            bayar_obat_model bo = new bayar_obat_model();
            bo.setId_pembayaran(request.getParameter("id_pembelian"));
            bo.setTgl_pembayaran(request.getParameter("tgl_pembayaran"));
            bo.setId_pasien(request.getParameter("id_pasien"));
            bo.setId_resep(request.getParameter("id_resep"));
            bo.setJenis_pembayaran(request.getParameter("jenis_pembayaran"));
            bo.setUser_id(request.getParameter("id_user"));
            dao.save(bo, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil diupdate");
        }

        else if ("delete".equals(page)) {
            dao.delete(request.getParameter("id_pembelian"));

            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil dihapus");
        }
    }

}
